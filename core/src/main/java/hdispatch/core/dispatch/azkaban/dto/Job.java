package hdispatch.core.dispatch.azkaban.dto;

/**
 * Created by 刘能 on 2016/9/2.
 */
public class Job {
    private int projectId;
    private int version;
    private String jobName;
    private long modifiedTime;
    private String property;

    public int getProjectId() {
        return projectId;
    }

    public Job setProjectId(int projectId) {
        this.projectId = projectId;
        return this;
    }

    public int getVersion() {
        return version;
    }

    public Job setVersion(int version) {
        this.version = version;
        return this;
    }

    public String getJobName() {
        return jobName;
    }

    public Job setJobName(String jobName) {
        this.jobName = jobName;
        return this;
    }

    public long getModifiedTime() {
        return modifiedTime;
    }

    public Job setModifiedTime(long modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getProperty() {
        return property;
    }

    public Job setProperty(String property) {
        this.property = property;
        return this;
    }
}
