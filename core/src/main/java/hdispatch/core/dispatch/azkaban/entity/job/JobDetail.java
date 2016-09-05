package hdispatch.core.dispatch.azkaban.entity.job;

/**
 * Created by 刘能 on 2016/9/2.
 */
public class JobDetail {
    private int projectId;
    private int version;
    private String jobName;
    private long modifiedTime;
    private DBJob job;

    public int getProjectId() {
        return projectId;
    }

    public JobDetail setProjectId(int projectId) {
        this.projectId = projectId;
        return this;
    }

    public int getVersion() {
        return version;
    }

    public JobDetail setVersion(int version) {
        this.version = version;
        return this;
    }

    public String getJobName() {
        return jobName;
    }

    public JobDetail setJobName(String jobName) {
        this.jobName = jobName;
        return this;
    }

    public long getModifiedTime() {
        return modifiedTime;
    }

    public JobDetail setModifiedTime(long modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public DBJob getJob() {
        return job;
    }

    public JobDetail setJob(DBJob job) {
        this.job = job;
        return this;
    }
}
