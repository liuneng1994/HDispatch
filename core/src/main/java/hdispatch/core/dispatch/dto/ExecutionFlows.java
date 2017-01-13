package hdispatch.core.dispatch.dto;

import com.hand.hap.system.dto.BaseDTO;

public class ExecutionFlows extends BaseDTO{
    private Integer execId;

    private Integer projectId;

    private Integer version;

    private String flowId;

    private Byte status;

    private String submitUser;

    private Long submitTime;

    private Long updateTime;

    private Long themeId;


    private Long startTime;

    private Long endTime;

    private Byte encType;

    private Integer executorId;

    private byte[] flowData;

    
    private String projectName;
    
    private String groupName;

    private Integer running;
    
    private String meaning;
    
    private String lang;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;



    public Long getTheme_id() {
        return themeId;
    }

    public void setTheme_id(Long theme_id) {
        this.themeId = theme_id;
    }

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
		return projectName;
	}

	public void setProject_name(String project_name) {
		this.projectName = project_name;
	}

	public String getGroup_name() {
		return groupName;
	}

	public void setGroup_name(String group_name) {
		this.groupName = group_name;
	}

	public Integer getExec_id() {
        return execId;
    }

    public void setExec_id(Integer exec_id) {
        this.execId = exec_id;
    }

    public Integer getProject_id() {
        return projectId;
    }

    public void setProject_id(Integer project_id) {
        this.projectId = project_id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getFlow_id() {
        return flowId;
    }

    public void setFlow_id(String flow_id) {
        this.flowId = flow_id == null ? null : flow_id.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getSubmit_user() {
        return submitUser;
    }

    public void setSubmit_user(String submit_user) {
        this.submitUser = submit_user == null ? null : submit_user.trim();
    }

    public Long getSubmit_time() {
        return submitTime;
    }

    public void setSubmit_time(Long submit_time) {
        this.submitTime = submit_time;
    }

    public Long getUpdate_time() {
        return updateTime;
    }

    public void setUpdate_time(Long update_time) {
        this.updateTime = update_time;
    }

    public Long getStart_time() {
        return startTime;
    }

    public void setStart_time(Long start_time) {
        this.startTime = start_time;
    }

    public Long getEnd_time() {
        return endTime;
    }

    public void setEnd_time(Long end_time) {
        this.endTime = end_time;
    }

    public Byte getEnc_type() {
        return encType;
    }

    public void setEnc_type(Byte enc_type) {
        this.encType = enc_type;
    }

    public Integer getExecutor_id() {
        return executorId;
    }

    public void setExecutor_id(Integer executor_id) {
        this.executorId = executor_id;
    }

    public byte[] getFlow_data() {
        return flowData;
    }

    public void setFlow_data(byte[] flow_data) {
        this.flowData = flow_data;
    }
}