package hdispatch.core.dispatch.dto;

import com.hand.hap.system.dto.BaseDTO;

public class ExecutionFlows extends BaseDTO{
    private Integer exec_id;

    private Integer project_id;

    private Integer version;

    private String flow_id;

    private Byte status;

    private String submit_user;

    private Long submit_time;

    private Long update_time;

    private Long start_time;

    private Long end_time;

    private Byte enc_type;

    private Integer executor_id;

    private byte[] flow_data;

    
    private String project_name;
    
    private String group_name;

    private Integer running;
    
    private String meaning;
    
    private String lang;
    
    
    
	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public Integer getRunning() {
		return running;
	}

	public void setRunning(Integer running) {
		this.running = running;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public Integer getExec_id() {
        return exec_id;
    }

    public void setExec_id(Integer exec_id) {
        this.exec_id = exec_id;
    }

    public Integer getProject_id() {
        return project_id;
    }

    public void setProject_id(Integer project_id) {
        this.project_id = project_id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getFlow_id() {
        return flow_id;
    }

    public void setFlow_id(String flow_id) {
        this.flow_id = flow_id == null ? null : flow_id.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getSubmit_user() {
        return submit_user;
    }

    public void setSubmit_user(String submit_user) {
        this.submit_user = submit_user == null ? null : submit_user.trim();
    }

    public Long getSubmit_time() {
        return submit_time;
    }

    public void setSubmit_time(Long submit_time) {
        this.submit_time = submit_time;
    }

    public Long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Long update_time) {
        this.update_time = update_time;
    }

    public Long getStart_time() {
        return start_time;
    }

    public void setStart_time(Long start_time) {
        this.start_time = start_time;
    }

    public Long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Long end_time) {
        this.end_time = end_time;
    }

    public Byte getEnc_type() {
        return enc_type;
    }

    public void setEnc_type(Byte enc_type) {
        this.enc_type = enc_type;
    }

    public Integer getExecutor_id() {
        return executor_id;
    }

    public void setExecutor_id(Integer executor_id) {
        this.executor_id = executor_id;
    }

    public byte[] getFlow_data() {
        return flow_data;
    }

    public void setFlow_data(byte[] flow_data) {
        this.flow_data = flow_data;
    }
}