package hdispatch.core.dispatch.azkaban.service.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import hdispatch.core.dispatch.azkaban.flow.FlowObj;
import hdispatch.core.dispatch.azkaban.service.ScheduleFlowService;
import hdispatch.core.dispatch.azkaban.util.RequestUrl;
import hdispatch.core.dispatch.azkaban.util.RequestUtils;
import hdispatch.core.dispatch.azkaban.util.ResultObj;
import org.apache.log4j.Logger;

/**
 * Created by 邓志龙 on 2016/8/31.
 */
public class ScheduleFlowServiceImpl implements ScheduleFlowService {
    private static Logger logger = Logger.getLogger(ScheduleFlowService.class);
    private HttpResponse<JsonNode> response;

    /**
     * 调度一个流
     *
     * @param flow
     * @return
     */
    @Override
    public ResultObj scheduleFlow(FlowObj flow) {
        ResultObj result = new ResultObj();
        try {
            response = RequestUtils.post(RequestUrl.SCHEDULE).field("ajax", "scheduleFlow")
                    .field("projectName", flow.getProjectName())
                    .field("projectId", flow.getProjectId())
                    .field("flow", flow.getFlowName())
                    .field("scheduleTime", flow.getScheduleTime())
                    .field("scheduleDate", flow.getScheduleDate())
                    .field("is_recurring", flow.getIs_recurring())
                    .field("period", flow.getPeriodVal() + flow.getPeriod())
                    .asJson();
            System.out.println(response.getBody().getObject().toString());
            if (response.getBody().getObject().getString("status").equals("success")) {
               /* if(response.getBody().getObject().toString().contains("\"error\":"))
                {
                        result.setCode(0);
                        result.setMessage("调度错误！");
                }else
                {

                }*/
                result.setCode(1);
                result.setMessage("执行成功");

            } else {
                result.setCode(0);
                result.setMessage("调度错误！");
            }
        } catch (UnirestException e) {
            result.setCode(0);
            result.setMessage("工程错误或调度时间错误");
            logger.error("工程错误或调度时间错误");
            throw new IllegalArgumentException("工程错误或调度时间错误", e);


        }

        return result;
    }

    @Override
    public ResultObj unscheduleFlow(Long id) {
        ResultObj result = new ResultObj();
        try {
            response = RequestUtils.post(RequestUrl.SCHEDULE).field("action", "removeSched")
                    .field("scheduleId", id)
                    .asJson();
            System.out.println(response.getBody().getObject().toString());
            if (response.getBody().getObject().getString("status").equals("success")) {

                result.setCode(1);
                result.setMessage("执行成功");

            } else {
                result.setCode(0);
                result.setMessage("调度错误！");
            }
        } catch (UnirestException e) {
            result.setCode(0);
            result.setMessage("此项目不存在或没有运行，请刷新后重试");
            logger.error("此项目不存在或没有运行，请刷新后重试");
            throw new IllegalArgumentException("此项目不存在或没有运行，请刷新后重试", e);
        }
        return result;
    }
}

