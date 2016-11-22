package hdispatch.core.dispatch.mappers;

import hdispatch.core.dispatch.dto.workflow.AzkabanFlowDependency;
import hdispatch.core.dispatch.dto.workflow.WorkflowDependency;

import java.util.List;

/**
 * Created by liuneng on 2016/10/26.
 */
public interface WorkflowDependencyMapper {
    List<WorkflowDependency> queryDependency(String projectName);
    int batchInsert(List<AzkabanFlowDependency> dependencies);
    int delete(AzkabanFlowDependency dependency);
    List<WorkflowDependency> queryDependencyed(String projectName);
}
