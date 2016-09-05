package hdispatch.core.dispatch.azkaban.entity.record;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by yyz on 2016/9/3.
 * yazheng.yang@hand-china.
 *
 * 存放从数据库中读取被执行的job的日志
 */
public class DBExecutionJob {
    private JSONObject object;

    public DBExecutionJob(){
        object = new JSONObject();
    }
    public String getExecId() {
        return object.getString("exec_id");
    }

    public void setExecId(String execId) {
        object.put("exec_id",execId);
    }

    public JSONObject getObject() {
        return object;
    }

    public void setObject(JSONObject object) {
        this.object = object;
    }

    public long getProjectId() {
        return object.getLong("project_id");
    }

    public void setProjectId(long projectId) {
        object.put("project_id",projectId);
    }

    public long getVersion() {
        return object.getLong("version");
    }

    public void setVersion(long version) {
        object.put("version",version);
    }

    public String getFlowId() {
        return object.getString("flow_id");
    }

    public void setFlowId(String flowId) {
        object.put("flow_id",flowId);
    }

    public String getJobId() {
        return object.getString("job_id");
    }

    public void setJobId(String jobId) {
        object.put("job_id",jobId);
    }

    public int getStatus() {
        return object.getInt("status");
    }

    public void setStatus(int status) {
        object.put("status",status);
    }

    public JSONArray getInputParams() {
        return object.getJSONArray("input_params");
    }

    public void setInputParams(JSONArray inputParams) {
        object.put("input_params",inputParams);
    }

    public JSONArray getOutputParams() {
        return object.getJSONArray("output_params");
    }

    public void setOutputParams(JSONArray outputParams) {
        object.put("output_params",outputParams);
    }

    public JSONArray getAttachments() {
        return object.getJSONArray("attachments");
    }

    public void setAttachments(JSONArray attachments) {
        object.put("attachments",attachments);
    }

    public long getAttempt() {
        return object.getLong("attempt");
    }

    public void setAttempt(long attempt) {
        object.put("attempt",attempt);
    }

    public long getStartTime() {
        return object.getLong("start_time");
    }

    public void setStartTime(long startTime) {
        object.put("start_time",startTime);
    }

    public long getEndTime() {
        return object.getLong("end_time");
    }

    public void setEndTime(long endTime) {
        object.put("end_time",endTime);
    }


}
