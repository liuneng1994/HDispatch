package hdispatch.core.dispatch.azkaban.entity.record;

import org.json.JSONObject;

/**
 *
 * 存放从数据库中读取的执行流的日志
 */
public class DBExecutionFlow {
    private JSONObject object;

    public DBExecutionFlow(){
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

    public int getStatus() {
        return object.getInt("status");
    }

    public void setStatus(int status) {
        object.put("status",status);
    }

    public String getSubmitUser(){
        return object.getString("submit_user");
    }

    public void setSubmitUser(String submitUser){
        object.put("submit_user",submitUser);
    }

    public long getSubmitTime() {
        return object.getLong("submit_time");
    }

    public void setSubmitTime(long submitTime) {
        object.put("submit_time",submitTime);
    }

    public long getUpdateTime() {
        return object.getLong("update_time");
    }

    public void setUpdateTime(long updateTime) {
        object.put("update_time",updateTime);
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

    public String getEncType() {
        return object.getString("enc_type");
    }

    public void setEncType(String encType) {
        object.put("enc_type",encType);
    }

    public JSONObject getFlowData(){
        return object.getJSONObject("flow_data");
    }

    public void setFlowData(JSONObject flowData){
        object.put("flow_data",flowData);
    }

    public long getExecutorId(){
        return object.getLong("executor_id");
    }

    public void setExecutorId(long executorId){
        object.put("executor_id",executorId);
    }
}
