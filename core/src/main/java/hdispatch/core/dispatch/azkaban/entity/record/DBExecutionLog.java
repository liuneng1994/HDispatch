package hdispatch.core.dispatch.azkaban.entity.record;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * 存放从数据库中读取的执行日志
 */
public class DBExecutionLog {

    private JSONObject object;

    public DBExecutionLog(){
        object = new JSONObject();
    }

    public String getExecId() {
        return object.getString("exec_id");
    }

    public void setExecId(String execId) {
        object.put("exec_id",execId);
    }

    public String getName() {
        return object.getString("name");
    }

    public void setName(String name) {
        object.put("name",name);
    }

    public long getAttempt() {
        return object.getLong("attempt");
    }

    public void setAttempt(long attempt) {
        object.put("attempt",attempt);
    }

    public String getEncType() {
        return object.getString("enc_type");
    }

    public void setEncType(String encType) {
        object.put("enc_type",encType);
    }

    public long getStartByte() {
        return object.getLong("start_byte");
    }

    public void setStartByte(long startByte) {
        object.put("start_byte",startByte);
    }

    public long getEndByte() {
        return object.getLong("end_byte");
    }

    public void setEndByte(long endByte) {
        object.put("end_byte",endByte);
    }

    public JSONArray getLog() {
        return object.getJSONArray("log");
    }

    public void setLog(JSONArray log) {
        object.put("log",log);
    }

    public long getUploadTime() {
        return object.getLong("upload_time");
    }

    public void setUploadTime(long uploadTime) {
        object.put("upload_time",uploadTime);
    }

    public JSONObject getObject() {
        return object;
    }

    public void setObject(JSONObject object) {
        this.object = object;
    }
}
