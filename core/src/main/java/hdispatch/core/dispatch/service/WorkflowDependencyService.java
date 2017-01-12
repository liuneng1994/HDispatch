package hdispatch.core.dispatch.service;

import hdispatch.core.dispatch.dto.workflow.AzkabanFlowDependency;
import hdispatch.core.dispatch.dto.workflow.WorkflowDependency;

import java.util.List;

/**
 * Created by liuneng on 2016/10/26.
 */
/**
 * 任务流依赖服务
 * @author neng.liu@hand-china.com
 */
public interface WorkflowDependencyService {
    List<WorkflowDependency> query(String projectName);

    int batchInsert(List<AzkabanFlowDependency> dependencies);

    int batchDelete(List<AzkabanFlowDependency> dependencies);
}
