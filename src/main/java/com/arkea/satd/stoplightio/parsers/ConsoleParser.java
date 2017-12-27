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
package com.arkea.satd.stoplightio.parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.arkea.satd.stoplightio.model.Assertion;
import com.arkea.satd.stoplightio.model.Collection;
import com.arkea.satd.stoplightio.model.Scenario;
import com.arkea.satd.stoplightio.model.Step;

/**
 * Parser for a log in console format
 * @author Nicolas TISSERAND
 *
 */
public final class ConsoleParser {

	// Useful patterns for console parsing
	private static final Pattern SCENARIO_LINE_PATTERN = Pattern.compile("^\\s{4}(\\S.*) \\((.*)\\)");
	private static final Pattern STEP_LINE_PATTERN = Pattern.compile("^\\s{6}(.*), (.*) (.*) \\((.*)\\)");
	private static final Pattern ASSERTION_LINE_PATTERN = Pattern.compile("^\\s{9}(\\S)  (.*)");
	
	// E2 9C 93
	private static final String SUCCESS_MARK = new String(new byte[]{-30, -100, -109}, Charset.forName("UTF-8"));
	
	private ConsoleParser() {
	}
	
	/**
	 * Parser for Prism console output
	 * @param consoleFile File to be parsed
	 * @return a Collection object filled with the results
	 */
	public static Collection parse(final File consoleFile) {
		
		// Result initialization
		final Collection collection = new Collection();

		int totalTests = 0;
		int succeededTests = 0;
		
		// File parsing		
		FileInputStream fis = null;
		BufferedReader br = null;
		try {
			fis = new FileInputStream(consoleFile);
			br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
		 
			
			Scenario currentScenario = null;
			Step currentStep = null;
			
			String line = null;
			Matcher m;
			while ((line = br.readLine()) != null) {
				
				
				// Test on "scenario" line
				m = SCENARIO_LINE_PATTERN.matcher(line);
				if(m.matches()) {
					currentScenario = new Scenario();
					currentScenario.setLabel(m.group(1));
					currentScenario.setDuration(m.group(2));
					collection.getScenarios().add(currentScenario);
				}
				
				// Test on "step" line			
				m = STEP_LINE_PATTERN.matcher(line);
				if(m.matches()) {
					currentStep = new Step();
					currentStep.setLabel(m.group(1));
					currentStep.setVerb(m.group(2));
					currentStep.setUrl(m.group(3));
					currentStep.setDuration(m.group(4));
					currentScenario.getSteps().add(currentStep);
				}
				
				// Test on "assertion" line			
				m = ASSERTION_LINE_PATTERN.matcher(line);
				if(m.matches()) {
					final Assertion assertion = new Assertion();
					assertion.setSuccess(SUCCESS_MARK.equals(m.group(1)));
					assertion.setMessage(m.group(2));					
					currentStep.getAssertions().add(assertion);
					
					// Global stats for collection
					totalTests++;
					if(assertion.isSuccess()) {
						succeededTests++;
					}
				}
				
			}
			
			br.close();		
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fis!=null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(br!=null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		// Global stats
		collection.setTotalTests(totalTests);
		collection.setSucceededTests(succeededTests);
		
		return collection;
	}
	
	
}
