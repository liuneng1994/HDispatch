package hdispatch.core.dispatch.dto;

public class ExecutionJobsKey {
    private Integer attempt;

    private Integer exec_id;

    private String job_id;

    public Integer getAttempt() {
        return attempt;
    }

    public void setAttempt(Integer attempt) {
        this.attempt = attempt;
    }

    public Integer getExec_id() {
        return exec_id;
    }

    public void setExec_id(Integer exec_id) {
        this.exec_id = exec_id;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id == null ? null : job_id.trim();
    }
}