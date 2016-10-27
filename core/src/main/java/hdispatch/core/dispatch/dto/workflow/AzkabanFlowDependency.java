package hdispatch.core.dispatch.dto.workflow;

/**
 * Created by liuneng on 2016/10/26.
 */
public class AzkabanFlowDependency {
    private String projectName;
    private Integer projectId;
    private String flowId;
    private String deptProjectName;
    private Integer deptProjectId;
    private String deptFlowId;

    public String getProjectName() {
        return projectName;
    }

    public AzkabanFlowDependency setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public AzkabanFlowDependency setProjectId(Integer projectId) {
        this.projectId = projectId;
        return this;
    }

    public String getFlowId() {
        return flowId;
    }

    public AzkabanFlowDependency setFlowId(String flowId) {
        this.flowId = flowId;
        return this;
    }

    public String getDeptProjectName() {
        return deptProjectName;
    }

    public AzkabanFlowDependency setDeptProjectName(String deptProjectName) {
        this.deptProjectName = deptProjectName;
        return this;
    }

    public Integer getDeptProjectId() {
        return deptProjectId;
    }

    public AzkabanFlowDependency setDeptProjectId(Integer deptProjectId) {
        this.deptProjectId = deptProjectId;
        return this;
    }

    public String getDeptFlowId() {
        return deptFlowId;
    }

    public AzkabanFlowDependency setDeptFlowId(String deptFlowId) {
        this.deptFlowId = deptFlowId;
        return this;
    }
}
