/**

   Copyright 2010 OpenEngSB Division, Vienna University of Technology

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */
package org.openengsb.integrationtest.exam;

import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openengsb.core.common.Event;
import org.openengsb.core.common.context.ContextCurrentService;
import org.openengsb.core.workflow.RuleManager;
import org.openengsb.core.workflow.WorkflowService;
import org.openengsb.core.workflow.model.RuleBaseElementId;
import org.openengsb.core.workflow.model.RuleBaseElementType;
import org.openengsb.integrationtest.util.AbstractExamTestHelper;
import org.openengsb.integrationtest.util.BaseExamConfiguration;
import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.Inject;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.osgi.framework.BundleContext;

@RunWith(JUnit4TestRunner.class)
public class WorkflowIT extends AbstractExamTestHelper {

    @Inject
    private BundleContext bundleContext;

    @Configuration
    public static Option[] configuration() {
        List<Option> baseConfiguration = BaseExamConfiguration.getBaseExamOptions("../");
        BaseExamConfiguration.addEntireOpenEngSBPlatform(baseConfiguration);
        Option[] options = BaseExamConfiguration.convertOptionListToArray(baseConfiguration);
        return CoreOptions.options(options);
    }

    @Test
    public void testHasHelloRule() throws Exception {
        RuleManager ruleManager = retrieveService(bundleContext, RuleManager.class);
        Collection<RuleBaseElementId> list = ruleManager.list(RuleBaseElementType.Rule);
        Assert.assertTrue(list.contains(new RuleBaseElementId(RuleBaseElementType.Rule, "hello1")));
    }

    @Test
    public void testSendEvent() throws Exception {
        ContextCurrentService contextCurrentService = retrieveService(bundleContext, ContextCurrentService.class);
        contextCurrentService.createContext("42");

        WorkflowService workflowService = retrieveService(bundleContext, WorkflowService.class);
        Event e = new Event("42");
        workflowService.processEvent(e);
    }
}