package hdispatch.core.dispatch.controllers;

import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hdispatch.core.dispatch.dto.workflow.Workflow;
import hdispatch.core.dispatch.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static hdispatch.core.dispatch.utils.Constants.RET_ERROR;
import static hdispatch.core.dispatch.utils.Constants.RET_SUCCESS;

/**
 * Created by 刘能 on 2016/9/12.
 */

/**
 * 工作流管理控制器
 * @author neng.liu@hand-china.com
 */
@RestController
@RequestMapping("/dispatcher/workflow")
public class WorkflowController extends BaseController{
    @Autowired
    private WorkflowService workflowService;

    /**
     * 创建工作流，工作流的名称要唯一，工作流内的job名称不能重复
     * @param workflow 工作流对象
     * @return 结果信息
     */
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public ResponseData createWorkflow(@RequestBody Workflow workflow) {
        ResponseData responseData;
        if (workflowService.getWorkflowByName(workflow.getName()) !=null) {
            responseData = new ResponseData(false);
            responseData.setMessage("工作流已存在");
            return responseData;
        }
        Set<String> jobs = new HashSet<>();
        workflow.getJobs().forEach(job-> {
            jobs.add(job.getWorkflowJobId());
        });
        if (jobs.size() < workflow.getJobs().size()) {
            responseData = new ResponseData(false);
            responseData.setMessage("工作流中存在重复的job");
            return responseData;
        }
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

    /**
     * 保存工作流图
     * @param workflowId 工作流id
     * @param graph 图形数据
     * @return 保存结果
     */
    @RequestMapping(path = "/saveGraph" ,method = RequestMethod.POST)
    public ResponseData saveWorkflowGraph(@RequestParam("workflowId") long workflowId,
                                          @RequestParam("graph") String graph) {
        ResponseData responseData;
        if (workflowService.saveGraph(workflowId, graph)) {
            responseData = new ResponseData(true);
        } else {
            responseData = new ResponseData(false);
        }
        return responseData;
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

    @RequestMapping(path = "/hasWorkflow", method = RequestMethod.GET)
    public ResponseData existWorflow(@RequestParam(name="name") String name) {
        ResponseData responseData;
        Workflow workflow = workflowService.getWorkflowByName(name);
        if (workflow == null) {
            responseData = new ResponseData(false);
        } else {
            responseData = new ResponseData(true);
        }
        return responseData;
    }
}
