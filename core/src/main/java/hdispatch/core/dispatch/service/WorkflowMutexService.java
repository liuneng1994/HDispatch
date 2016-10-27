package hdispatch.core.dispatch.service;

import hdispatch.core.dispatch.dto.workflow.AzkabanFlowMutex;
import hdispatch.core.dispatch.dto.workflow.WorkflowMutex;

import java.util.List;

/**
 * Created by liuneng on 2016/10/26.
 */
public interface WorkflowMutexService {
    List<WorkflowMutex> query(String projectName);

    int batchInsert(List<AzkabanFlowMutex> dependencies);

    int batchDelete(List<AzkabanFlowMutex> dependencies);
}
