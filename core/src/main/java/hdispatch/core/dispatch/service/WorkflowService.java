package hdispatch.core.dispatch.service;

import com.hand.hap.core.IRequest;
import hdispatch.core.dispatch.dto.workflow.SimpleWorkflow;
import hdispatch.core.dispatch.dto.workflow.Workflow;

import java.util.List;
import java.util.Map;

/**
 * Created by 刘能 on 2016/9/12.
 */

/**
 * 任务流服务
 * @author neng.liu@hand-china.com
 */
public interface WorkflowService {
    Map<String, Object> createWorkflow(Workflow workflow);

    Map<String, Object> updateWorkFlow(Workflow workflow);

    String generateWorkflow(long workflowId);

    Workflow getWorkflowById(long workflowId);

    Workflow getWorkflowByName(String name);

    boolean saveGraph(long workflowId, String graph);

    String getGraph(long workflowId);

    List<SimpleWorkflow> queryWorkflow(IRequest request,Long themeId, Long layerId, String workflowName, String decription, int page, int pageSize);

    List<SimpleWorkflow> queryOperateWorkflow(IRequest request,Long themeId, Long layerId, String workflowName, String decription, int page, int pageSize);

    String deleteWorkflow(List<Long> ids);

    Map<String, List<String>> getDeptGraph(String flowName);
}
