package hdispatch.core.dispatch.controllers;

import hdispatch.core.dispatch.dto.workflow.Workflow;
import hdispatch.core.dispatch.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by 刘能 on 2016/9/12.
 */
@RestController
public class WorkflowController {
    @Autowired
    private WorkflowService workflowService;

    public Map<String, Object> createWorkflow(@RequestBody @Validated Workflow workflow) {
        return workflowService.createWorkflow(workflow);
    }

    public Map<String, Object> updateWorkflow(@RequestBody @Validated Workflow workflow) {
        return workflowService.updateWorkFlow(workflow);
    }

    public Map<String, Object> generateWorkflow(@RequestParam(name = "workflowId") long workflowId) {
        workflowService.generateWorkflow(workflowId);
        return null;
    }
}
