package hdispatch.core.dispatch.azkaban.entity.flow;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by 邓志龙 on 2016/10/26.
 */
public class exejob {
    private JSONObject obj;

    public  exejob(JSONObject obj) {
        this.obj = obj;
    }

    public String getProject() {
        if(hasProject())
        return obj.getString("project");
        else
            return null;
    }

    public String getId() {
        return obj.getString("id");
    }

    public Long getStartTime() {
        return obj.getLong("startTime");
    }

    public Long getEndTime() {
        return obj.getLong("endTime");
    }

    public String getNestedId() {
        return obj.getString("nestedId");
    }

    public String getFlowId() {
        return obj.getString("flowId");
    }

    public int getStatus(){

        String s=obj.getString("status");
        if(s.equals("READY"))
         return 10;
        else if(s.equals("PREPARING"))
         return 20;
        else if(s.equals("RUNNING"))
         return 30;
        else if(s.equals("PAUSED"))
         return 40;
        else if(s.equals("SUCCEEDED"))
         return 50;
        else if(s.equals("KILLED"))
         return 60;
         else if(s.equals("FAILED"))
         return 70;
         else if(s.equals("FAILED_FINISHING"))
         return 80;
         else if(s.equals("SKIPPED"))
         return 90;
         else if(s.equals("DISABLED"))
         return 100;
         else if(s.equals("QUEUED"))
         return 110;
         else if(s.equals("FAILED_SUCCEEDED"))
         return 120;
         else
         return 130;
    }
    public JSONArray getNodes() {
        if(obj.has("nodes"))
            return obj.getJSONArray("nodes");
        else
            return new JSONArray();
    }
    public boolean hasNodes()
    {
        return obj.has("nodes");
    }
    public boolean hasProject()
    {
        return obj.has("project");
    }

}
