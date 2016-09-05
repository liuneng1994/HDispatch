package hdispatch.core.dispatch.azkaban.mapper;

import hdispatch.core.dispatch.azkaban.dto.Job;
import org.apache.ibatis.annotations.Param;

/**
 * Created by 刘能 on 2016/9/2.
 */
public interface JobMapper {
    int insert(Job job);

    int update(Job job);

    Job get(@Param("projectId") int projectId, @Param("version") int version, @Param("jobName") String jobName);
}
