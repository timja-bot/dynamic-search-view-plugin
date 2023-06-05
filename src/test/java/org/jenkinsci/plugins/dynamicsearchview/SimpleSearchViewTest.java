/*
 * The MIT License
 *
 * Copyright (c) 2015 Oleg Nenashev.
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
package org.jenkinsci.plugins.dynamicsearchview;

import org.htmlunit.html.HtmlPage;
import com.synopsys.arc.jenkinsci.plugins.dynamic_search.views.SimpleSearchView;
import hudson.model.FreeStyleProject;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.Bug;
import org.jvnet.hudson.test.JenkinsRule;

/**
 * Tests of {@link SimpleSearchView}.
 * @author Oleg Nenashev
 */
public class SimpleSearchViewTest {
    
    @Rule
    public JenkinsRule j = new JenkinsRule();
    
    //TODO: Change @Bug to @Issue after Jenkins core upgrade
    @Test
    @Bug(30663)
    public void spotCheck() throws Exception {
        // Create two sample projects
        FreeStyleProject projectA = j.createFreeStyleProject("a");
        FreeStyleProject projectB = j.createFreeStyleProject("b");
        
        // Create view taking all jobs
        SimpleSearchView view = new SimpleSearchView("testView");
        view.setDefaultIncludeRegex(".*");
        j.jenkins.addView(view);
       
        // Perform a request
        JenkinsRule.WebClient webClient = j.createWebClient(); 
        HtmlPage res = webClient.goTo(view.getUrl());
        
        // Reload jenkins config and send the request again
        j.jenkins.reload();
        res = webClient.goTo(view.getUrl());
    }
}
