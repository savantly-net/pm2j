package net.savantly.pm2j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Pm2Locator {
	
	private final static Logger log = LogManager.getLogger(Pm2Locator.class);
	private static String OS = System.getProperty("os.name").toLowerCase();
	
	public static String getBinaryLocationFromPath(String binaryName){
		List<String> entries = new ArrayList<String>();
		try {
		    String line;
		    ProcessBuilder pb = new ProcessBuilder(getLocatorCommand(), binaryName);
		    Process p = pb.start();
		    BufferedReader input =
		            new BufferedReader(new InputStreamReader(p.getInputStream()));
		    while ((line = input.readLine()) != null) {
		        log.debug("Found entry: {}", line);
		        entries.add(line);
		    }
		    input.close();
		} catch (Exception err) {
		    log.error(err);
		}
		if(entries.isEmpty()){
			throw new RuntimeException("Unable to locate pm2");
		}
		return entries.get(entries.size()-1);
	}
	
	public static String getPm2Home(){
		String PM2_HOME = System.getenv("PM2_HOME");
		if(null != PM2_HOME){
			return PM2_HOME;
		} else {
			String userHomeDir = System.getProperty("user.home", "~");
			return Paths.get(userHomeDir, ".pm2").toString();
		}
	}

	private static String getLocatorCommand() {
		if(isWindows()){
			return "where";
		} else if(isUnix()){
			return "which";
		} else {
			throw new RuntimeException("Unknown OS");
		}
	}
	
	public static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}

	public static boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
	}
}
