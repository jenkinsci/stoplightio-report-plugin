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
