package hdispatch.core.dispatch.azkaban.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import hdispatch.core.dispatch.azkaban.entity.project.SimpleProject;
import hdispatch.core.dispatch.azkaban.service.ProjectService;
import hdispatch.core.dispatch.azkaban.util.RequestUrl;
import hdispatch.core.dispatch.azkaban.util.RequestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by 刘能 on 2016/8/31.
 */
@Component
public class ProjectServiceImpl implements ProjectService {
    private Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);
    private Gson gson = new Gson();

    @Override
    public boolean createProject(String projectName, String description) {
        logger.info("创建工程:{}--{}", projectName, description);
        HttpResponse<JsonNode> response;
        try {
            response = RequestUtils.post(RequestUrl.PROJECT_MANAGER).field("action", "create")
                    .field("name", projectName)
                    .field("description", "Description must be English").asJson();
        } catch (UnirestException e) {
            logger.error("创建工程失败", e);
            throw new IllegalStateException("创建工程失败", e);
        }
        logger.info(response.getBody().toString());
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
    public String deleteProject(String projectName) {
        HttpResponse<String> response;
        try {
            response = RequestUtils.get(RequestUrl.INDEX)
                    .queryString("delete", "true")
                    .queryString("project", projectName)
                    .asString();
        } catch (UnirestException e) {
            logger.error("刪除工程失败", e);
            throw new IllegalStateException("刪除工程失败", e);
        }
        List<String> cookies = response.getHeaders().get("Set-Cookie");
        String cookie = String.join(",", cookies);
        String successMessage = Pattern.compile("azkaban\\.success\\.message=(.*);").matcher(cookie).group(0);
        String failMessage = Pattern.compile("azkaban\\.failure\\.message=(.*);").matcher(cookie).group(0);
        if (!StringUtils.isBlank(failMessage)) {
            return failMessage;
        } else {
            return null;
        }
    }

    @Override
    public Map<String, String> uploadProjectFile(String projectName, File projectFile) {
        HttpResponse<JsonNode> response;
        try {
            response = RequestUtils.post("/manager")
                    .field("ajax", "upload")
                    .field("project", projectName)
                    .field("file", new FileInputStream(projectFile), ContentType.APPLICATION_OCTET_STREAM, projectFile.getName()).asJson();
        } catch (UnirestException e) {
            logger.error("上传工程失败", e);
            throw new IllegalStateException("上传工程失败", e);
        } catch (FileNotFoundException e) {
            logger.error("工程文件找不到", e);
            throw new IllegalStateException("工程文件找不到", e);
        }
        logger.info(response.getBody().toString());
        JSONObject object = response.getBody().getObject();
        Map<String, String> ret = new HashMap<>();
        if (object.has("error")) {
            ret.put("error", object.getString("error"));
        }
        ret.put("projectId", object.getString("projectId"));
        ret.put("version", object.getString("version"));
        return ret;
    }
}
