package hdispatch.core.dispatch.azkaban.service;

import hdispatch.core.dispatch.azkaban.entity.flow.ExeFlow;
import hdispatch.core.dispatch.azkaban.flow.FlowObj;

import java.util.Map;

/**
 * Created by 邓志龙 on 2016/8/31.
 */
public interface ExeFlowService {
    Object Fetchflows(String projectName);

    Object FetchExeFlows(String projectName, String flowId, Integer start, Integer length);

    Object ExecuteFlow(FlowObj obj);

    ExeFlow FetchJobs(Map<String, Object> map);

    ExeFlow FetchRunningFlow(Map<String, Object> map);

    ExeFlow CancelFlow(Map<String, Object> map);

    ExeFlow PauseFlow(Map<String, Object> map);

    ExeFlow ResumeFlow(Map<String, Object> map);

}
