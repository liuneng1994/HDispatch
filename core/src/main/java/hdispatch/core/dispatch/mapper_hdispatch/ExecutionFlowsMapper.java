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
     * add by yazheng.yang
     * @param dateBefore
     */
    void cleanLogsBefore(@Param("dateBefore") Date dateBefore);
}