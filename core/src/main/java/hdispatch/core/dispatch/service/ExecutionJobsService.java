package hdispatch.core.dispatch.service;

import java.util.List;

import com.hand.hap.system.service.IBaseService;

import hdispatch.core.dispatch.dto.ExecutionFlows;
import hdispatch.core.dispatch.dto.ExecutionJobs;

public interface ExecutionJobsService{

	List<ExecutionJobs> selectJobsByFlow(ExecutionJobs exe);

}
