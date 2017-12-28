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
	private List<Assertion> assertions = new ArrayList<>();

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
