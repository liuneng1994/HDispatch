package hdispatch.core.dispatch.service;

import hdispatch.core.dispatch.dto.workflow.SimpleWorkflow;
import hdispatch.core.dispatch.dto.workflow.Workflow;

import java.util.List;
import java.util.Map;

/**
 * Created by 刘能 on 2016/9/12.
 */
public interface WorkflowService {
    Map<String, Object> createWorkflow(Workflow workflow);

    Map<String, Object> updateWorkFlow(Workflow workflow);

    boolean generateWorkflow(long workflowId);

    Workflow getWorkflowById(long workflowId);

    Workflow getWorkflowByName(String name);

    boolean saveGraph(long workflowId, String graph);

    String getGraph(long workflowId);

    List<SimpleWorkflow> queryWorkflow(Long themeId, Long layerId, String workflowName, String decription, int page, int pageSize);
}
