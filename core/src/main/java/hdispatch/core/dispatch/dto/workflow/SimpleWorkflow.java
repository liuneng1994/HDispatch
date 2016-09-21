package hdispatch.core.dispatch.dto.workflow;

/**
 * Created by hasee on 2016/9/21.
 */
public class SimpleWorkflow {
    private String theme;
    private String layer;
    private Long workflowId;
    private String name;
    private String description;

    public String getTheme() {
        return theme;
    }

    public SimpleWorkflow setTheme(String theme) {
        this.theme = theme;
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
