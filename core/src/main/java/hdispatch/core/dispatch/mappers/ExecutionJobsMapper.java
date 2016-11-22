package hdispatch.core.dispatch.mappers;

import hdispatch.core.dispatch.dto.ExecutionJobs;
import hdispatch.core.dispatch.dto.ExecutionJobsKey;
import hdispatch.core.dispatch.dto.ExecutionJobsWithBLOBs;

import java.util.List;

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