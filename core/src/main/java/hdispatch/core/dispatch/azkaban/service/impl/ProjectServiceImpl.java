package hdispatch.core.dispatch.azkaban.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import hdispatch.core.dispatch.azkaban.entity.project.SimpleProject;
import hdispatch.core.dispatch.azkaban.service.ProjectService;
import hdispatch.core.dispatch.azkaban.util.RequestUrl;
import hdispatch.core.dispatch.azkaban.util.RequestUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 刘能 on 2016/8/31.
 */
@Component
public class ProjectServiceImpl implements ProjectService {
    private Logger logger = Logger.getLogger(ProjectServiceImpl.class);
    private Gson gson = new Gson();

    @Override
    public boolean createProject(String projectName, String description) {
        HttpResponse<JsonNode> response;
        try {
            response = RequestUtils.get(RequestUrl.PROJECT_MANAGER).queryString("action", "create")
                    .queryString("name", projectName)
                    .queryString("description", description).asJson();
        } catch (UnirestException e) {
            logger.error("创建工程失败", e);
            throw new IllegalStateException("创建工程失败", e);
        }
        String status = response.getBody().getObject().getString("status");
        return "success".equals(status);
    }

    @Override
    public List<SimpleProject> getAllProjects() {
        HttpResponse<String> response;
        try {
            response = RequestUtils.get(RequestUrl.INDEX)
                    .queryString("ajax", "fetchallprojects")
                    .asString();
        } catch (UnirestException e) {
            logger.error("查询工程列表失败", e);
            throw new IllegalStateException("查询工程列表失败", e);
        }
        Map<String, List<SimpleProject>> projects = gson.fromJson(response.getBody(), new TypeToken<Map<String, List<SimpleProject>>>() {
        }.getType());
        return projects.get("projects");
    }

    @Override
    public boolean deleteProject(String projectName) {
        try {
            RequestUtils.get(RequestUrl.INDEX)
                    .queryString("delete", "true")
                    .queryString("project", projectName)
                    .asString();
        } catch (UnirestException e) {
            logger.error("刪除工程失败", e);
            throw new IllegalStateException("刪除工程失败", e);
        }
        return true;
    }

    @Override
    public Map<String, String> uploadProjectFile(String projectName, File projectFile) {
        HttpResponse<JsonNode> response;
        try {
            response = RequestUtils.post("/manager?ajax=upload").header("Content-Type", "multipart/mixed")
                    .field("project", projectName)
                    .field(projectFile.getName(), projectFile, "application/zip").asJson();
        } catch (UnirestException e) {
            logger.error("上传工程失败", e);
            throw new IllegalStateException("上传工程失败", e);
        }
        JSONObject object = response.getBody().getObject();
        Map<String, String> ret = new HashMap<>();
        ret.put("error", object.getString("error"));
        ret.put("projectId", object.getString("projectId"));
        ret.put("version", object.getString("version"));
        return ret;
    }
}
