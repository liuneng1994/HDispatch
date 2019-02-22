package hdispatch.core.dispatch.azkaban.mapper_hdispatch;

import hdispatch.core.dispatch.azkaban.entity.record.DBExecutionJob;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 */
public interface ExecutionJobMapper {
    DBExecutionJob get(@Param("execId") String execId, @Param("jobId") String jobId, @Param("attempt") long attempt);

    List<DBExecutionJob> getAll();

    List<DBExecutionJob> getAllInExec(@Param("execId") String execId);
}
