package hdispatch.core.dispatch.service;

import com.hand.hap.core.IRequest;
import hdispatch.core.dispatch.dto.job.Job;

import java.util.List;

/**
 * Created by yyz on 2016/9/11.
 * yazheng.yang@hand-china.com
 */
public interface JobService {
    List<Job> selectByJob(IRequest requestContext, Job job, int page, int pageSize);

    boolean[] checkIsExist(List<Job> jobList);

    List<Job> batchUpdate(IRequest requestContext, List<Job> jobList);
}
