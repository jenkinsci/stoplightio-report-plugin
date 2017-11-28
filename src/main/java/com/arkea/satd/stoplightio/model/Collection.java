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
package com.arkea.satd.stoplightio.model;

import java.util.List;
import java.util.ArrayList;

/**
 * Internal model
 * @author Nicolas TISSERAND
 *
 */
public class Collection {

	private List<Scenario> scenarios = new ArrayList<>();

	private int totalTests;
	private int succeededTests;
	
	public List<Scenario> getScenarios() {
		return scenarios;
	}

	public void setScenarios(final List<Scenario> scenarios) {
		this.scenarios = scenarios;
	}

	public String toString() {
		return scenarios.toString();
	}

	public int getTotalTests() {
		return totalTests;
	}

	public void setTotalTests(final int totalTests) {
		this.totalTests = totalTests;
	}

	public int getSucceededTests() {
		return succeededTests;
	}

	public void setSucceededTests(final int succeededTests) {
		this.succeededTests = succeededTests;
	}

}
