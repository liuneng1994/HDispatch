package hdispatch.core.dispatch.service;

import hdispatch.core.dispatch.dto.workflow.Workflow;

import java.util.Map;

/**
 * Created by 刘能 on 2016/9/12.
 */
public interface WorkflowService {
    Map<String, Object> createWorkflow(Workflow workflow);

    Map<String, Object> updateWorkFlow(Workflow workflow);

    boolean generateWorkflow(long workflowId);
}
