package hdispatch.core.dispatch.service;

import com.hand.hap.core.IRequest;
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

    String generateWorkflow(long workflowId);

    Workflow getWorkflowById(long workflowId);

    Workflow getWorkflowByName(String name);

    boolean saveGraph(long workflowId, String graph);

    String getGraph(long workflowId);

    List<SimpleWorkflow> queryWorkflow(IRequest request,Long themeId, Long layerId, String workflowName, String decription, int page, int pageSize);

    void deleteWorkflow(List<Integer> ids);
}
