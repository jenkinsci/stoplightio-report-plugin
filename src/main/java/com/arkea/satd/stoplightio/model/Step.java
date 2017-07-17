package com.arkea.satd.stoplightio.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Internal model
 * @author Nicolas TISSERAND
 *
 */
public class Step {

	private String label;
	private String verb;
	private String url;
	private String duration;
	private List<Assertion> assertions = new ArrayList<Assertion>();

	public String getLabel() {
		return label;
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	public String getVerb() {
		return verb;
	}

	public void setVerb(final String verb) {
		this.verb = verb;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(final String duration) {
		this.duration = duration;
	}
	
	public List<Assertion> getAssertions() {
		return assertions;
	}

	public void setAssertions(final List<Assertion> assertions) {
		this.assertions = assertions;
	}

	public String toString() {
		return label + ", " + verb + " " + url + " ("+duration+") " + " : " + assertions.toString();
	}	
	
}
