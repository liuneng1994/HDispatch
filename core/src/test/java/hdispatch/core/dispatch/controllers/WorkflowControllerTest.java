package hdispatch.core.dispatch.controllers;

import hdispatch.core.dispatch.service.WorkflowService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

/**
 * WorkflowController Tester.
 *
 * @author neng.liu@hand-china.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/applicationContext.xml","classpath:/spring/applicationContext-*.xml","classpath:/spring/appServlet/servlet-context.xml"})
@Transactional
@Rollback
public class WorkflowControllerTest{
    @InjectMocks
    private WorkflowController workflowController;

    private MockMvc mockMvc;

    @Mock
    private WorkflowService workflowService;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(workflowController).build();
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: createWorkflow(@RequestBody Workflow workflow)
     */
    @Test
    public void testCreateWorkflow() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: saveWorkflowGraph(@RequestParam("workflowId") long workflowId, @RequestParam("graph") String graph)
     */
    @Test
    public void testSaveWorkflowGraph() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: generateWorkflow(@RequestParam(name = "workflowId") long workflowId)
     */
    @Test
    public void testGenerateWorkflow() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: existWorflow(@RequestParam(name = "name") String name)
     */
    @Test
    public void testExistWorflow() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: queryWorkflow(@RequestParam(name = "themeId", required = false) Long themeId, @RequestParam(name = "layerId", required = false) Long layerId, @RequestParam(name = "workflowName", required = false) String workflowName, @RequestParam(name = "description", required = false) String description, @RequestParam(name = "page", required = false, defaultValue = "1") int page, @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize, HttpServletRequest servletRequest)
     */
    @Test
    public void testQueryWorkflow() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: queryOperateWorkflow(@RequestParam(name = "themeId", required = false) Long themeId, @RequestParam(name = "layerId", required = false) Long layerId, @RequestParam(name = "workflowName", required = false) String workflowName, @RequestParam(name = "description", required = false) String description, @RequestParam(name = "page", required = false, defaultValue = "1") int page, @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize, HttpServletRequest servletRequest)
     */
    @Test
    public void testQueryOperateWorkflow() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getWorkflow(@RequestParam(name = "workflowId") long workflowId)
     */
    @Test
    public void testGetWorkflow() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: updateWorkflow(@RequestBody Workflow workflow)
     */
    @Test
    public void testUpdateWorkflowWorkflow() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: updateWorkflow(@RequestBody List<Integer> ids)
     */
    @Test
    public void testUpdateWorkflowIds() throws Exception {
//TODO: Test goes here... 
    }


} 
