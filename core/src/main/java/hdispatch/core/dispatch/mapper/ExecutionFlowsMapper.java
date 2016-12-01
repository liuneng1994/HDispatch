package hdispatch.core.dispatch.mapper;


import hdispatch.core.dispatch.dto.ExecutionFlows;

import java.util.List;

public interface ExecutionFlowsMapper{
    int deleteByPrimaryKey(Integer exec_id);

    int insert(ExecutionFlows record);


    ExecutionFlows selectByPrimaryKey(Integer exec_id);


	List<ExecutionFlows> selectAllExes(ExecutionFlows exe);
}