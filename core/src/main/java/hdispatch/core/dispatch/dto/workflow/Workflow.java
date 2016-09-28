package hdispatch.core.dispatch.dto.workflow;

import com.hand.hap.system.dto.BaseDTO;

import java.util.List;

/**
 * Created by 刘能 on 2016/9/12.
 */
public class Workflow extends BaseDTO {
    private Long themeId;
    private Long layerId;
    private Long workflowId;
    private String name;
    private String projectName;
    private String flowId;
    private String description;
    private String graph;
    private List<WorkflowProperty> properties;
    private List<WorkflowJob> jobs;

    public Long getThemeId() {
        return themeId;
    }

    public Workflow setThemeId(Long themeId) {
        this.themeId = themeId;
        return this;
    }

    public Long getLayerId() {
        return layerId;
    }

    public Workflow setLayerId(Long layerId) {
        this.layerId = layerId;
        return this;
    }

    public Long getWorkflowId() {
        return workflowId;
    }

    public Workflow setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Workflow setName(String name) {
        this.name = name;
        return this;
    }

    public String getProjectName() {
        return projectName;
    }

    public Workflow setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public String getFlowId() {
        return flowId;
    }

    public Workflow setFlowId(String flowId) {
        this.flowId = flowId;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Workflow setDescription(String description) {
        this.description = description;
        return this;
    }


    public String getGraph() {
        return graph;
    }

    public Workflow setGraph(String graph) {
        this.graph = graph;
        return this;
    }

    public List<WorkflowProperty> getProperties() {
        return properties;
    }

    public Workflow setProperties(List<WorkflowProperty> properties) {
        this.properties = properties;
        return this;
    }

    public List<WorkflowJob> getJobs() {
        return jobs;
    }

    public Workflow setJobs(List<WorkflowJob> jobs) {
        this.jobs = jobs;
        return this;
    }

    @Override
    public String toString() {
        return "Workflow{" +
                "themeId=" + themeId +
                ", layerId=" + layerId +
                ", workflowId=" + workflowId +
                ", name='" + name + '\'' +
                ", projectName='" + projectName + '\'' +
                ", flowId='" + flowId + '\'' +
                ", description='" + description + '\'' +
                ", properties=" + properties +
                ", jobs=" + jobs +
                '}';
    }
}
