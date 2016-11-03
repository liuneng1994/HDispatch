package hdispatch.core.dispatch.controllers;

import hdispatch.core.dispatch.TestUtil;
import hdispatch.core.dispatch.dto.workflow.Workflow;
import hdispatch.core.dispatch.service.WorkflowService;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;
import org.mvel2.util.Make;
import org.springframework.context.MessageSource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import java.util.HashMap;
import java.util.Map;

import static hdispatch.core.dispatch.utils.Constants.RET_ERROR;
import static hdispatch.core.dispatch.utils.Constants.RET_SUCCESS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * WorkflowController Tester.
 *
 * @author neng.liu@hand-china.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/applicationContext.xml","classpath:/spring/applicationContext-*.xml","classpath:/spring/appServlet/servlet-context.xml"})
@Transactional
@Rollback
public class WorkflowControllerTest {
    @InjectMocks
    private WorkflowController workflowController;

    private MockMvc mockMvc;

    @Mock
    private Validator validator;

    @Mock
    private MessageSource messageSource;

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
    public void testCreateWorkflowInHappy() throws Exception {
        Workflow workflow = new Workflow().setWorkflowId(1L);
        Map<String, Object> map = new HashMap<>();
        map.put(RET_SUCCESS, "1");
        when(workflowService.getWorkflowByName(anyString())).thenReturn(null);
        when(workflowService.createWorkflow(anyObject())).thenReturn(map);
        mockMvc.perform(post("/dispatcher/workflow/create")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content("{\"themeId\":1,\"layerId\":1,\"name\":\"aaa\",\"description\":\"aaa\",\"jobs\":[{\"workflowJobId\":\"a\",\"jobSource\":\"29\",\"jobType\":\"job\",\"parentsJobId\":\"\"}],\"graph\":\"{\\\"graph\\\":{\\\"cells\\\":[{\\\"type\\\":\\\"basic.Rect\\\",\\\"position\\\":{\\\"x\\\":190,\\\"y\\\":80},\\\"size\\\":{\\\"width\\\":100,\\\"height\\\":50},\\\"angle\\\":0,\\\"id\\\":\\\"e3682a22-c627-4fa9-937e-5c58750699e5\\\",\\\"jobId\\\":\\\"29\\\",\\\"z\\\":1,\\\"attrs\\\":{\\\"rect\\\":{\\\"fill\\\":\\\"lightgray\\\",\\\"stroke\\\":\\\"black\\\",\\\"stroke-width\\\":\\\"1\\\",\\\"stroke-opacity\\\":0.7,\\\"rx\\\":3,\\\"ry\\\":3},\\\"text\\\":{\\\"fill\\\":\\\"black\\\",\\\"text\\\":\\\"a\\\"}}}]},\\\"jobs\\\":{\\\"jobs\\\":{\\\"keys\\\":[\\\"e3682a22-c627-4fa9-937e-5c58750699e5\\\"],\\\"values\\\":[{\\\"themeId\\\":null,\\\"layerId\\\":0,\\\"name\\\":\\\"a\\\",\\\"type\\\":\\\"job\\\",\\\"jobSource\\\":\\\"29\\\",\\\"dept\\\":[]}]},\\\"names\\\":{\\\"keys\\\":[\\\"a\\\"],\\\"values\\\":[\\\"e3682a22-c627-4fa9-937e-5c58750699e5\\\"]},\\\"depts\\\":{\\\"keys\\\":[],\\\"values\\\":[]}}}\"}"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("success", is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("message", is("1")));
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
