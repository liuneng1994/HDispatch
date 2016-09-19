package hdispatch.core.dispatch.dto;

public class ProjectFlows extends ProjectFlowsKey {
    private Long modified_time;

    private Byte encoding_type;

    private byte[] json;

    private String project_name;
    
    
    public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public Long getModified_time() {
        return modified_time;
    }

    public void setModified_time(Long modified_time) {
        this.modified_time = modified_time;
    }

    public Byte getEncoding_type() {
        return encoding_type;
    }

    public void setEncoding_type(Byte encoding_type) {
        this.encoding_type = encoding_type;
    }

    public byte[] getJson() {
        return json;
    }

    public void setJson(byte[] json) {
        this.json = json;
    }
}