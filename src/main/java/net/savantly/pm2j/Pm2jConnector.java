package net.savantly.pm2j;

import static net.savantly.pm2j.Pm2Commands.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Pm2jConnector {
	
	private final static Logger log = LogManager.getLogger(Pm2jConnector.class);
	private static ObjectMapper objectMapper = new ObjectMapper();
	static{
		objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
	}

	
	private String pm2Path;
	private String pm2Home;
	
	public Pm2jConnector() {
		this.pm2Path = Pm2Locator.getBinaryLocationFromPath("pm2");
		this.pm2Home = Pm2Locator.getPm2Home();
	}
	
	public Pm2jConnector(String pm2Path) {
		this.pm2Path = pm2Path;
		this.pm2Home = Pm2Locator.getPm2Home();;
	}
	
	public Pm2jConnector(String pm2Path, String pm2Home) {
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
	
	@SuppressWarnings("unchecked")
	public List<Pm2ProcessInfo> getPm2Processes(){
		String json = executePm2Command(PRETTY_LIST);
		List<Pm2ProcessInfo> pm2Processes = new ArrayList<Pm2ProcessInfo>();
		try {
			pm2Processes.addAll(objectMapper.readValue(json, pm2Processes.getClass()));
		} catch (Exception ex) {
			log.error(ex);
		}
		return pm2Processes;
	}


	public void startPm2(String configFilePath){
		File configFile = new File(configFilePath);
		if(!configFile.canRead()){
			throw new RuntimeException(String.format("Cannot read the config file: %s", configFilePath));
		} else {
			log.info("Using configuration file: {}", configFilePath);
			executePm2Command(START, configFilePath);
		}

	}
	
	
	private String executePm2Command(String... args) {
		String[] parameters = new String[args.length+1];
		parameters[0] = pm2Path;
		for (int i = 0; i < args.length; i++) {
			parameters[i+1] = args[i];
		}
		
		StringBuilder sb = new StringBuilder();
		
		try {
		    String line;
		    ProcessBuilder pb = new ProcessBuilder(parameters);
		    Process p = pb.start();
		    BufferedReader input =
		            new BufferedReader(new InputStreamReader(p.getInputStream()));
		    while ((line = input.readLine()) != null) {
		        log.debug(line);
		        sb.append(line);
		    }
		    input.close();
		} catch (Exception err) {
		    log.error(err);
		}
		return sb.toString();
	}
}
