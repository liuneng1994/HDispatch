package hdispatch.core.dispatch.azkaban.service;

import hdispatch.core.dispatch.azkaban.entity.schedule.Schedule;
import hdispatch.core.dispatch.azkaban.flow.FlowObj;
import hdispatch.core.dispatch.azkaban.util.ResultObj;

import java.util.Map;

/**
 * Created by 邓志龙 on 2016/8/31.
 */
public interface ScheduleFlowService {
    ResultObj scheduleFlow(FlowObj flow);

    ResultObj unscheduleFlow(Long id);
    
    Schedule fetchschedule(Map<String, Object> map);
    
    boolean hasSla(Integer id);
    
    ResultObj setsla(Map<String, Object> map);

	ResultObj slaInfo(Map<String, Object> map);

}
