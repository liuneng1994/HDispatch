package hdispatch.core.dispatch.dto.workflow;

/**
 * Created by liuneng on 2016/10/26.
 */
public class AzkabanFlowMutex {
    private String projectName;
    private Integer projectId;
    private String flowId;
    private String mutexProjectName;
    private Integer mutexProjectId;
    private String mutexFlowId;

    public String getProjectName() {
        return projectName;
    }

    public AzkabanFlowMutex setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public AzkabanFlowMutex setProjectId(Integer projectId) {
        this.projectId = projectId;
        return this;
    }

    public String getFlowId() {
        return flowId;
    }

    public AzkabanFlowMutex setFlowId(String flowId) {
        this.flowId = flowId;
        return this;
    }

    public String getMutexProjectName() {
        return mutexProjectName;
    }

    public AzkabanFlowMutex setMutexProjectName(String mutexProjectName) {
        this.mutexProjectName = mutexProjectName;
        return this;
    }

    public Integer getMutexProjectId() {
        return mutexProjectId;
    }

    public AzkabanFlowMutex setMutexProjectId(Integer mutexProjectId) {
        this.mutexProjectId = mutexProjectId;
        return this;
    }

    public String getMutexFlowId() {
        return mutexFlowId;
    }

    public AzkabanFlowMutex setMutexFlowId(String mutexFlowId) {
        this.mutexFlowId = mutexFlowId;
        return this;
    }
}
