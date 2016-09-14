package hdispatch.core.dispatch.service.impl;

import hdispatch.core.dispatch.dto.workflow.Workflow;
import hdispatch.core.dispatch.dto.workflow.WorkflowJob;
import hdispatch.core.dispatch.dto.workflow.WorkflowProperty;
import hdispatch.core.dispatch.service.WorkflowService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Map;

import static hdispatch.core.dispatch.utils.Constants.RET_SUCCESS;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class WorkflowServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private WorkflowService workflowService;

    private Workflow workflow;


    @Before
    public void before() throws Exception {

        workflow = new Workflow()
                .setLayerId(1L)
                .setThemeId(1L)
                .setDescription("test")
                .setName("testFlow")
                .setProperties(Arrays.asList(new WorkflowProperty().setWorkflowName("test").setWorkflowValue("test")))
                .setJobs(Arrays.asList(
                        new WorkflowJob().setJobSource(1L).setJobType("job").setWorkflowJobId("job1"),
                        new WorkflowJob().setJobSource(2L).setJobType("job").setWorkflowJobId("job2"),
                        new WorkflowJob().setJobSource(3L).setJobType("job").setWorkflowJobId("job3")
                ));
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testCreateWorkflow() throws Exception {
        Map<String, Object> result = workflowService.createWorkflow(workflow);
        Assert.assertNotNull(result.get(RET_SUCCESS));
    }

    /**
     * Method: updateWorkFlow(Workflow workflow)
     */
    @Ignore
    public void testUpdateWorkFlow() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: generateWorkflow(long workflowId)
     */
    @Ignore
    public void testGenerateWorkflow() throws Exception {
//TODO: Test goes here... 
    }


    /**
     * Method: generateWorkflow(Workflow workflow, Collection<Job> jobStore)
     */
    @Test
    public void testGenerateWorkflowFile() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = WorkflowServiceImpl.getClass().getMethod("generateWorkflow", Workflow.class, Collection<Job>.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

} 
