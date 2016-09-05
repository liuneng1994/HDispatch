package hdispatch.core.dispatch.azkaban.entity.flow;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuneng on 16-8-30.
 */
public class DBFlow {
    private JSONObject object;

    public DBFlow() {
        object = new JSONObject();
        object.put("mailCreator", "default");
        object.put("props", new JSONArray());
        object.put("layedout", false);
        object.put("metadata", new JSONObject());
    }

    public DBFlow(JSONObject object) {
        this.object = object;
    }

    public JSONObject getObject() {
        return object;
    }

    public DBFlow setObject(JSONObject object) {
        this.object = object;
        return this;
    }

    public int getProjectId() {
        return object.getInt("project.id");
    }

    public JSONArray getNodes() {
        return object.getJSONArray("nodes");
    }

    public DBFlow setNodes(JSONArray nodes) {
        object.put("nodes", nodes);
        return this;
    }

    public JSONArray getEdges() {
        return object.getJSONArray("edges");
    }

    public DBFlow setEdges(JSONArray edges) {
        object.put("edges", edges);
        return this;
    }

    public List<String> getFailureEmail() {
        JSONArray emails = object.getJSONArray("failure.email");
        List<String> emailList = new ArrayList<>();
        for (int i = 0; i < emails.length(); i++) {
            emailList.add(emails.getString(i));
        }
        return emailList;
    }

    public DBFlow setFailureEmail(List<String> emails) {
        object.put("failure.email", new JSONArray(emails));
        return this;
    }

    public List<String> getSuccessEmail() {
        JSONArray emails = object.getJSONArray("success.email");
        List<String> emailList = new ArrayList<>();
        for (int i = 0; i < emails.length(); i++) {
            emailList.add(emails.getString(i));
        }
        return emailList;
    }

    public DBFlow setSuccessEmail(List<String> emails) {
        object.put("success.email", new JSONArray(emails));
        return this;
    }

    public String getFlowId() {
        return object.getString("id");
    }

    public DBFlow setFlowId(String flowId) {
        object.put("id", flowId);
        return this;
    }

    public String getType() {
        return object.getString("type");
    }

    public DBFlow setType(String type) {
        object.put("type", type);
        return this;
    }

    public int getVersion() {
        return object.getInt("version");
    }

    public DBFlow setVersion(int version) {
        object.put("version", version);
        return this;
    }

    public String toJSON() {
        return object.toString();
    }
}
