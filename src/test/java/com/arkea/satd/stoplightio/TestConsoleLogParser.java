package com.arkea.satd.stoplightio;

import java.io.File;

import com.arkea.satd.stoplightio.model.Collection;
import com.arkea.satd.stoplightio.parsers.ConsoleParser;

import junit.framework.TestCase;

/**
 * Simple Test for Console Parser
 * @author Nicolas TISSERAND
 *
 */
public class TestConsoleLogParser extends TestCase{

	public void test() {

		String fileLocation = "prism_console.log";
		
		Collection coll = ConsoleParser.parse(new File(fileLocation));
		
		assertNotNull(coll);

		System.out.println(coll.getSucceededTests() + " test(s) passed on " + coll.getTotalTests() );

		assertEquals(53, coll.getSucceededTests());
		assertEquals(53, coll.getTotalTests());

	}
	
}
