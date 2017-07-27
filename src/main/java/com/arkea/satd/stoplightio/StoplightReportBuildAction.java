package com.arkea.satd.stoplightio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.arkea.satd.stoplightio.model.Collection;
import com.arkea.satd.stoplightio.model.Scenario;
import com.arkea.satd.stoplightio.model.Step;

import hudson.model.Run;
import jenkins.tasks.SimpleBuildStep;
import hudson.model.Action;
import hudson.model.Result;

/**
 * Class for Build Page
 * @author Nicolas TISSERAND
 *
 */

public class StoplightReportBuildAction implements Action, SimpleBuildStep.LastBuildAction {

	private Run<?, ?> build;
	private Collection collection;
	
	public StoplightReportBuildAction(final Run<?, ?> build, Collection collection) {
		this.build = build;
		this.collection = collection;
	}

	@Override
	public String getDisplayName() {
		return "Scenario Execution Report";
	}

	@Override
	public String getIconFileName() {
		return "/plugin/stoplightio-report/img/scenarios.png";
	}

	@Override
	public String getUrlName() {
		return "stoplightBuildReport";
	}

	public int getBuildNumber() {
        return this.build.number;
	}	

	public Date getBuildDate() {
        return this.build.getTime();
	}	
	
	public Run<?, ?> getBuild() {
        return build;
	}	
	
	public Collection getCollection() {
		return collection;
	}
	
	public String getResult() {
		Result r = build.getResult();
		if(r!=null) {
			return r.toString();
		}
		return "";
	}
	
	public boolean isSuccess() {
		Result r = build.getResult();
		if(r!=null) {
			return r==Result.SUCCESS;
		}
		return false;
	}

	public boolean getIsSuccess() {
		Result r = build.getResult();
		if(r!=null) {
			return r==Result.SUCCESS;
		}
		return false;
	}
	
	/**
	 * Returns the total count of tests 
	 * @return the total count of tests
	 */
	public int getAssertionsCount() {
		return collection.getTotalTests();	
	}
	
	/**
	 * Returns the total count of succeeded tests 
	 * @return the total count of succeeded tests
	 */
	public int getSucceededAssertionsCount() {
		return collection.getSucceededTests();	
	}
	
	/**
	 * Returns the total count of failed tests 
	 * @return the total count of failed tests
	 */
	public int getFailedAssertionsCount() {
		return collection.getTotalTests() - collection.getSucceededTests();	
	}
	
	/**
	 * Returns the count of steps of this build
	 * @return the count of steps of this build
	 */
	public int getScenariosCount() {
		return collection.getScenarios().size();
	}
	
	/**
	 * Returns the count of steps of this build
	 * @return the count of steps of this build
	 */
	public int getStepsCount() {
		int count = 0;
		List<Scenario> scenList = collection.getScenarios();	
		for(Scenario s : scenList) {
			count += s.getSteps().size();
		}
		return count;
	}

	/**
	 * Returns the total count of steps without any assertion
	 * @return the total count of steps without any assertion
	 */
	public int getNoAssertionCount() {
		int count = 0;
		List<Scenario> scenList = collection.getScenarios();	
		for(Scenario scen : scenList) {
			for(Step st : scen.getSteps()) {
				if(st.getAssertions().isEmpty()) {
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public java.util.Collection<? extends Action> getProjectActions() {
		List<StoplightReportProjectAction> projectActions = new ArrayList<>();
        projectActions.add(new StoplightReportProjectAction(build.getParent()));
		return projectActions;
	}
		
}

