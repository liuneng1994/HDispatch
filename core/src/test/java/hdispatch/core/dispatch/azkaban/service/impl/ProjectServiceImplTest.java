package hdispatch.core.dispatch.azkaban.service.impl;

import hdispatch.core.dispatch.azkaban.entity.project.SimpleProject;
import hdispatch.core.dispatch.azkaban.service.ProjectService;
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
