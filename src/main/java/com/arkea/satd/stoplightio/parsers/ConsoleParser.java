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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.arkea.satd.stoplightio.model.Assertion;
import com.arkea.satd.stoplightio.model.Collection;
import com.arkea.satd.stoplightio.model.Scenario;
import com.arkea.satd.stoplightio.model.Step;

import hudson.FilePath;

/**
 * Parser for a log in console format
 * @author Nicolas TISSERAND
 *
 */
public final class ConsoleParser {

	// Useful patterns for console parsing
	private static final Pattern SCENARIO_LINE_PATTERN = Pattern.compile("^\\s{4}(\\S.*) \\((.*)\\)");
	private static final Pattern STEP_LINE_PATTERN = Pattern.compile("^\\s{6}(.*), (.*) \\((.*)\\)");
	private static final Pattern ASSERTION_LINE_PATTERN = Pattern.compile("^\\s{9}(\\S)  (.*)");
	
	// E2 9C 93
	private static final String SUCCESS_MARK = new String(new byte[]{-30, -100, -109}, StandardCharsets.UTF_8);
	
	private ConsoleParser() {
	}
	
	/**
	 * Parser for Prism console output
	 * @param filepath FilePath to be parsed
	 * @return a Collection object filled with the results
	 */
	public static Collection parse(final FilePath filepath) {
		
		// Result initialization
		final Collection collection = new Collection();

		int totalTests = 0;
		int succeededTests = 0;
		
		// File parsing
		try (InputStream is = filepath.read();
				InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
				BufferedReader br = new BufferedReader(isr);
			) {
			
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
					currentStep.setDuration(m.group(3));
					Matcher m2 = Pattern.compile("(.*) (.*)").matcher(m.group(2));
					if(m2.matches()) {
						currentStep.setVerb(m2.group(1));
						currentStep.setUrl(m2.group(2));
					} else {
						currentStep.setVerb("");
						currentStep.setUrl("");
					}
					currentScenario.getSteps().add(currentStep);
				}
				
				// Test on "assertion" line			
				m = ASSERTION_LINE_PATTERN.matcher(line);
				if(m.matches()) {
					final Assertion assertion = new Assertion();
					assertion.setSuccess(SUCCESS_MARK.equals(m.group(1)));
					assertion.setMessage(m.group(2));
					
					if(currentStep.getAssertions()==null){
						currentStep.setAssertions(new ArrayList<Assertion>() );
					}
					currentStep.getAssertions().add(assertion);
					
					// Global stats for collection
					totalTests++;
					if(assertion.isSuccess()) {
						succeededTests++;
					}
				}
			}

		} catch (IOException | InterruptedException e) {
			Logger logger = LogManager.getLogManager().getLogger("hudson.WebAppMain");
			logger.log(Level.SEVERE, "Error while parsing the console default output", e);
		}
		
		// Global stats
		collection.setTotalTests(totalTests);
		collection.setSucceededTests(succeededTests);
		
		return collection;
	}
	
	
}
