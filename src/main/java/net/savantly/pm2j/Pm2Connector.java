package net.savantly.pm2j;

import static net.savantly.pm2j.Pm2Commands.KILL;
import static net.savantly.pm2j.Pm2Commands.LOGS;
import static net.savantly.pm2j.Pm2Commands.PRETTY_LIST;
import static net.savantly.pm2j.Pm2Commands.SHOW;
import static net.savantly.pm2j.Pm2Commands.START;
import static net.savantly.pm2j.Pm2Commands.STATUS;
import static net.savantly.pm2j.Pm2Commands.STOP;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Pm2Connector {
	
	private final static Logger log = LogManager.getLogger(Pm2Connector.class);
	private static ObjectMapper objectMapper = new ObjectMapper();
	static{
		objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
	}

	
	private String pm2Path;
	private String pm2Home;
	
	public Pm2Connector() {
		this.pm2Path = Pm2Locator.getBinaryLocationFromPath("pm2");
		this.pm2Home = Pm2Locator.getPm2Home();
	}
	
	public Pm2Connector(String pm2Path) {
		this.pm2Path = pm2Path;
		this.pm2Home = Pm2Locator.getPm2Home();
	}
	
	public Pm2Connector(String pm2Path, String pm2Home) {
		this.pm2Path = pm2Path;
		this.pm2Home = pm2Home;
	}
	
	public void getPm2Version(){
		executePm2Command("-v");
	}
	
	public void killPm2(){
		executePm2Command(KILL);
	}
	
	public void stopPm2(){
		executePm2Command(STOP, "all");
	}
	
	public String showDetail(String processId){
		return executePm2Command(SHOW, processId);
	}

	// TODO: Need to do this without hanging the thread indefinitely
	@Deprecated
	public String getlogTail(String processId, int lines){
		return executePm2Command(20, LOGS, processId, "--lines", new Integer(lines).toString());
	}
	
	public boolean ensureRunning(){
		//File pm2Pid = new File(Paths.get(pm2Home, "pm2.pid").toString());
		executePm2Command(STATUS);
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public List<Pm2ProcessInfo> getPm2Processes(){
		if(!ensureRunning()) throw new RuntimeException("pm2 is not running");
		
		String json = executePm2Command(PRETTY_LIST);
		List<Pm2ProcessInfo> pm2Processes = new ArrayList<Pm2ProcessInfo>();
		try {
			pm2Processes.addAll(objectMapper.readValue(json, pm2Processes.getClass()));
		} catch (Exception ex) {
			log.error(ex);
		}
		return pm2Processes;
	}
	
	@SuppressWarnings("unchecked")
	public Pm2ProcessInfo getPm2Processes(int id){
		List<Pm2ProcessInfo> pm2Processes = getPm2Processes();
		for (Pm2ProcessInfo pm2ProcessInfo : pm2Processes) {
			if(pm2ProcessInfo.getPm_id() == id){
				return pm2ProcessInfo;
			}
		}
		// Didnt find a matching process id
		return null;
	}


	public void startPm2(String configFilePath){

		Path cwdPath = Paths.get("");
		String s = cwdPath.toAbsolutePath().toString();
		log.info("Current working directory path is: " + s);
		Path relativePathToConfig = Paths.get(cwdPath.toString(), configFilePath);
		
		File configFile = new File(relativePathToConfig.toString());
		if(!configFile.canRead()){
			throw new RuntimeException(String.format("Cannot read the config file: %s", configFile.getAbsolutePath()));
		} else {
			log.info("Using configuration file: {}", configFile.getPath());
			executePm2Command(START, relativePathToConfig.toString());
		}

	}
	
	public String executePm2Command(String... args) {
		return executePm2Command(120, args);
	}
	public String executePm2Command(long seconds, String... args) {
		// For debugging
		StringBuilder textCommand = new StringBuilder();
		String[] parameters = new String[args.length+1];
		parameters[0] = pm2Path;
		textCommand.append(pm2Path + " ");
		for (int i = 0; i < args.length; i++) {
			parameters[i+1] = args[i];
			textCommand.append(args[i] + " ");
		}
		
		StringBuilder sb = new StringBuilder();
		
		try {
		    String line;
		    log.info("executing command: {}", textCommand.toString());
		    ProcessBuilder pb = new ProcessBuilder(parameters);
		    pb.redirectErrorStream(true);
		    Process p = pb.start();
		    p.waitFor(seconds, TimeUnit.SECONDS);
		    BufferedReader input =
		            new BufferedReader(new InputStreamReader(p.getInputStream()));
		    while ((line = input.readLine()) != null) {
		    	if (line.contains("[ERROR]")) {
		    		log.error(line);
		    	} else {
		    		log.debug(line);
		    	}
		        sb.append(line);
		        sb.append("\n");
		    }
		    input.close();
		} catch (Exception err) {
		    log.error(err);
		}
		return sb.toString();
	}
}
