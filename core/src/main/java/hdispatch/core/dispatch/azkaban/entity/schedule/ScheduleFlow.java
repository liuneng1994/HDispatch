package hdispatch.core.dispatch.azkaban.entity.schedule;

public class ScheduleFlow {
	 private String nextExecTime;
	 private String period;
	 private String submitUser;
	 private String scheduleId;
	 private String firstSchedTime;
	 private boolean hasSla;
	 private String flowId;
	 private String projectName;
	 private Integer projectId;
	 private Long themeId;

	public Long getThemeId() {
		return themeId;
	}

	public void setThemeId(Long themeId) {
		this.themeId = themeId;
	}

	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public boolean isHasSla() {
		return hasSla;
	}
	public void setHasSla(boolean hasSla) {
		this.hasSla = hasSla;
	}
	public String getNextExecTime() {
		return nextExecTime;
	}
	public void setNextExecTime(String nextExecTime) {
		this.nextExecTime = nextExecTime;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getSubmitUser() {
		return submitUser;
	}
	public void setSubmitUser(String submitUser) {
		this.submitUser = submitUser;
	}
	public String getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}
	public String getFirstSchedTime() {
		return firstSchedTime;
	}
	public void setFirstSchedTime(String firstSchedTime) {
		this.firstSchedTime = firstSchedTime;
	}
	 
}
