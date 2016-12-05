package hdispatch.core.dispatch.dto.workflow;

/**
 * Created by liuneng on 2016/9/21.
 */
public class SimpleWorkflow {
    private String theme;
    private String themeId;
    private String layer;
    private String layerId;
    private Long workflowId;
    private String project;
    private String flowId;
    private String name;
    private String description;

    public String getTheme() {
        return theme;
    }

    public SimpleWorkflow setTheme(String theme) {
        this.theme = theme;
        return this;
    }

    public String getThemeId() {
        return themeId;
    }

    public SimpleWorkflow setThemeId(String themeId) {
        this.themeId = themeId;
        return this;
    }

    public String getLayerId() {
        return layerId;
    }

    public SimpleWorkflow setLayerId(String layerId) {
        this.layerId = layerId;
        return this;
    }

    public String getProject() {
        return project;
    }

    public SimpleWorkflow setProject(String project) {
        this.project = project;
        return this;
    }

    public String getFlowId() {
        return flowId;
    }

    public SimpleWorkflow setFlowId(String flowId) {
        this.flowId = flowId;
        return this;
    }

    public String getLayer() {
        return layer;
    }

    public SimpleWorkflow setLayer(String layer) {
        this.layer = layer;
        return this;
    }

    public Long getWorkflowId() {
        return workflowId;
    }

    public SimpleWorkflow setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
        return this;
    }

    public String getName() {
        return name;
    }

    public SimpleWorkflow setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SimpleWorkflow setDescription(String description) {
        this.description = description;
        return this;
    }
}
