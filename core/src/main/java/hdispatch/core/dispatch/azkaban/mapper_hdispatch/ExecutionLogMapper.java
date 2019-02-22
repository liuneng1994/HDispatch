package hdispatch.core.dispatch.azkaban.mapper_hdispatch;

import hdispatch.core.dispatch.azkaban.entity.record.DBExecutionLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 */
public interface ExecutionLogMapper {
    DBExecutionLog get(@Param("execId") String execId);

    List<DBExecutionLog> getAll();
}
