package hdispatch.core.dispatch.dto;

public class ExecutionJobsKey {
    private Integer attempt;

    private Integer execId;

    private String jobId;

    public Integer getAttempt() {
        return attempt;
    }

    public void setAttempt(Integer attempt) {
        this.attempt = attempt;
    }

    public Integer getExec_id() {
        return execId;
    }

    public void setExec_id(Integer exec_id) {
        this.execId = exec_id;
    }

    public String getJob_id() {
        return jobId;
    }

    public void setJob_id(String job_id) {
        this.jobId = job_id == null ? null : job_id.trim();
    }
}