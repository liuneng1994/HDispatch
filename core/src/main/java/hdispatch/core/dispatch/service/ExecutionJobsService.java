package hdispatch.core.dispatch.service;

import hdispatch.core.dispatch.dto.ExecutionJobs;

import java.util.List;

/**
 * 作业service接口类
 */
public interface ExecutionJobsService{
	/**
	 * 通过流查询所有作业
	 * @param exe
	 * @return
     */
	List<ExecutionJobs> selectJobsByFlow(ExecutionJobs exe);

}
