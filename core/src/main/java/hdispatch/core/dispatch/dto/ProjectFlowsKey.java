package hdispatch.core.dispatch.dto;

public class ProjectFlowsKey {
    private String flowId;

    private Integer projectId;

    private Integer version;

    public String getFlow_id() {
        return flowId;
    }

    public void setFlow_id(String flow_id) {
        this.flowId = flow_id == null ? null : flow_id.trim();
    }

    public Integer getProject_id() {
        return projectId;
    }

    public void setProject_id(Integer project_id) {
        this.projectId = project_id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}