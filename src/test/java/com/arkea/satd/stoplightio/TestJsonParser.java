/*
 * Copyright 2017 Credit Mutuel Arkea
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */
package com.arkea.satd.stoplightio;

import com.arkea.satd.stoplightio.model.Collection;
import com.arkea.satd.stoplightio.parsers.JsonResultParser;
import hudson.FilePath;
import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;

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
			File testFile = new File(fileLocation);
			FilePath fp =  new FilePath(testFile);
			coll = JsonResultParser.parse(fp);

			assertNotNull(coll);

			System.out.println(coll.getSucceededTests() + " test(s) passed on " + coll.getTotalTests() );

			assertEquals(56, coll.getSucceededTests());
			assertEquals(56, coll.getTotalTests());

		} catch (IOException | InterruptedException e) {
			fail();
		}

	}

}
