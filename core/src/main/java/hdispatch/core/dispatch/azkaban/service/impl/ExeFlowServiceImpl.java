package hdispatch.core.dispatch.azkaban.service.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import hdispatch.core.dispatch.azkaban.entity.flow.ExeFlow;
import hdispatch.core.dispatch.azkaban.entity.flow.exejob;
import hdispatch.core.dispatch.azkaban.service.ExeFlowService;
import hdispatch.core.dispatch.azkaban.util.RequestUrl;
import hdispatch.core.dispatch.azkaban.util.RequestUtils;
import hdispatch.core.dispatch.azkaban.util.ResultObj;
import hdispatch.core.dispatch.dto.ExecutionJobs;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * azkaban任务流实现类
 * Created by 邓志龙 on 2016/8/31.
 * zhilong.deng@hand-china.com
 */
@Service
@Transactional("hdispatchTM")
public class ExeFlowServiceImpl implements ExeFlowService {
	private static Logger logger = Logger.getLogger(ExeFlowService.class);
	private HttpResponse<JsonNode> response;
	private static List<ExecutionJobs> list;
	private static Integer parentId;
	private static Integer version;

	/**
	 * 通过projectname获取peojectid
	 * @param projectName
	 * @return
     */
	@Override
	@Transactional
	public Long Fetchflows(String projectName) {
		try {
			response = RequestUtils.get(RequestUrl.MANAGER).queryString("ajax", "fetchprojectflows")
					.queryString("project", projectName).asJson();

		} catch (UnirestException e) {
			logger.error("网络异常！");
			throw new IllegalArgumentException("网络异常！", e);
		}
		return response.getBody().getObject().getLong("projectId");
	}

	/**
	 * 查询任务流
	 * @param projectName
	 * @param flowId
	 * @param start
	 * @param length
     * @return
     */
	@Override
	@Transactional
	public Object FetchExeFlows(String projectName, String flowId, Integer start, Integer length) {
		try {
			response = RequestUtils.get(RequestUrl.MANAGER).queryString("ajax", "fetchFlowExecutions")
					.queryString("project", projectName).queryString("flow", flowId).queryString("start", start)
					.queryString("length", length).asJson();

		} catch (UnirestException e) {
			logger.error("无法查询此项目或流");
			throw new IllegalArgumentException("无法查询此项目或流", e);
		}
		return response.getBody().getObject();
	}

	/**
	 * 执行任务流
	 * @param map
	 * @return
     */
	@Override
	@Transactional
	public ExeFlow ExecuteFlow(Map<String, Object> map) {
		try {
			response = RequestUtils.get(RequestUrl.EXECUTOR).queryString("ajax", "executeFlow").queryString(map)
					.asJson();
		} catch (UnirestException e) {
			logger.error("流已经运行！");
			throw new IllegalArgumentException("流已经运行！", e);
		}
		
		System.out.println(response.getBody().getObject());
		return new ExeFlow(response.getBody().getObject());
	}

	/**
	 * 查询所有job
	 * @param map
     * @return
     */
	@Override
	@Transactional
	public ExeFlow FetchJobs(Map<String, Object> map) {
		try {
			response = RequestUtils.get(RequestUrl.MANAGER).queryString("ajax", "fetchflowgraph").queryString(map)
					.asJson();

		} catch (UnirestException e) {
			logger.error("无法找到流！");
			throw new IllegalArgumentException("无法找到流！", e);
		}
		return new ExeFlow(response.getBody().getObject());
	}

	/**
	 * 查询正在运行的任务流
	 * @param map
     * @return
     */
	@Override
	@Transactional
	public ExeFlow FetchRunningFlow(Map<String, Object> map) {
		try {
			response = RequestUtils.get(RequestUrl.EXECUTOR).queryString("ajax", "getRunning").queryString(map)
					.asJson();

		} catch (UnirestException e) {
			logger.error("流不在运行中！");
			throw new IllegalArgumentException("流不在运行中！", e);
		}
		return new ExeFlow(response.getBody().getObject());
	}

	/**
	 * 取消任务流的运行
	 * @param map
     * @return
     */
	@Override
	@Transactional
	public ExeFlow CancelFlow(Map<String, Object> map) {
		try {
			response = RequestUtils.get(RequestUrl.EXECUTOR).queryString("ajax", "cancelFlow").queryString(map)
					.asJson();

		} catch (UnirestException e) {
			logger.error("流不在运行中！");
			throw new IllegalArgumentException("流不在运行中！", e);
		}
		return new ExeFlow(response.getBody().getObject());
	}

	/**
	 * 暂停任务流的运行
	 * @param map
     * @return
     */
	@Override
	@Transactional
	public ExeFlow PauseFlow(Map<String, Object> map) {
		try {
			response = RequestUtils.get(RequestUrl.EXECUTOR).queryString("ajax", "pauseFlow").queryString(map).asJson();

		} catch (UnirestException e) {
			logger.error("流不在运行中！");
			throw new IllegalArgumentException("流不在运行中！", e);
		}
		return new ExeFlow(response.getBody().getObject());
	}

	/**
	 * 恢复暂停的任务流
	 * @param map
     * @return
     */
	@Override
	@Transactional
	public ExeFlow ResumeFlow(Map<String, Object> map) {
		try {
			response = RequestUtils.get(RequestUrl.EXECUTOR).queryString("ajax", "resumeFlow").queryString(map)
					.asJson();

		} catch (UnirestException e) {
			logger.error("流不在运行中！");
			throw new IllegalArgumentException("流不在运行中！", e);
		}

		return new ExeFlow(response.getBody().getObject());
	}

	/**
	 * 查找job的日志
	 * @param map
     * @return
     */
	@Override
	@Transactional
	public ResultObj fetchJobLogs(Map<String, Object> map) {
		ResultObj obj=new ResultObj();
		try {
			response = RequestUtils.get(RequestUrl.EXECUTOR).queryString("ajax", "fetchExecJobLogs").queryString(map)
					.asJson();
			System.out.println(response.getBody().getObject());
		} catch (UnirestException e) {
			logger.error("无法找到log！");
			throw new IllegalArgumentException("无法找到log！", e);
		}
		obj.setMessage((String)response.getBody().getObject().get("data"));
		return obj;
	}

	/**
	 * 重跑失败流
	 */
	@Override
	@Transactional
	public ResultObj retryFlow(Map<String, Object> map) {
		ResultObj obj=new ResultObj();
		try {
			response = RequestUtils.get(RequestUrl.EXECUTOR).queryString("ajax", "retryFailedJobs").queryString(map)
					.asJson();

		} catch (UnirestException e) {
			logger.error("当前流已经运行完，无法重跑！");
			throw new IllegalArgumentException("当前流已经运行完，无法重跑！", e);
		}
		if(response.getBody().getObject().has("error"))
		obj.setMessage((String)response.getBody().getObject().get("error"));
		else
		{
			obj.setMessage("重跑成功，重跑3次后任务将失败！");
		}
		return obj;
	}

	/**
	 * 获取任务流的job 并解析出来
	 * @param map
     * @return
     */
	@Override
	@Transactional
	public List<ExecutionJobs> getJobsOfFlow(Map<String, Object> map) {
		ResultObj obj=new ResultObj();
		try {
			response = RequestUtils.get(RequestUrl.EXECUTOR).queryString("ajax", "fetchexecflow").queryString(map)
					.asJson();
		} catch (UnirestException e) {
			logger.error("当前execid不存在");
		}
		list=new ArrayList<>();
		parentId=0;
		version=0;
		list=parseJSON(response.getBody().getObject(),parentId);
		return list;
	}

	/**
	 * 递归解析json
	 * @param obj
	 * @param parentId
     * @return
     */
	public static List<ExecutionJobs> parseJSON(JSONObject obj,Integer parentId)
	{
		if(new exejob(obj).hasNodes())
		{
			ExecutionJobs job=new ExecutionJobs();
			job.setVersion(version++);
			job.setParentId(parentId);
			job.setFlow_id(new exejob(obj).getProject());
			job.setNestedId(new exejob(obj).getNestedId());
			job.setJob_id(new exejob(obj).getId());
			job.setEnd_time(new exejob(obj).getEndTime());
			job.setStart_time(new exejob(obj).getStartTime());
			job.setStatus((byte)new exejob(obj).getStatus());
			if(job.getStart_time()!=-1&&!job.getJob_id().startsWith("_"))
				list.add(job);
			JSONArray array=new exejob(obj).getNodes();
			for (int i=0;i<array.length();i++)
			{
				parseJSON((JSONObject)array.get(i),job.getVersion());
			}
		}else
		{
			ExecutionJobs job=new ExecutionJobs();
			job.setFlow_id(new exejob(obj).getProject());
			job.setParentId(parentId);
			job.setVersion(version++);
			job.setNestedId(new exejob(obj).getNestedId());
			job.setJob_id(new exejob(obj).getId());
			job.setEnd_time(new exejob(obj).getEndTime());
			job.setStart_time(new exejob(obj).getStartTime());
			job.setStatus((byte)new exejob(obj).getStatus());
			if(job.getStart_time()!=-1&&!job.getJob_id().startsWith("_"))
				list.add(job);
		}
		return list;
	}
}
