package com.arkea.satd.stoplightio.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Internal model
 * @author Nicolas TISSERAND
 *
 */
public class Scenario {

	private String label;
	private String duration;
	private List<Step> steps = new ArrayList<Step>();

	public String getLabel() {
		return label;
	}

	public void setLabel(final String label) {
		this.label = label;
	}	
	
	public String getDuration() {
		return duration;
	}

	public void setDuration(final String duration) {
		this.duration = duration;
	}

	public List<Step> getSteps() {
		return steps;
	}

	public void setSteps(final List<Step> steps) {
		this.steps = steps;
	}
	
	public String toString() {
		return label + " ("+duration+") " + " : " + steps.toString();
	}
	
	
}
