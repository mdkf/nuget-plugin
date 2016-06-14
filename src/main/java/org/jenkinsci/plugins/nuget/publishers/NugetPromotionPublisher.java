/*
 * The MIT License
 *
 * Copyright 2016 MFowler.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.jenkinsci.plugins.nuget.publishers;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Publisher;
import hudson.util.FormValidation;
import java.util.List;
import jenkins.model.Jenkins;
import org.jenkinsci.plugins.nuget.Messages;
import org.jenkinsci.plugins.nuget.NugetPublication;
import org.jenkinsci.plugins.nuget.Utils.Validations;
import org.kohsuke.stapler.DataBoundConstructor;

import org.kohsuke.stapler.QueryParameter;

/**
 *
 * @author MFowler
 */
public class NugetPromotionPublisher extends NugetPublisher{
   
//    private boolean useWorkspaceInPromotion; 
    
    @DataBoundConstructor
    public NugetPromotionPublisher(String name, String packagesPattern, String nugetPublicationName, String packagesExclusionPattern, boolean useWorkspaceInPromotion) {
        super(name, packagesPattern, nugetPublicationName, packagesExclusionPattern, useWorkspaceInPromotion);
//        this.useWorkspaceInPromotion=useWorkspaceInPromotion;
    }
    
/*  
    @Override
    public boolean getUseWorkspaceInPromotion(){
        return useWorkspaceInPromotion;
    }
 */   

@Extension
    public static final class NugetPromotionPublisherDescriptor extends BuildStepDescriptor<Publisher> {
        private static final String PROMOTION_JOB_TYPE = "hudson.plugins.promoted_builds.PromotionProcess";
        
        public NugetPromotionPublisherDescriptor() {
            super(NugetPromotionPublisher.class);
            load();
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> type) {
            return PROMOTION_JOB_TYPE.equals(type.getCanonicalName());
        }

        @Override
        public String getDisplayName() {
            return Messages.NugetPromotionPublisher_DisplayName();
        }
        /*
        public NugetPromotionPublisherDescriptor getPublisherDescriptor() {
            return Jenkins.getInstance().getDescriptorByType(NugetPromotionPublisherDescriptor.class);
        } */
        
        public Class<? super NugetPromotionPublisher> getPublisherSuperclass(){
            return NugetPromotionPublisher.class.getSuperclass();
        }

        public List<NugetPublication> getPublications() {
            return NugetPublication.all();
        }

        public FormValidation doCheckName(@QueryParameter String value) {
            return Validations.mandatory(value);
        }

        public FormValidation doCheckPackagesPattern(@QueryParameter String value) {
            return Validations.mandatory(value);
        }

        public FormValidation doCheckNugetPublicationName(@QueryParameter String value) {
            return Validations.mandatory(value);
        }
    }
}