package hdispatch.core.dispatch.mappers;

import hdispatch.core.dispatch.dto.workflow.WorkflowProperty;

import java.util.List;

/**
 * Created by 刘能 on 2016/9/12.
 */
public interface WorkflowPropertyMapper {
    int batchInsert(List<WorkflowProperty> properties);

    int deleteByWorkflowId(Long workflowId);
}
