package hdispatch.core.dispatch.azkaban.entity.record;


/**
 * Created by yyz on 2016/9/2.
 * yazheng.yang@hand-china.com
 */
public class RECExecutionHistory extends RECExecutionSince {
    /*
    数据：
        执行流的信息
        nodes:对象数组，存放执行流中执行的所有job的执行结果
           {
              "project" : "Job-Test",
              "updateTime" : 1472729400134,
              "type" : null,
              "attempt" : 0,
              "execid" : 27,
              "submitTime" : 1472729907098,
              "nodes" : [ {
                "nestedId" : "bar",
                "in" : [ "foo" ],
                "startTime" : 1472729344346,
                "updateTime" : 1472729400064,
                "id" : "bar",
                "endTime" : 1472729400053,
                "type" : "command",
                "attempt" : 0,
                "status" : "KILLED"
              }, {
                "nestedId" : "foo",
                "startTime" : 1472729324309,
                "updateTime" : 1472729344345,
                "id" : "foo",
                "endTime" : 1472729344339,
                "type" : "command",
                "attempt" : 0,
                "status" : "SUCCEEDED"
              } ],
              "nestedId" : "bar",
              "submitUser" : "azkaban",
              "startTime" : 1472729324295,
              "id" : "bar",
              "endTime" : 1472729400070,
              "projectId" : 1,
              "flowId" : "bar",
              "flow" : "bar",
              "status" : "KILLED"
           }

     */

    public String getProject() {
        if (null == getJsonObject()) {
            return null;
        }
        return getJsonObject().getString("project");
    }

    public String getType() {
        if (null == getJsonObject()) {
            return null;
        }
        return getJsonObject().getString("type");
    }

    public long getSubmitTime() {
        if (null == getJsonObject()) {
            return 0L;
        }
        return getJsonObject().getLong("submitTime");
    }

    public String getExecId() {
        if (null == getJsonObject()) {
            return null;
        }
        return getJsonObject().getString("execid");
    }

    public String getProjectId() {
        if (null == getJsonObject()) {
            return null;
        }
        return getJsonObject().getString("projectId");
    }

    public String getFlowId() {
        if (null == getJsonObject()) {
            return null;
        }
        return getJsonObject().getString("flowId");
    }
}
