package hdispatch.core.dispatch.dto;


public class HdispatchSchedule {
    private Long sch_id;

    private Integer schedule_id;

    private Integer project_id;

    private String flow_id;

    private String submit_date;

    private String project_name;
    
    
    public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public Long getSch_id() {
        return sch_id;
    }

    public void setSch_id(Long sch_id) {
        this.sch_id = sch_id;
    }

    public Integer getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(Integer schedule_id) {
        this.schedule_id = schedule_id;
    }

    public Integer getProject_id() {
        return project_id;
    }

    public void setProject_id(Integer project_id) {
        this.project_id = project_id;
    }

    public String getFlow_id() {
        return flow_id;
    }

    public void setFlow_id(String flow_id) {
        this.flow_id = flow_id == null ? null : flow_id.trim();
    }

    public String getSubmit_date() {
        return submit_date;
    }

    public void setSubmit_date(String submit_date) {
        this.submit_date = submit_date;
    }
}