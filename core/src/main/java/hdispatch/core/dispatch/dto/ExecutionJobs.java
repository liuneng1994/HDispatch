package hdispatch.core.dispatch.dto;

public class ExecutionJobs extends ExecutionJobsKey {
    private Integer project_id;

    private Integer version;

    private String flow_id;

    private Long start_time;

    private Long end_time;

    private Byte status;

    private String running;
    
    
    public String getRunning() {
		return running;
	}

	public void setRunning(String running) {
		this.running = running;
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

    public String getFlow_id() {
        return flow_id;
    }

    public void setFlow_id(String flow_id) {
        this.flow_id = flow_id == null ? null : flow_id.trim();
    }

    public Long getStart_time() {
        return start_time;
    }

    public void setStart_time(Long start_time) {
        this.start_time = start_time;
    }

    public Long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Long end_time) {
        this.end_time = end_time;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}