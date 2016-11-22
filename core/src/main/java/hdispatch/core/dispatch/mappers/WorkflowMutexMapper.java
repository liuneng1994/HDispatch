package hdispatch.core.dispatch.mappers;

import hdispatch.core.dispatch.dto.workflow.AzkabanFlowMutex;
import hdispatch.core.dispatch.dto.workflow.WorkflowMutex;

import java.util.List;

/**
 * Created by liuneng on 2016/10/26.
 */
public interface WorkflowMutexMapper {
    List<WorkflowMutex> queryMutex(String projectName);
    int batchInsert(List<AzkabanFlowMutex> dependencies);
    int delete(AzkabanFlowMutex dependency);
    List<WorkflowMutex> queryMutexed(String projectName);
}
