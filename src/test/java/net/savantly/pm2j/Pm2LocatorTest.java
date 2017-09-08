package net.savantly.pm2j;

import static org.junit.Assert.*;

import org.junit.Test;

public class Pm2LocatorTest {

	@Test
	public void testException() {
		String expected = "passed";
		String actual = expected;
		try{
			actual = Pm2Locator.getBinaryLocationFromPath("okasdffdgdghfghjsdfnon");
			fail("Unknown binary should throw exception");
		} catch (Exception ex){
			assertTrue("result should be unchanged", actual == expected);
		}
	}
	
	@Test
	public void testValid() {
		String actual = "";
		try{
			actual = Pm2Locator.getBinaryLocationFromPath("pm2");
		} catch (Exception ex){
			assertTrue("result should be set", actual.length() > 0);
		}
	}

}
