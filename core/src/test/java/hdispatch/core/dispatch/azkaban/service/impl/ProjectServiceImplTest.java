package hbi.core.azkaban.service.impl;

import hbi.core.azkaban.entity.project.SimpleProject;
import hbi.core.azkaban.service.ProjectService;
import org.json.JSONObject;
import org.junit.*;

import java.util.List;

public class ProjectServiceImplTest {
    private ProjectService projectService = new ProjectServiceImpl();

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: createProject(String projectName, String description)
     */
    @Ignore
    public void testCreateProject() throws Exception {
    }

    /**
     * Method: getAllProjects()
     */
    @Test
    public void testGetAllProjects() throws Exception {
        List<SimpleProject> list = projectService.getAllProjects();
        Assert.assertNotNull(list);
        Assert.assertFalse(list.isEmpty());
    }

    /**
     * Method: deleteProject(String projectName)
     */
    @Ignore
    public void testDeleteProject() throws Exception {
    }
} 
