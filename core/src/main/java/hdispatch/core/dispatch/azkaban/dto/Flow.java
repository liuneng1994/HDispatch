package hdispatch.core.dispatch.azkaban.dto;

/**
 * Created by liuneng on 16-9-1.
 */
public class Flow {
    private int projectId;
    private int version;
    private String flowId;
    private Long modifiedTime;
    private String json;

    public int getProjectId() {
        return projectId;
    }

    public Flow setProjectId(int projectId) {
        this.projectId = projectId;
        return this;
    }

    public int getVersion() {
        return version;
    }

    public Flow setVersion(int version) {
        this.version = version;
        return this;
    }

    public String getFlowId() {
        return flowId;
    }

    public Flow setFlowId(String flowId) {
        this.flowId = flowId;
        return this;
    }

    public Long getModifiedTime() {
        return modifiedTime;
    }

    public Flow setModifiedTime(Long modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getJson() {
        return json;
    }

    public Flow setJson(String json) {
        this.json = json;
        return this;
    }
}
