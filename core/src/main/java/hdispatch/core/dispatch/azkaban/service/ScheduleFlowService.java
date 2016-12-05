package hdispatch.core.dispatch.azkaban.service;

import com.hand.hap.system.dto.ResponseData;
import hdispatch.core.dispatch.azkaban.entity.schedule.Schedule;
import hdispatch.core.dispatch.azkaban.flow.FlowObj;
import hdispatch.core.dispatch.azkaban.util.ResultObj;

import java.util.Map;

/**
 * Created by 邓志龙 on 2016/8/31.
 */
public interface ScheduleFlowService {
    /**
     * 调度任务流
     * @param flow
     * @return
     */
    ResultObj scheduleFlow(FlowObj flow);

    /**
     * 取消调度流
     * @param id
     * @return
     */
    ResultObj unscheduleFlow(Long id);

    /**
     * 获取所有调度计划
     * @param map
     * @return
     */
    Schedule fetchschedule(Map<String, Object> map);

    /**
     * 判断是否设置了slq
     * @param id
     * @return
     */
    boolean hasSla(Integer id);

    /**
     * 设置sla
     * @param map
     * @return
     */
    ResultObj setsla(Map<String, Object> map);

    /**
     * 获取sla信息
     * @param map
     * @return
     */
	ResultObj slaInfo(Map<String, Object> map);
    /**
     *调度任务流（调度表达式，新的接口）
     */
	ResponseData scheduleCronFlow(Map<String, Object> map);

}
