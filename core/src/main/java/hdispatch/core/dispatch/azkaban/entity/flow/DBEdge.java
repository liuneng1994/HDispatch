package hdispatch.core.dispatch.azkaban.entity.flow;

import org.json.JSONObject;

/**
 * Created by 刘能 on 2016/9/2.
 */
public class DBEdge {
    private JSONObject object;

    public DBEdge(JSONObject object) {
        this.object = object;
    }

    public String getSource() {
        return object.getString("source");
    }

    public DBEdge setSource(String source) {
        object.put("source", source);
        return this;
    }

    public String getTarget() {
        return object.getString("target");
    }

    public DBEdge setTarget(String target) {
        object.put("target", target);
        return this;
    }

    public String toJSON() {
        return object.toString();
    }
}
