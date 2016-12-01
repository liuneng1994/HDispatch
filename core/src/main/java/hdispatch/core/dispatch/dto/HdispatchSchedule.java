package hdispatch.core.dispatch.dto;


public class HdispatchSchedule {
    private Long schId;

    private Integer scheduleId;

    private Integer projectId;

    private String flowId;

    private String submitDate;

    private String projectName;
    
    private Long themeId;

    public Long getTheme_id() {
        return themeId;
    }

    public void setTheme_id(Long theme_id) {
        this.themeId = theme_id;
    }

    public String getProject_name() {
		return projectName;
	}

	public void setProject_name(String project_name) {
		this.projectName = project_name;
	}

	public Long getSch_id() {
        return schId;
    }

    public void setSch_id(Long sch_id) {
        this.schId = sch_id;
    }

    public Integer getSchedule_id() {
        return scheduleId;
    }

    public void setSchedule_id(Integer schedule_id) {
        this.scheduleId = schedule_id;
    }

    public Integer getProject_id() {
        return projectId;
    }

    public void setProject_id(Integer project_id) {
        this.projectId = project_id;
    }

    public String getFlow_id() {
        return flowId;
    }

    public void setFlow_id(String flow_id) {
        this.flowId = flow_id == null ? null : flow_id.trim();
    }

    public String getSubmit_date() {
        return submitDate;
    }

    public void setSubmit_date(String submit_date) {
        this.submitDate = submit_date;
    }
}