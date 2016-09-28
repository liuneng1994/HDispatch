package hdispatch.core.dispatch.service;

import hdispatch.core.dispatch.dto.ExecutionFlows;

import java.util.List;

public interface ExecutionFlowsService{

	List<ExecutionFlows> selectAll(ExecutionFlows exe);

}
