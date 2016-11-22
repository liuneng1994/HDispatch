package hdispatch.core.dispatch.azkaban.mapper_hdispatch;

import hdispatch.core.dispatch.azkaban.entity.record.DBExecutionLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yyz on 2016/9/3.
 * yazheng.yang@hand-china.com
 */
public interface ExecutionLogMapper {
    DBExecutionLog get(@Param("execId") String execId);

    List<DBExecutionLog> getAll();
}
