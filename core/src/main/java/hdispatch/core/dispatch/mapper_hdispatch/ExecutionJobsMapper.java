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

    ExecutionJobsWithBLOBs selectByPrimaryKey(ExecutionJobsKey key);

	List<ExecutionJobs> selectJobsByFlow(ExecutionJobs exe);

    /**
     * 清空日志,
     * add by yazheng.yang
     * @param dateBefore
     */
    void cleanLogsBefore(@Param("dateBefore") Date dateBefore);
}