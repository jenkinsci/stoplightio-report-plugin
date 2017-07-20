package com.arkea.satd.stoplightio.pipeline;

import hudson.Extension;

import org.jenkinsci.Symbol;
import org.jenkinsci.plugins.workflow.steps.AbstractStepDescriptorImpl;
import org.jenkinsci.plugins.workflow.steps.AbstractStepImpl;
import org.kohsuke.stapler.DataBoundConstructor;

import javax.annotation.CheckForNull;

/**
 * @author Nicolas TISSERAND
 */
public class PublishStoplightStep extends AbstractStepImpl {

    private final String consoleOrFile;
    private final String resultFile;

    @DataBoundConstructor
    public PublishStoplightStep(@CheckForNull String consoleOrFile, @CheckForNull String resultFile) {
        this.consoleOrFile = consoleOrFile;
        this.resultFile = resultFile;
    }

    public String getResultFile() {
        return resultFile;
    }

    public String getConsoleOrFile() {
		return consoleOrFile;
	}

	@Extension //@Symbol("publishStoplight")
    public static class DescriptorImpl extends AbstractStepDescriptorImpl {

        public DescriptorImpl() {
            super(PublishStoplightStepExecution.class);
        }

        @Override
        public String getFunctionName() {
            return "publishStoplight";
        }


        @Override
        public String getDisplayName() {
            return "Publish Stoplight reports";
        }
    }
}
