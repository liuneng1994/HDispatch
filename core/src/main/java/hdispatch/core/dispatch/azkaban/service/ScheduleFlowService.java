package hdispatch.core.dispatch.azkaban.service;

import hdispatch.core.dispatch.azkaban.flow.FlowObj;
import hdispatch.core.dispatch.azkaban.util.ResultObj;

/**
 * Created by 邓志龙 on 2016/8/31.
 */
public interface ScheduleFlowService {
    ResultObj scheduleFlow(FlowObj flow);

    ResultObj unscheduleFlow(Long id);
}
