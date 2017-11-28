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

import javax.annotation.CheckForNull;

import org.jenkinsci.plugins.workflow.steps.AbstractStepDescriptorImpl;
import org.jenkinsci.plugins.workflow.steps.AbstractStepImpl;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;

import hudson.Extension;
import hudson.util.FormValidation;

/**
 * @author Nicolas TISSERAND
 */
public class PublishStoplightStep extends AbstractStepImpl {

    private final String consoleOrFile;
    private String resultFile = "";
    
    @DataBoundConstructor
    public PublishStoplightStep(@CheckForNull String consoleOrFile) {
        this.consoleOrFile = consoleOrFile;
    }

    @DataBoundSetter
    public void setResultFile(String resultFile) {
        this.resultFile = resultFile==null?"":resultFile;
    }
    
    
    public String getResultFile() {
        return resultFile;
    }

    public String getConsoleOrFile() {
		return consoleOrFile;
	}

	@Extension
    public static class DescriptorImpl extends AbstractStepDescriptorImpl {

        public DescriptorImpl() {
            super(PublishStoplightStepExecution.class);
        }
        
        /**
         * Performs on-the-fly validation of the form field 'name'.
         *
         * @param value
         *      This parameter receives the value that the user has typed.
         * @return
         *      Indicates the outcome of the validation. This is sent to the browser.
         *      <p>
         *      Note that returning {@link FormValidation#error(String)} does not
         *      prevent the form from being saved. It just means that a message
         *      will be displayed to the user. 
         */
        public FormValidation doCheckResultFile(@QueryParameter String value) {
            if (value.length() == 0)
                return FormValidation.error("Please set a path");
            if (value.length() < 4)
                return FormValidation.warning("Isn't the path too short?");
            return FormValidation.ok();
        }        
        
        @Override
        public String getFunctionName() {
            return "publishStoplight";
        }

        @Override
        public String getDisplayName() {
            return "Publish Stoplight Report";
        }
    }
}
