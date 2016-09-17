package hdispatch.core.dispatch.dto.workflow;

/**
 * Created by 刘能 on 2016/9/12.
 */
public class WorkflowProperty {
    private Long workflowId;
    private String workflowPropertyName;
    private String workflowPropertyValue;

    public Long getWorkflowId() {
        return workflowId;
    }

    public WorkflowProperty setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
        return this;
    }

    public String getWorkflowPropertyName() {
        return workflowPropertyName;
    }

    public WorkflowProperty setWorkflowPropertyName(String workflowPropertyName) {
        this.workflowPropertyName = workflowPropertyName;
        return this;
    }

    public String getWorkflowPropertyValue() {
        return workflowPropertyValue;
    }

    public WorkflowProperty setWorkflowPropertyValue(String workflowPropertyValue) {
        this.workflowPropertyValue = workflowPropertyValue;
        return this;
    }

    @Override
    public String toString() {
        return "WorkflowProperty{" +
                "workflowId=" + workflowId +
                ", workflowPropertyName='" + workflowPropertyName + '\'' +
                ", workflowPropertyValue='" + workflowPropertyValue + '\'' +
                '}';
    }
}
