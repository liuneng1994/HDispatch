package hdispatch.core.dispatch.dto.workflow;

/**
 * Created by 刘能 on 2016/9/12.
 */
public class WorkflowProperty {
    private Long workflowId;
    private String workflowName;
    private String workflowValue;

    public Long getWorkflowId() {
        return workflowId;
    }

    public WorkflowProperty setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
        return this;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public WorkflowProperty setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
        return this;
    }

    public String getWorkflowValue() {
        return workflowValue;
    }

    public WorkflowProperty setWorkflowValue(String workflowValue) {
        this.workflowValue = workflowValue;
        return this;
    }
}
