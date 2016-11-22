package hdispatch.core.dispatch.mapper_hdispatch;

import hdispatch.core.dispatch.dto.workflow.WorkflowJob;

import java.util.List;

/**
 * Created by 刘能 on 2016/9/12.
 */
public interface WorkflowJobMapper {
    int batchInsert(List<WorkflowJob> jobs);

    int deleteByWorkflowId(Long workflowId);
}
