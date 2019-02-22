package hdispatch.core.dispatch.mapper_hdispatch;


import hdispatch.core.dispatch.dto.ExecutionFlows;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ExecutionFlowsMapper{
    int deleteByPrimaryKey(Integer exec_id);

    int insert(ExecutionFlows record);


    ExecutionFlows selectByPrimaryKey(Integer exec_id);


	List<ExecutionFlows> selectAllExes(ExecutionFlows exe);

    /**
     * 清空日志,
     * @param dateBeforeMillisecond
     */
    void cleanLogsBefore(@Param("dateBefore") Long dateBeforeMillisecond);
}