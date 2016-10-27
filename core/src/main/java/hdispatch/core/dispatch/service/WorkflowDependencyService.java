package hdispatch.core.dispatch.service;

import hdispatch.core.dispatch.dto.workflow.AzkabanFlowDependency;
import hdispatch.core.dispatch.dto.workflow.WorkflowDependency;

import java.util.List;

/**
 * Created by liuneng on 2016/10/26.
 */
public interface WorkflowDependencyService {
    List<WorkflowDependency> query(String projectName);

    int batchInsert(List<AzkabanFlowDependency> dependencies);

    int batchDelete(List<AzkabanFlowDependency> dependencies);
}
