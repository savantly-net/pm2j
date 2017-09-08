package net.savantly.pm2j;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

public class Pm2jConnectorTest {
	
	Logger log = LogManager.getLogger(Pm2jConnectorTest.class);

	private static Pm2Connector connector = new Pm2Connector();
	private String configFile = "./src/test/resources/ecosystem.json";
	
	@AfterClass
	public static void after(){
		connector.killPm2();
	}
	
	@Test
	public void getPm2VersionTest(){
		connector.getPm2Version();
	}
	
	@Test
	public void getPm2ProcessesTest(){
		List<Pm2ProcessInfo> pm2Processes = connector.getPm2Processes();
		Assert.assertNotNull(pm2Processes);
	}
	
	@Test
	public void startAndStopPm2Test() throws IOException{
		connector.startPm2(configFile);
		connector.stopPm2();
	}

}
