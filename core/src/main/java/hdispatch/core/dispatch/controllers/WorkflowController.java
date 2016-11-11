package hdispatch.core.dispatch.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hdispatch.core.dispatch.dto.workflow.Workflow;
import hdispatch.core.dispatch.exception.JobAbsentException;
import hdispatch.core.dispatch.service.WorkflowService;
import hdispatch.core.dispatch.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static hdispatch.core.dispatch.utils.Constants.RET_ERROR;
import static hdispatch.core.dispatch.utils.Constants.RET_SUCCESS;

/**
 * Created by 刘能 on 2016/9/12.
 */

/**
 * 任务流管理控制器
 *
 * @author neng.liu@hand-china.com
 */
@RestController
@RequestMapping("/dispatcher/workflow")
public class WorkflowController extends BaseController {
    @Autowired
    private WorkflowService workflowService;
    private Logger logger = LoggerFactory.getLogger(WorkflowController.class);

    /**
     * 创建任务流，任务流的名称要唯一，任务流内的job名称不能重复
     *
     * @param workflow 任务流对象
     * @return 结果信息
     */
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public ResponseData createWorkflow(@RequestBody Workflow workflow) {
        logger.info("create workflow {}", workflow);
        ResponseData responseData;
        if (workflowService.getWorkflowByName(workflow.getName()) != null) {
            logger.info("workflow {} exits", workflow.getName());
            responseData = new ResponseData(false);
            responseData.setMessage("任务流已存在");
            return responseData;
        }
        Set<String> jobs = new HashSet<>();
        workflow.getJobs().forEach(job -> {
            jobs.add(job.getWorkflowJobId());
        });
        if (jobs.size() < workflow.getJobs().size()) {
            logger.info("workflow {} has duplicated job", workflow);
            responseData = new ResponseData(false);
            responseData.setMessage("任务流中存在重复的job");
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

    /**
     * 保存任务流图
     *
     * @param workflowId 任务流id
     * @param graph      图形数据
     * @return 保存结果
     */
    @RequestMapping(path = "/saveGraph", method = RequestMethod.POST)
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

    /**
     * 在azkaban生成任务流
     *
     * @param workflowId 任务流id
     * @return 创建是否成功
     */
    @RequestMapping(path = "/generateWorkflow", method = RequestMethod.GET)
    public ResponseData generateWorkflow(@RequestParam(name = "workflowId") long workflowId) {
        ResponseData responseData;
        try {
            String result = workflowService.generateWorkflow(workflowId);
            if (StringUtils.isEmpty(result)) {
                responseData = new ResponseData(true);
                responseData.setMessage("任务流生成成功");
            } else {
                responseData = new ResponseData(false);
                responseData.setMessage(result);
            }
        } catch (JobAbsentException e) {
            responseData = new ResponseData(false);
            responseData.setMessage(e.getMessage() + "不存在");
        }
        return responseData;
    }

    @RequestMapping(path = "/hasWorkflow", method = RequestMethod.GET)
    public ResponseData existWorflow(@RequestParam(name = "name") String name) {
        ResponseData responseData;
        Workflow workflow = workflowService.getWorkflowByName(name);
        if (workflow == null) {
            responseData = new ResponseData(false);
        } else {
            responseData = new ResponseData(true);
        }
        return responseData;
    }

    /**
     * 任务流查询
     *
     * @param themeId      主题编号
     * @param layerId      层级编号
     * @param workflowName 任务流名称
     * @param description  描述
     * @param page         页数
     * @param pageSize     每页个数
     * @return
     */
    @RequestMapping(path = "/query", method = RequestMethod.POST)
    public ResponseData queryWorkflow(@RequestParam(name = "themeId", required = false) Long themeId,
                                      @RequestParam(name = "layerId", required = false) Long layerId,
                                      @RequestParam(name = "workflowName", required = false) String workflowName,
                                      @RequestParam(name = "description", required = false) String description,
                                      @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                      @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
                                      HttpServletRequest servletRequest) {
        ResponseData responseData = null;
        IRequest request = createRequestContext(servletRequest);
        responseData = new ResponseData(workflowService.queryWorkflow(request, themeId, layerId, workflowName, description, page, pageSize));
        return responseData;
    }

    @RequestMapping(path = "/query_operate", method = RequestMethod.POST)
    public ResponseData queryOperateWorkflow(@RequestParam(name = "themeId", required = false) Long themeId,
                                             @RequestParam(name = "layerId", required = false) Long layerId,
                                             @RequestParam(name = "workflowName", required = false) String workflowName,
                                             @RequestParam(name = "description", required = false) String description,
                                             @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                             @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
                                             HttpServletRequest servletRequest) {
        ResponseData responseData = null;
        IRequest request = createRequestContext(servletRequest);
        responseData = new ResponseData(workflowService.queryOperateWorkflow(request, themeId, layerId, workflowName, description, page, pageSize));
        return responseData;
    }

    /**
     * 通过ID查找任务流
     *
     * @param workflowId 任务流编号
     * @return
     */
    @RequestMapping(path = "/get", method = RequestMethod.GET)
    public ResponseData getWorkflow(@RequestParam(name = "workflowId") long workflowId) {
        ResponseData responseData = null;
        Workflow workflow = workflowService.getWorkflowById(workflowId);
        if (workflow == null) {
            responseData = new ResponseData(false);
            responseData.setMessage("任务流不存在");
        } else {
            responseData = new ResponseData(true);
            responseData.setRows(Collections.singletonList(workflow));
        }
        return responseData;
    }

    /**
     * 更新任务流
     *
     * @param workflow 任务流
     * @return
     */
    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public ResponseData updateWorkflow(@RequestBody Workflow workflow) {
        ResponseData responseData = null;
        workflowService.updateWorkFlow(workflow);
        responseData = new ResponseData(true);
        responseData.setMessage("更新成功");
        return responseData;
    }

    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    public ResponseData updateWorkflow(@RequestBody List<Integer> ids) {
        workflowService.deleteWorkflow(ids);
        return new ResponseData(true);
    }
}
