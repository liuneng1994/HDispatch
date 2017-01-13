package hdispatch.core.dispatch.azkaban.service;

import hdispatch.core.dispatch.azkaban.entity.flow.ExeFlow;
import hdispatch.core.dispatch.azkaban.entity.flow.exejob;
import hdispatch.core.dispatch.azkaban.util.ResultObj;
import hdispatch.core.dispatch.dto.ExecutionJobs;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 任务流service接口
 * Created by 邓志龙 on 2016/8/31.
 * zhilong.deng@hand-china.com
 */
public interface ExeFlowService {
	/**
	 * 通过projectname获取peojectid
	 * @param projectName
	 * @return
	 */
	Long Fetchflows(String projectName);
	/**
	 * 查询任务流
	 * @param projectName
	 * @param flowId
	 * @param start
	 * @param length
	 * @return
	 */
	Object FetchExeFlows(String projectName, String flowId, Integer start, Integer length);
	/**
	 * 执行任务流
	 * @param map
	 * @return
	 */
	ExeFlow ExecuteFlow(Map<String, Object> map);
	/**
	 * 查询所有job
	 * @param map
	 * @return
	 */
	ExeFlow FetchJobs(Map<String, Object> map);
	/**
	 * 查询正在运行的任务流
	 * @param map
	 * @return
	 */
	ExeFlow FetchRunningFlow(Map<String, Object> map);
	/**
	 * 取消任务流的运行
	 * @param map
	 * @return
	 */
	ExeFlow CancelFlow(Map<String, Object> map);
	/**
	 * 暂停任务流的运行
	 * @param map
	 * @return
	 */
	ExeFlow PauseFlow(Map<String, Object> map);
	/**
	 * 恢复暂停的任务流
	 * @param map
	 * @return
	 */
	ExeFlow ResumeFlow(Map<String, Object> map);
	/**
	 * 查找job的日志
	 * @param map
	 * @return
	 */
	ResultObj fetchJobLogs(Map<String, Object> map);
	/**
	 * 重跑失败流
	 */
	ResultObj retryFlow(Map<String, Object> map);
	/**
	 * 获取任务流的job 并解析出来
	 * @param map
	 * @return
	 */
	List<ExecutionJobs> getJobsOfFlow(Map<String, Object> map);
}
