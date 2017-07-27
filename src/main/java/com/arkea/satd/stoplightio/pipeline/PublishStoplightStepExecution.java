package com.arkea.satd.stoplightio.pipeline;

import javax.inject.Inject;

import org.jenkinsci.plugins.workflow.steps.AbstractSynchronousStepExecution;
import org.jenkinsci.plugins.workflow.steps.StepContextParameter;

import com.arkea.satd.stoplightio.StoplightReportPublisher;

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.Run;
import hudson.model.TaskListener;

/**
 * @author Nicolas TISSERAND
 */
public class PublishStoplightStepExecution extends AbstractSynchronousStepExecution<Void> {

	private static final long serialVersionUID = 1L;

	@StepContextParameter
    private transient FilePath ws;

    @StepContextParameter
    private transient Run<?, ?> build;

    @StepContextParameter
    private transient Launcher launcher;

    @StepContextParameter
    private transient TaskListener listener;

    @Inject
    private transient PublishStoplightStep step;


    @Override
    protected Void run() throws Exception {
    	
    	listener.getLogger().println("Running PublishStoplight step.");
    	listener.getLogger().println("Mode : " + step.getConsoleOrFile());
    	listener.getLogger().println("ResultFile : " + step.getResultFile());
    	
        new StoplightReportPublisher(step.getConsoleOrFile(), step.getResultFile()).perform(build, ws, launcher, listener);
        return null;
    }
}
