package hdispatch.core.dispatch.azkaban.entity.job;

import org.json.JSONObject;

/**
 * Created by 刘能 on 2016/9/2.
 */
public class DBJob {
    private JSONObject object;

    public DBJob(JSONObject object) {
        this.object = object;
    }

    public String getType() {
        return object.getString("type");
    }

    public DBJob setType(String type) {
        object.put("type", type);
        return this;
    }

    public String getCommand() {
        return object.getString("command");
    }

    public DBJob setCommand(String command) {
        object.put("command", command);
        return this;
    }

    public String getDependencies() {
        return object.getString("dependencies");
    }

    public DBJob setDependencies(String dependencies) {
        object.put("dependencies", dependencies);
        return this;
    }

    public String toJSON() {
        return object.toString();
    }
}
