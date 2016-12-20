package hdispatch.core.dispatch.controllers;

import com.github.pagehelper.PageHelper;
import com.hand.hap.system.dto.ResponseData;
import hdispatch.core.dispatch.dto.workflow.AzkabanFlowDependency;
import hdispatch.core.dispatch.dto.workflow.WorkflowDependency;
import hdispatch.core.dispatch.service.WorkflowDependencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by liuneng on 2016/10/26.
 */

/**
 * 任务流依赖控制器
 * @author neng.liu@hand-china.com
 */
@RestController
@RequestMapping("/dispatcher/workflow_dependency")
public class WorkflowDependencyController {
    @Autowired
    private WorkflowDependencyService workflowDependencyService;

    @RequestMapping(path = "/query", method = RequestMethod.GET)
    public ResponseData query(@RequestParam(name = "projectName") String projectName,
                              @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                              @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<WorkflowDependency> list = workflowDependencyService.query(projectName);
        ResponseData responseData = new ResponseData(list);
        return responseData;
    }

    @RequestMapping(path = "/insert", method = RequestMethod.POST)
    public ResponseData insert(@RequestBody List<AzkabanFlowDependency> dependencies) {
        workflowDependencyService.batchInsert(dependencies);
        return new ResponseData(true);
    }

    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    public ResponseData delete(@RequestBody List<AzkabanFlowDependency> dependencies) {
        workflowDependencyService.batchDelete(dependencies);
        return new ResponseData(true);
    }
}
