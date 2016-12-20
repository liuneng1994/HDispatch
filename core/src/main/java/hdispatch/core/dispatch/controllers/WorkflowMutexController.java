package hdispatch.core.dispatch.controllers;

import com.github.pagehelper.PageHelper;
import com.hand.hap.system.dto.ResponseData;
import hdispatch.core.dispatch.dto.workflow.AzkabanFlowMutex;
import hdispatch.core.dispatch.dto.workflow.WorkflowMutex;
import hdispatch.core.dispatch.service.WorkflowMutexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by liuneng on 2016/10/26.
 */

/**
 * 任务流互斥控制器
 * @author neng.liu@hand-china.com
 */
@RestController
@RequestMapping("/dispatcher/workflow_mutex")
public class WorkflowMutexController {
    @Autowired
    private WorkflowMutexService workflowMutexService;

    @RequestMapping(path = "/query", method = RequestMethod.GET)
    public ResponseData query(@RequestParam(name = "projectName") String projectName,
                              @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                              @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<WorkflowMutex> list = workflowMutexService.query(projectName);
        ResponseData responseData = new ResponseData(list);
        return responseData;
    }

    @RequestMapping(path = "/insert", method = RequestMethod.POST)
    public ResponseData insert(@RequestBody List<AzkabanFlowMutex> mutexList) {
        workflowMutexService.batchInsert(mutexList);
        return new ResponseData(true);
    }

    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    public ResponseData delete(@RequestBody List<AzkabanFlowMutex> mutexList) {
        workflowMutexService.batchDelete(mutexList);
        return new ResponseData(true);
    }
}
