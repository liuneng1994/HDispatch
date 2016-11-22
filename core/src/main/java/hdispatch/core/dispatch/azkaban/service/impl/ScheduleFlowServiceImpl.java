package hdispatch.core.dispatch.azkaban.service.impl;

import com.hand.hap.system.dto.ResponseData;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import hdispatch.core.dispatch.azkaban.entity.schedule.Schedule;
import hdispatch.core.dispatch.azkaban.flow.FlowObj;
import hdispatch.core.dispatch.azkaban.service.ScheduleFlowService;
import hdispatch.core.dispatch.azkaban.util.RequestUrl;
import hdispatch.core.dispatch.azkaban.util.RequestUtils;
import hdispatch.core.dispatch.azkaban.util.ResultObj;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 邓志龙 on 2016/8/31.
 */
@Service
@Transactional("hdispatchTM")
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
            if (response.getBody().getObject().getString("status").equals("success")) {

                result.setCode(1);
                result.setMessage("删除成功");

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
/**
 * 获取所有schedule
 */
	@Override
	public Schedule fetchschedule(Map<String, Object> map) {
		try {
			response = RequestUtils.get(RequestUrl.SCHEDULE).queryString("ajax", "fetchSchedule")
					.queryString(map).asJson();

		} catch (UnirestException e) {
			logger.error("schedule不存在！");
			throw new IllegalArgumentException("schedule不存在！", e);
		}
        return new Schedule(response.getBody().getObject());
	}
/**
 * 是否设置了sla
 */
@Override
public boolean hasSla(Integer id) {
	try {
		response = RequestUtils.get(RequestUrl.SCHEDULE).queryString("ajax", "slaInfo")
				.queryString("scheduleId",id).asJson();

	} catch (UnirestException e) {
		logger.error("schedule不存在！");
		throw new IllegalArgumentException("schedule不存在！", e);
	}
	if(response.getBody().getObject().has("settings"))
		return true;
	else
		return false;
}
/**
 * 为schedule设置sla
 */
@Override
public ResultObj setsla(Map<String, Object> map) {
	ResultObj obj=new ResultObj();
	
	try {
		
		response = RequestUtils.post(RequestUrl.SCHEDULE).queryString("ajax", "setSla")
				.queryString(map).asJson();

	} catch (UnirestException e) {
		logger.error("schedule不存在！");
		throw new IllegalArgumentException("schedule不存在！", e);
	}
	if(response.getBody().getObject().length()==0)
		obj.setMessage("设置成功!");
	else
		obj.setMessage("设置失败!");
	return obj;
}
/**
 * 获取sla信息
 */
@Override
public ResultObj slaInfo(Map<String, Object> map) {
	ResultObj obj=new ResultObj();
	try {
		response = RequestUtils.get(RequestUrl.SCHEDULE).queryString("ajax", "slaInfo")
				.queryString(map).asJson();

	} catch (UnirestException e) {
		logger.error("schedule不存在！");
		throw new IllegalArgumentException("schedule不存在！", e);
	}
	obj.setMessage(response.getBody().getObject().toString());
	return obj;
}

@Override
public ResponseData scheduleCronFlow(Map<String, Object> map) {
    System.out.println(map);
    ResponseData result = null;
     try {
         response = RequestUtils.get(RequestUrl.SCHEDULE).queryString("ajax", "scheduleCronFlow")
                 .queryString(map)
                 .asJson();
         if(response.getBody().getObject().has("status")) {
             if (response.getBody().getObject().getString("status").equals("success")) {
                 result = new ResponseData(true);
                 result.setMessage("执行成功");
             } else {
                 result = new ResponseData(false);
                 result.setMessage(response.getBody().getObject().getString("message"));
             }
         }else {
             result = new ResponseData(false);
             result.setMessage(response.getBody().getObject().getString("error"));
         }
     } catch (UnirestException e) {
         result=new ResponseData(false);
         result.setMessage("工程错误或表达式错误");
         throw new IllegalArgumentException("工程错误或调度表达式错误", e);
     }
     return result;
}
}

