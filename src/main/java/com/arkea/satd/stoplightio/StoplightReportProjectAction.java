package com.arkea.satd.stoplightio;

import java.util.ArrayList;
import java.util.List;

import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Action;

/**
 * Class for Main Project Page
 * @author Nicolas TISSERAND
 *
 */
public class StoplightReportProjectAction implements Action {

	private final AbstractProject<?, ?> project;
	
	public StoplightReportProjectAction(final AbstractProject<?, ?> project) {
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

	public AbstractProject<?, ?> getProject() {
		return project;
	}

	public List<StoplightReportBuildAction> getProjectCollections() {
		final List<StoplightReportBuildAction> projectCollections = new ArrayList<>();
        final List<? extends AbstractBuild<?, ?>> builds = project.getBuilds();
        for (final AbstractBuild<?, ?> currentBuild : builds) {
        	
        	final StoplightReportBuildAction action = currentBuild.getAction(StoplightReportBuildAction.class);
        	// Add only valid actions
        	if(action!=null) {
        		projectCollections.add(action);
        	}
        }
        return projectCollections;
	}
	
}
