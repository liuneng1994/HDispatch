package hdispatch.core.dispatch.azkaban.service;


import hdispatch.core.dispatch.azkaban.entity.project.SimpleProject;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by 刘能 on 2016/8/31.
 */
public interface ProjectService {
    boolean createProject(String projectName, String description);

    List<SimpleProject> getAllProjects();

    boolean deleteProject(String projectName);

    Map<String, String> uploadProjectFile(String projectName, File projectFile);
}
