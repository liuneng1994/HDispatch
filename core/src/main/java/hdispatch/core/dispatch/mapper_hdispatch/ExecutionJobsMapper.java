package hdispatch.core.dispatch.mapper_hdispatch;

import hdispatch.core.dispatch.dto.ExecutionJobs;
import hdispatch.core.dispatch.dto.ExecutionJobsKey;
import hdispatch.core.dispatch.dto.ExecutionJobsWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
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

    /**
     * 清空日志,
     * add by yazheng.yang
     * @param dateBeforeMillisecond
     */
    void cleanLogsBefore(@Param("dateBefore") Long dateBeforeMillisecond);
}