package hdispatch.core.dispatch.service;

import hdispatch.core.dispatch.dto.ExecutionJobs;

import java.util.List;

public interface ExecutionJobsService{

	List<ExecutionJobs> selectJobsByFlow(ExecutionJobs exe);

}
