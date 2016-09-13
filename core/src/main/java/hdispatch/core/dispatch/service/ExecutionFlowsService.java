package hdispatch.core.dispatch.service;

import java.util.List;

import com.hand.hap.system.service.IBaseService;

import hdispatch.core.dispatch.dto.ExecutionFlows;

public interface ExecutionFlowsService{

	List<ExecutionFlows> selectAll(ExecutionFlows exe);

}
