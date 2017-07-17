package com.arkea.satd.stoplightio.parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.arkea.satd.stoplightio.model.Assertion;
import com.arkea.satd.stoplightio.model.Collection;
import com.arkea.satd.stoplightio.model.Scenario;
import com.arkea.satd.stoplightio.model.Step;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
	 * @param resultFile File to be parsed
	 * @return a Collection object filled with the results
	 * @throws FileNotFoundException throwed if resultFile is not found
	 * @throws UnsupportedEncodingException throwed if resultFile is not JSON compliant
	 */
	public static Collection parse(final File resultFile) throws FileNotFoundException, UnsupportedEncodingException {
		
		// Result initialization
		final Collection collection = new Collection();

		int totalTests = 0;
		int succeededTests = 0;
		
		final FileInputStream fis = new FileInputStream(resultFile);
		final BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
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
								assrt.setSuccess(pass!=null?pass.getAsBoolean():false);
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

		
		// Global stats
		collection.setTotalTests(totalTests);
		collection.setSucceededTests(succeededTests);

		// Global stats
		collection.setTotalTests(totalTests);
		collection.setSucceededTests(succeededTests);
		
		return collection;
	}
	
	
}
