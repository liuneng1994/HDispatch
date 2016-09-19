package hdispatch.core.dispatch.dto.workflow;

/**
 * Created by 刘能 on 2016/9/12.
 */
public class WorkflowJob {
    private String workflowJobId;
    private Long workflowId;
    private Long jobSource;
    private String jobType;
    private String parentsJobId;

    public Long getWorkflowId() {
        return workflowId;
    }

    public WorkflowJob setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
        return this;
    }

    public Long getJobSource() {
        return jobSource;
    }

    public WorkflowJob setJobSource(Long jobSource) {
        this.jobSource = jobSource;
        return this;
    }

    public String getJobType() {
        return jobType;
    }

    public WorkflowJob setJobType(String jobType) {
        this.jobType = jobType;
        return this;
    }

    public String getWorkflowJobId() {
        return workflowJobId;
    }

    public WorkflowJob setWorkflowJobId(String workflowJobId) {
        this.workflowJobId = workflowJobId;
        return this;
    }

    public String getParentsJobId() {
        return parentsJobId;
    }

    public WorkflowJob setParentsJobId(String parentsJobId) {
        this.parentsJobId = parentsJobId;
        return this;
    }

    @Override
    public String toString() {
        return "WorkflowJob{" +
                "workflowJobId='" + workflowJobId + '\'' +
                ", workflowId=" + workflowId +
                ", jobSource=" + jobSource +
                ", jobType='" + jobType + '\'' +
                ", parentsJobId='" + parentsJobId + '\'' +
                '}';
    }
}
