package com.arkea.satd.stoplightio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import com.arkea.satd.stoplightio.model.Collection;
import com.arkea.satd.stoplightio.parsers.JsonResultParser;

import junit.framework.TestCase;

/**
 * Simple Test for Console Parser
 * @author Nicolas TISSERAND
 *
 */
public class TestJsonParser extends TestCase{

	public void test() {

		String fileLocation = "collection_result.json";		
		
		Collection coll;
		try {
			coll = JsonResultParser.parse(new File(fileLocation));
			
			assertNotNull(coll);

			System.out.println(coll.getSucceededTests() + " test(s) passed on " + coll.getTotalTests() );

			assertEquals(56, coll.getSucceededTests());
			assertEquals(56, coll.getTotalTests());

		} catch (FileNotFoundException e) {
			fail();
		} catch (UnsupportedEncodingException e) {
			fail();
		}

	}

}
