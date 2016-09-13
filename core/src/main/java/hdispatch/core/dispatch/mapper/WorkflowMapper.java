package hdispatch.core.dispatch.mapper;

import hdispatch.core.dispatch.dto.workflow.Workflow;

/**
 * Created by 刘能 on 2016/9/12.
 */
public interface WorkflowMapper {
    Workflow getByName(String name);

    Long create(Workflow workflow);

    Workflow getById(Long workflowId);
}
