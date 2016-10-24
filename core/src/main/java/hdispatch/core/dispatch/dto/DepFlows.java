package hdispatch.core.dispatch.dto;

import java.util.Date;

public class DepFlows {
    private String flow_id;

    private String dep_flow_id;

    private Date submit_date;

    private Integer project_id;

    private Integer dep_project_id;

    private String mut_flow_id;
    
    private Integer mut_project_id;
    
    
    public String getMut_flow_id() {
		return mut_flow_id;
	}

	public void setMut_flow_id(String mut_flow_id) {
		this.mut_flow_id = mut_flow_id;
	}

	public Integer getMut_project_id() {
		return mut_project_id;
	}

	public void setMut_project_id(Integer mut_project_id) {
		this.mut_project_id = mut_project_id;
	}

	public String getFlow_id() {
        return flow_id;
    }

    public void setFlow_id(String flow_id) {
        this.flow_id = flow_id == null ? null : flow_id.trim();
    }

    public String getDep_flow_id() {
        return dep_flow_id;
    }

    public void setDep_flow_id(String dep_flow_id) {
        this.dep_flow_id = dep_flow_id == null ? null : dep_flow_id.trim();
    }

    public Date getSubmit_date() {
        return submit_date;
    }

    public void setSubmit_date(Date submit_date) {
        this.submit_date = submit_date;
    }

    public Integer getProject_id() {
        return project_id;
    }

    public void setProject_id(Integer project_id) {
        this.project_id = project_id;
    }

    public Integer getDep_project_id() {
        return dep_project_id;
    }

    public void setDep_project_id(Integer dep_project_id) {
        this.dep_project_id = dep_project_id;
    }
}