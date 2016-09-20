package hdispatch.core.dispatch.service;

import java.util.List;

import com.hand.hap.system.service.IBaseService;

import hdispatch.core.dispatch.dto.ExecutionFlows;
import hdispatch.core.dispatch.dto.ProjectFlows;

public interface ProjectFlowsService{

	List<ProjectFlows> selectAllWithDistinct();

}
