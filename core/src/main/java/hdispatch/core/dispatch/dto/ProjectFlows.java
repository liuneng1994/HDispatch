package hdispatch.core.dispatch.dto;

public class ProjectFlows extends ProjectFlowsKey {
    private Long modifiedTime;

    private Byte encodingType;

    private byte[] json;

    private String projectName;
    
    
    public String getProject_name() {
		return projectName;
	}

	public void setProject_name(String project_name) {
		this.projectName = project_name;
	}

	public Long getModified_time() {
        return modifiedTime;
    }

    public void setModified_time(Long modified_time) {
        this.modifiedTime = modified_time;
    }

    public Byte getEncoding_type() {
        return encodingType;
    }

    public void setEncoding_type(Byte encoding_type) {
        this.encodingType = encoding_type;
    }

    public byte[] getJson() {
        return json;
    }

    public void setJson(byte[] json) {
        this.json = json;
    }
}