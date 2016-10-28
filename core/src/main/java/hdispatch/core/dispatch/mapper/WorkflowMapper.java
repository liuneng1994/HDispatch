package hdispatch.core.dispatch.mapper;

import hdispatch.core.dispatch.dto.workflow.SimpleWorkflow;
import hdispatch.core.dispatch.dto.workflow.Workflow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 刘能 on 2016/9/12.
 */
public interface WorkflowMapper {
    Workflow getByName(String name);

    Long create(Workflow workflow);

    int update(Workflow workflow);

    int deleteByIds(List<Integer> ids);

    Workflow getById(Long workflowId);

    int updateProjectNameAndFlowIdById(@Param("workflowId") long workflowId,@Param("projectName") String projectName,@Param("flowId") String flowId);

    int saveGraph(@Param("workflowId") Long workflowId,@Param("graph") String graph);

    String getGraph(Long workflowId);

    List<SimpleWorkflow> query(@Param("themeId") Long themeId, @Param("layerId") Long layerId, @Param("workflowName") String workflowName, @Param("description") String decription);

    List<SimpleWorkflow> queryOperate(@Param("themeId") Long themeId, @Param("layerId") Long layerId, @Param("workflowName") String workflowName, @Param("description") String decription);

    List<SimpleWorkflow> queryWorkflowUnderLayer(@Param("layerId") Long layerId);
}
