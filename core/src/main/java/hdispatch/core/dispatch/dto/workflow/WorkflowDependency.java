package hdispatch.core.dispatch.dto.workflow;

/**
 * Created by liuneng on 2016/10/26.
 */
public class WorkflowDependency {
    //被依赖流的id
    private Long deptWorkflowId;
    //被依赖流的name
    private String deptWorkflowName;
    private String deptTheme;
    private String deptLayer;
    private String deptProjectName;
    private String deptFlowId;
    //依赖流的id
    private Long workflowId;
    //依赖流的name
    private String workflowName;

    public Long getDeptWorkflowId() {
        return deptWorkflowId;
    }

    public WorkflowDependency setDeptWorkflowId(Long deptWorkflowId) {
        this.deptWorkflowId = deptWorkflowId;
        return this;
    }

    public String getDeptWorkflowName() {
        return deptWorkflowName;
    }

    public WorkflowDependency setDeptWorkflowName(String deptWorkflowName) {
        this.deptWorkflowName = deptWorkflowName;
        return this;
    }

    public String getDeptTheme() {
        return deptTheme;
    }

    public WorkflowDependency setDeptTheme(String deptTheme) {
        this.deptTheme = deptTheme;
        return this;
    }

    public String getDeptLayer() {
        return deptLayer;
    }

    public WorkflowDependency setDeptLayer(String deptLayer) {
        this.deptLayer = deptLayer;
        return this;
    }

    public String getDeptProjectName() {
        return deptProjectName;
    }

    public WorkflowDependency setDeptProjectName(String deptProjectName) {
        this.deptProjectName = deptProjectName;
        return this;
    }

    public String getDeptFlowId() {
        return deptFlowId;
    }

    public WorkflowDependency setDeptFlowId(String deptFlowId) {
        this.deptFlowId = deptFlowId;
        return this;
    }

    public Long getWorkflowId() {
        return workflowId;
    }

    public WorkflowDependency setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
        return this;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public WorkflowDependency setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
        return this;
    }
}
