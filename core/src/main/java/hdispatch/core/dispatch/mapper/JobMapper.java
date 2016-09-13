package hdispatch.core.dispatch.mapper;

import hdispatch.core.dispatch.dto.job.Job;

import java.util.Collection;
import java.util.List;

/**
 * Created by yyz on 2016/9/11.
 * yazheng.yang@hand-china.com
 */
public interface JobMapper {
    List<Job> selectByJob(Job job);

    List<Job> getByIds(Collection<Long> ids);
}
