package hdispatch.core.dispatch.azkaban.service;


import hdispatch.core.dispatch.azkaban.entity.job.JobDetail;

/**
 * Created by 刘能 on 2016/9/2.
 */
public interface JobService {
    int createJob(JobDetail jobDetail);

    JobDetail getJob(int projectId, int version, String JobName);

    int updateJob(JobDetail jobDetail);
}
