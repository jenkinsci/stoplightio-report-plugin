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
