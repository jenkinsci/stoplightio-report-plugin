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
import com.arkea.satd.stoplightio.parsers.ConsoleParser;
import com.arkea.satd.stoplightio.parsers.JsonResultParser;
import junit.framework.TestCase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class TestFull extends TestCase {

	public void test() {

		String fileLocation = "prism_console.log";
		Collection coll = null;
		File testFile = new File(fileLocation);
		InputStream testStream = null;
		try {
			testStream = new FileInputStream(testFile);
			try {
				coll = JsonResultParser.parse(testStream);
			} catch(Exception e) {
				assertNull(coll);
				System.out.println("JsonResultParser failed, using ConsoleParser");
				coll = ConsoleParser.parse(testStream);
			}
		} catch (FileNotFoundException e) {
			fail();
		}

   		
		assertNotNull(coll);

		System.out.println(coll.getSucceededTests() + " test(s) passed on " + coll.getTotalTests() );

		assertEquals(53, coll.getSucceededTests());
		assertEquals(53, coll.getTotalTests());
		
		
		
	}

}
