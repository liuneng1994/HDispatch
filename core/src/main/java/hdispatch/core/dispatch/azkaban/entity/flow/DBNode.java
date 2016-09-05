package hdispatch.core.dispatch.azkaban.entity.flow;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuneng on 16-8-30.
 */

/**
 * 请先判断是否为EmbeddedNode再使用
 */
public class DBNode {
    private JSONObject object;

    public DBNode(JSONObject object) {
        this.object = object;
    }

    public DBNode() {
        this.object = new JSONObject();
        object.put("propSource", JSONObject.NULL);
    }

    public boolean isEmbeddedNodes() {
        return object.keySet().contains("nodes");
    }

    public JSONArray getNodes() {
        if (isEmbeddedNodes()) {
            return object.getJSONArray("nodes");
        }
        return null;
    }

    public DBNode setNodes(JSONArray nodes) {
        object.put("nodes", nodes);
        return this;
    }

    /**
     * 依赖的job id
     *
     * @return
     */
    public List<String> getIn() {
        List<String> inList = new ArrayList<>();
        if (object.has("in")) {
            JSONArray in = object.getJSONArray("in");
            for (int i = 0; i < in.length(); i++) {
                inList.add(in.getString(i));
            }
        }
        return inList;
    }

    public DBNode setIn(List<String> inList) {
        object.put("in", new JSONArray(inList));
        return this;
    }

    public String getNodeId() {
        return object.getString("id");
    }

    public DBNode setNodeId(String id) {
        object.put("id", id);
        return this;
    }

    public String getType() {
        return object.getString("type");
    }

    public DBNode setType(String type) {
        object.put("type", type);
        return this;
    }

    public String toJSON() {
        return object.toString();
    }
}
