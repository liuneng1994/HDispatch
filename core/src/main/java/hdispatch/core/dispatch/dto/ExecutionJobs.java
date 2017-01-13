package hdispatch.core.dispatch.dto;

public class ExecutionJobs extends ExecutionJobsKey {
    private Integer projectId;

    private Integer version;

    private String flowId;

    private Long startTime;

    private Long endTime;

    private Byte status;

    private String running;


    private String lang;

    private String meaning;

    private String nestedId;

    private Integer parentId;

    public String getNestedId() {
        return nestedId;
    }

    public void setNestedId(String nestedId) {
        this.nestedId = nestedId;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getRunning() {
        return running;
    }

    public void setRunning(String running) {
        this.running = running;
    }

    public Integer getProject_id() {
        return projectId;
    }

    public void setProject_id(Integer project_id) {
        this.projectId = project_id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getFlow_id() {
        return flowId;
    }

    public void setFlow_id(String flow_id) {
        this.flowId = flow_id == null ? null : flow_id.trim();
    }

    public Long getStart_time() {
        return startTime;
    }

    public void setStart_time(Long start_time) {
        this.startTime = start_time;
    }

    public Long getEnd_time() {
        return endTime;
    }

    public void setEnd_time(Long end_time) {
        this.endTime = end_time;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "ExecutionJobs{" +
                "project_id=" + projectId +
                ", version=" + version +
                ", parentId=" + parentId +
                ", flow_id='" + flowId + '\'' +
                ", start_time=" + startTime +
                ", end_time=" + endTime +
                ", status=" + status +
                ", running='" + running + '\'' +
                ", lang='" + lang + '\'' +
                ", meaning='" + meaning + '\'' +
                ", nestedId='" + nestedId + '\'' +

                '}';
    }
}