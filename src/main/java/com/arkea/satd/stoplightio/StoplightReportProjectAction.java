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
