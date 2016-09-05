package hdispatch.core.dispatch.azkaban.entity.record;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by yyz on 2016/9/2.
 * yazheng.yang@hand-china.com
 * 存放流在指定时刻之后的执行数据
 */
public class RECExecutionSince {
    private JSONObject jsonObject;

    /*
        {
          "nodes" : [ {
            "startTime" : 1472722654417,
            "updateTime" : 1472722764002,
            "id" : "bar",
            "endTime" : 1472722763986,
            "attempt" : 0,
            "status" : "SUCCEEDED"
          }, {
            "startTime" : 1472722634383,
            "updateTime" : 1472722654427,
            "id" : "foo",
            "endTime" : 1472722654410,
            "attempt" : 0,
            "status" : "SUCCEEDED"
          } ],
          "startTime" : 1472722634378,
          "updateTime" : 1472722764007,
          "id" : "bar",
          "endTime" : 1472722764004,
          "attempt" : 0,
          "flow" : "bar",
          "status" : "SUCCEEDED"
        }
         */

    public void setJsonObject(JSONObject jsonObject){
        this.jsonObject = jsonObject;
    }
    public JSONObject getJsonObject(){
        return jsonObject;
    }

    public JSONArray getNodes(){
        if(null == jsonObject){
            return null;
        }
        return jsonObject.getJSONArray("nodes");
    }

    public long getStartTime(){
        if(null == jsonObject){
            return 0L;
        }
        return jsonObject.getLong("startTime");
    }

    public long getEndTime(){
        if(null == jsonObject){
            return 0L;
        }
        return jsonObject.getLong("endTime");
    }

    public long getUpdateTime(){
        if(null == jsonObject){
            return 0L;
        }
        return jsonObject.getLong("updateTime");
    }

    public String getId(){
        if(null == jsonObject){
            return null;
        }
        return jsonObject.getString("id");
    }

    public long getAttempt(){
        if(null == jsonObject){
            return 0L;
        }
        return jsonObject.getLong("attempt");
    }

    /**
     * 返回流的名称
     */
    public String getFlow(){
        if(null == jsonObject){
            return null;
        }

        return jsonObject.getString("flow");
    }

    public String getStatus(){
        if(null == jsonObject){
            return null;
        }
        return jsonObject.getString("status");
    }
}
