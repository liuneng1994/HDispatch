package hdispatch.core.dispatch.dto;

import java.util.Date;

public class DepFlows {
    private String flowId;

    private String depFlowId;

    private Date submitDate;

    private Integer projectId;

    private Integer depProjectId;

    private String mutFlowId;
    
    private Integer mutProjectId;
    
    private String projectName;

    public String getProject_name() {
        return projectName;
    }

    public void setProject_name(String project_name) {
        this.projectName = project_name;
    }

    public String getMut_flow_id() {
		return mutFlowId;
	}

	public void setMut_flow_id(String mut_flow_id) {
		this.mutFlowId = mutFlowId;
	}

	public Integer getMut_project_id() {
		return mutProjectId;
	}

	public void setMut_project_id(Integer mut_project_id) {
		this.mutProjectId = mut_project_id;
	}

	public String getFlow_id() {
        return flowId;
    }

    public void setFlow_id(String flow_id) {
        this.flowId = flow_id == null ? null : flow_id.trim();
    }

    public String getDep_flow_id() {
        return depFlowId;
    }

    public void setDep_flow_id(String dep_flow_id) {
        this.depFlowId = dep_flow_id == null ? null : dep_flow_id.trim();
    }

    public Date getSubmit_date() {
        return submitDate;
    }

    public void setSubmit_date(Date submit_date) {
        this.submitDate = submit_date;
    }

    public Integer getProject_id() {
        return projectId;
    }

    public void setProject_id(Integer project_id) {
        this.projectId = project_id;
    }

    public Integer getDep_project_id() {
        return depProjectId;
    }

    public void setDep_project_id(Integer dep_project_id) {
        this.depProjectId = dep_project_id;
    }
}