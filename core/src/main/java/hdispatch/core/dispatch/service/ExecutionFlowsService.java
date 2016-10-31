package hdispatch.core.dispatch.service;

import hdispatch.core.dispatch.dto.ExecutionFlows;

import java.util.List;

import com.hand.hap.core.IRequest;

public interface ExecutionFlowsService{

	List<ExecutionFlows> selectAll(IRequest requestContext,ExecutionFlows exe);

}
