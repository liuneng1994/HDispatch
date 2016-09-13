package hdispatch.core.dispatch.mapper;

import java.util.List;

import hdispatch.core.dispatch.dto.ExecutionFlows;
import hdispatch.core.dispatch.dto.ExecutionJobs;
import hdispatch.core.dispatch.dto.ExecutionJobsKey;
import hdispatch.core.dispatch.dto.ExecutionJobsWithBLOBs;

public interface ExecutionJobsMapper {
    int deleteByPrimaryKey(ExecutionJobsKey key);

    int insert(ExecutionJobsWithBLOBs record);

    int insertSelective(ExecutionJobsWithBLOBs record);

    ExecutionJobsWithBLOBs selectByPrimaryKey(ExecutionJobsKey key);

    int updateByPrimaryKeySelective(ExecutionJobsWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ExecutionJobsWithBLOBs record);

    int updateByPrimaryKey(ExecutionJobs record);

	List<ExecutionJobs> selectJobsByFlow(ExecutionJobs exe);
}