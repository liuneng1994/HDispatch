package hdispatch.core.dispatch.azkaban.entity.flow;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by 邓志龙 on 2016/9/1.
 */
public class ExeFlow {
    private JSONObject obj;

    public ExeFlow(JSONObject obj) {
        this.obj = obj;
    }
    public String getMessage() {
        return obj.getString("message");
    }
    public String getProject() {
        return obj.getString("project");
    }
    public String getFlow() {
        return obj.getString("flow");
    }
    public Long getExecid() {
        return obj.getLong("execid");
    }
    public Long getProjectId() {
        return obj.getLong("projectId");
    }
    public JSONArray getExecids() {
        if(obj.has("execIds"))
        return obj.getJSONArray("execIds");
        else
            return new JSONArray();
    }
    public JSONArray getNodes() {
        if(obj.has("nodes"))
            return obj.getJSONArray("nodes");
        else
            return new JSONArray();
    }

    /**
     * 错误结果判断
     * @return
     */
    public String getError() {
        return obj.getString("error");
    }

    public String getResume()
    {
    	return obj.getString("resume");
    }
    public boolean isPause(){return obj.has("resume");}
    public boolean isError(){return obj.has("error");}

}
