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
package com.arkea.satd.stoplightio;

import java.util.ArrayList;
import java.util.List;

import hudson.model.Action;
import hudson.model.Job;
import hudson.model.Run;

/**
 * Class for Main Project Page
 * @author Nicolas TISSERAND
 *
 */
public class StoplightReportProjectAction implements Action {

	private Job<?, ?> project;
	
	public StoplightReportProjectAction(final Job<?, ?> project) {
		this.project = project;
	}

	@Override
	public String getDisplayName() {
		return "Scenario Execution Reports";
	}
	
	@Override
	public String getIconFileName() {
		return "/plugin/stoplightio-report/img/scenarios.png";
	}

	@Override
	public String getUrlName() {
		return "stoplightProjectReport";
	}

	public Job<?, ?> getProject() {
		return project;
	}

	public List<StoplightReportBuildAction> getProjectCollections() {
		final List<StoplightReportBuildAction> projectCollections = new ArrayList<>();
        for (final Run<?, ?> currentBuild : project.getBuilds()) {
        	final StoplightReportBuildAction action = currentBuild.getAction(StoplightReportBuildAction.class);
        	if(action!=null) {
            	// Add only valid actions
        		projectCollections.add(action);
        	}
        }
        return projectCollections;		
	}

}
