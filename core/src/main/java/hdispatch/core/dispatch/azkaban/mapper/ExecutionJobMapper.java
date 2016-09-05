package hdispatch.core.dispatch.azkaban.mapper;

import hdispatch.core.dispatch.azkaban.entity.record.DBExecutionJob;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yyz on 2016/9/3.
 * yazheng.yang@hand-china.com
 */
public interface ExecutionJobMapper {
    DBExecutionJob get(@Param("execId") String execId, @Param("jobId") String jobId, @Param("attempt") long attempt);

    List<DBExecutionJob> getAll();

    List<DBExecutionJob> getAllInExec(@Param("execId") String execId);
}
