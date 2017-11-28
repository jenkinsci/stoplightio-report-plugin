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
