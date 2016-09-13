package hdispatch.core.dispatch.mapper;


import java.util.List;

import hdispatch.core.dispatch.dto.ExecutionFlows;

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