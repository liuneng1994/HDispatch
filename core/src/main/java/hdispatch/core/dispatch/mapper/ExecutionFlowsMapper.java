package hdispatch.core.dispatch.mapper;


import hdispatch.core.dispatch.dto.ExecutionFlows;

import java.util.List;

public interface ExecutionFlowsMapper{
    int deleteByPrimaryKey(Integer exec_id);

    int insert(ExecutionFlows record);

    int insertSelective(ExecutionFlows record);

    ExecutionFlows selectByPrimaryKey(Integer exec_id);

    int updateByPrimaryKeySelective(ExecutionFlows record);

    int updateByPrimaryKeyWithBLOBs(ExecutionFlows record);

    int updateByPrimaryKey(ExecutionFlows record);

	List<ExecutionFlows> selectAllExes(ExecutionFlows exe);
}