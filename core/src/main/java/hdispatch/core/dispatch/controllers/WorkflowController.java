package hdispatch.core.dispatch.controllers;

import com.hand.hap.system.dto.ResponseData;
import hdispatch.core.dispatch.dto.workflow.Workflow;
import hdispatch.core.dispatch.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static hdispatch.core.dispatch.utils.Constants.RET_ERROR;
import static hdispatch.core.dispatch.utils.Constants.RET_SUCCESS;

/**
 * Created by 刘能 on 2016/9/12.
 */
@RestController
@RequestMapping("/workflow")
public class WorkflowController {
    @Autowired
    private WorkflowService workflowService;

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public ResponseData createWorkflow(@RequestBody Workflow workflow) {
        ResponseData responseData;
        Map<String, Object> result = workflowService.createWorkflow(workflow);
        if (result.containsKey(RET_ERROR)) {
            responseData = new ResponseData(false);
            responseData.setMessage((String) result.get(RET_ERROR));
        } else {
            responseData = new ResponseData(true);
            responseData.setMessage((String) result.get(RET_SUCCESS));
        }
        return responseData;
    }

    public Map<String, Object> updateWorkflow(@RequestBody Workflow workflow) {
        return workflowService.updateWorkFlow(workflow);
    }

    @RequestMapping(path = "/generateWorkflow", method = RequestMethod.GET)
    public ResponseData generateWorkflow(@RequestParam(name = "workflowId") long workflowId) {
        ResponseData responseData;
        if (workflowService.generateWorkflow(workflowId)) {
            responseData = new ResponseData(true);
            responseData.setMessage("工作流生成成功");
        } else {
            responseData = new ResponseData(false);
            responseData.setMessage("工作流生成失败");
        }
        return responseData;
    }
}
