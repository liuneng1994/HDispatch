package hdispatch.core.dispatch.dto.workflow;

import java.util.List;
import java.util.Map;

/**
 * Created by 刘能 on 2016/9/12.
 */
public class Workflow {
    private Long themeId;
    private Long layerId;
    private Long workflowId;
    private String name;
    private String projectName;
    private String flowId;
    private String description;
    private Map<String, String> properties;
    private List<WorkflowJob> jobs;
}
