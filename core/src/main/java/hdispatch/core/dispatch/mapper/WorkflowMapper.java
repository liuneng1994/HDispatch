package hdispatch.core.dispatch.mapper;

import hdispatch.core.dispatch.dto.workflow.Workflow;
import org.apache.ibatis.annotations.Param;

/**
 * Created by 刘能 on 2016/9/12.
 */
public interface WorkflowMapper {
    Workflow getByName(String name);

    Long create(Workflow workflow);

    Workflow getById(Long workflowId);

    int updateProjectNameAndFlowIdById(@Param("workflowId") long workflowId,@Param("projectName") String projectName,@Param("flowId") String flowId);
}
