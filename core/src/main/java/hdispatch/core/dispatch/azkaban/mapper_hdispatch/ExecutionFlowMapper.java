package hdispatch.core.dispatch.azkaban.mapper_hdispatch;

import hdispatch.core.dispatch.azkaban.entity.record.DBExecutionFlow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 */
public interface ExecutionFlowMapper {
    DBExecutionFlow get(@Param("execId") String execId);

    List<DBExecutionFlow> getAll();
}
