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

import com.arkea.satd.stoplightio.model.Assertion;
import com.arkea.satd.stoplightio.model.Collection;
import com.arkea.satd.stoplightio.model.Scenario;
import com.arkea.satd.stoplightio.model.Step;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import hudson.FilePath;

/**
 * Parser for a log in JSON format
 * @author Nicolas TISSERAND
 *
 */
public final class JsonResultParser {

	private JsonResultParser() {
	}
	
	/**
	 * Parser for Prism JSON output
	 * @param filepath FilePath to be parsed
	 * @return a Collection object filled with the results
	 * @throws InterruptedException thrown if resultFile is not readable
	 * @throws IOException thrown if resultFile is not readable or if if resultFile is not JSON compliant
	 */
	public static Collection parse(final FilePath filepath) throws InterruptedException, IOException {
		
		// Result initialization
		final Collection collection = new Collection();

		int totalTests = 0;
		int succeededTests = 0;


		try (InputStream is = filepath.read();
				InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
				BufferedReader br = new BufferedReader(isr);
			) {
			
			final JsonObject jsonObj = new JsonParser().parse(br).getAsJsonObject();
		
			// Iterate on section level : 
			for(final String firstLevelKey : new String[]{"before", "scenarios", "after"}) {
						
				if(jsonObj.has(firstLevelKey)) {
					final JsonObject section = jsonObj.getAsJsonObject(firstLevelKey);
					
					// Iterate on scenario level
					for(final String scenId : section.keySet()) {
						final JsonObject scen = section.getAsJsonObject(scenId);
						final JsonElement scenName = scen.get("name");
	
						final Scenario currentScenario = new Scenario();
						currentScenario.setLabel(scenName.getAsString());
						final double time = scen.get("time").getAsDouble() / 1000;
						currentScenario.setDuration(time+"s");
						collection.getScenarios().add(currentScenario);
	
						// Iterate on step level
						final JsonArray steps = scen.getAsJsonArray("steps");
						final int stepsCount = steps.size();
						for(int i=0; i<stepsCount; i++) {
							final JsonObject step = steps.get(i).getAsJsonObject();
							final JsonElement name = step.get("name");
							
							final Step currentStep = new Step();
							currentStep.setLabel(name==null ? "" : name.getAsString());
							final double stepTime = step.get("time").getAsDouble() / 1000;
							currentStep.setDuration(stepTime+"s");
							
							// If type HTTP : read VERB and URL
							if("http".equals(step.get("type").getAsString())) {
								final JsonObject input = step.get("input").getAsJsonObject();
								currentStep.setVerb(input.get("method").getAsString());
								currentStep.setUrl(input.get("url").getAsString());
							} else {
								currentStep.setVerb("");
								currentStep.setUrl("");
							}
							
							currentScenario.getSteps().add(currentStep);
							
							final JsonObject after = step.getAsJsonObject("after");
							if(after!=null) {
								final JsonArray assertions = after.getAsJsonArray("assertions");
								final int assertionsCount = assertions.size();
								// Iterate on assertion level
								for(int j=0; j<assertionsCount; j++) {
									final JsonObject assertion = assertions.get(j).getAsJsonObject();
									final JsonElement target = assertion.get("target");
									final JsonElement pass = assertion.get("pass");
									
									final Assertion assrt = new Assertion();
									assrt.setSuccess( pass!=null && pass.getAsBoolean());
									assrt.setMessage( target.getAsString() + " (" + assertion.get("op").getAsString() + ") " + (assertion.get("expected").isJsonObject() ? "against JSON Schema" : assertion.get("expected").getAsString()) );					
									currentStep.getAssertions().add(assrt);
									
									// Global stats for collection
									totalTests++;
									if(assrt.isSuccess()) {
										succeededTests++;
									}
									
								} // assertion
							}
						} // step level
					} // scenario level
				} // top level (section)
			}			
		}
		
		// Global stats
		collection.setTotalTests(totalTests);
		collection.setSucceededTests(succeededTests);

		// Global stats
		collection.setTotalTests(totalTests);
		collection.setSucceededTests(succeededTests);
		
		return collection;
	}
	
	
}
