package com.arkea.satd.stoplightio;

import java.io.File;

import com.arkea.satd.stoplightio.model.Collection;
import com.arkea.satd.stoplightio.parsers.ConsoleParser;
import com.arkea.satd.stoplightio.parsers.JsonResultParser;

import junit.framework.TestCase;

public class TestFull extends TestCase {

	public void test() {

		String fileLocation = "prism_console.log";
		
   		Collection coll = null;
   		File f = new File(fileLocation);
   		try {
   			coll = JsonResultParser.parse(f);
   		} catch(Exception e) {
   			
   			assertNull(coll);
   			System.out.println("JsonResultParser failed, using ConsoleParser");
   			coll = ConsoleParser.parse(f);	   			
   		}
   		
		assertNotNull(coll);

		System.out.println(coll.getSucceededTests() + " test(s) passed on " + coll.getTotalTests() );

		assertEquals(53, coll.getSucceededTests());
		assertEquals(53, coll.getTotalTests());
		
		
		
	}

}
