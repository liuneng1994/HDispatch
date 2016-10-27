package hdispatch.core.dispatch.dto.workflow;

/**
 * Created by liuneng on 2016/10/26.
 */
public class WorkflowMutex {
    private Long mutexWorkflowId;
    private String mutexWorkflowName;
    private String mutexTheme;
    private String mutexLayer;
    private String mutexProjectName;
    private String mutexFlowId;
    private Long workflowId;
    private String workflowName;

    public Long getMutexWorkflowId() {
        return mutexWorkflowId;
    }

    public WorkflowMutex setMutexWorkflowId(Long mutexWorkflowId) {
        this.mutexWorkflowId = mutexWorkflowId;
        return this;
    }

    public String getMutexWorkflowName() {
        return mutexWorkflowName;
    }

    public WorkflowMutex setMutexWorkflowName(String mutexWorkflowName) {
        this.mutexWorkflowName = mutexWorkflowName;
        return this;
    }

    public String getMutexTheme() {
        return mutexTheme;
    }

    public WorkflowMutex setMutexTheme(String mutexTheme) {
        this.mutexTheme = mutexTheme;
        return this;
    }

    public String getMutexLayer() {
        return mutexLayer;
    }

    public WorkflowMutex setMutexLayer(String mutexLayer) {
        this.mutexLayer = mutexLayer;
        return this;
    }

    public String getMutexProjectName() {
        return mutexProjectName;
    }

    public WorkflowMutex setMutexProjectName(String mutexProjectName) {
        this.mutexProjectName = mutexProjectName;
        return this;
    }

    public String getMutexFlowId() {
        return mutexFlowId;
    }

    public WorkflowMutex setMutexFlowId(String mutexFlowId) {
        this.mutexFlowId = mutexFlowId;
        return this;
    }

    public Long getWorkflowId() {
        return workflowId;
    }

    public WorkflowMutex setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
        return this;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public WorkflowMutex setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
        return this;
    }
}
