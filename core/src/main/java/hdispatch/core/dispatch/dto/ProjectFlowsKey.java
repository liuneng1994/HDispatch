package hdispatch.core.dispatch.dto;

public class ProjectFlowsKey {
    private String flow_id;

    private Integer project_id;

    private Integer version;

    public String getFlow_id() {
        return flow_id;
    }

    public void setFlow_id(String flow_id) {
        this.flow_id = flow_id == null ? null : flow_id.trim();
    }

    public Integer getProject_id() {
        return project_id;
    }

    public void setProject_id(Integer project_id) {
        this.project_id = project_id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}