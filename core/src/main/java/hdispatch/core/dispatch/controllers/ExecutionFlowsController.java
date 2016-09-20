package hdispatch.core.dispatch.controllers;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

import hdispatch.core.dispatch.azkaban.entity.flow.ExeFlow;
import hdispatch.core.dispatch.azkaban.service.ExeFlowService;
import hdispatch.core.dispatch.azkaban.util.ResultObj;
import hdispatch.core.dispatch.dto.ExecutionFlows;
import hdispatch.core.dispatch.dto.ExecutionJobs;
import hdispatch.core.dispatch.dto.HdispatchGroup;
import hdispatch.core.dispatch.service.ExecutionFlowsService;
import hdispatch.core.dispatch.service.ExecutionJobsService;
import hdispatch.core.dispatch.service.HdispatchGroupService;
import hdispatch.core.dispatch.service.ThemeService;
import hdispatch.core.dispatch.service.impl.ThemeServiceImpl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/flows")
public class ExecutionFlowsController extends BaseController {
	@Autowired
	private ExecutionFlowsService service;
	@Autowired
	ExeFlowService exeFlowservice;
	@Autowired
	ExecutionJobsService exeJobsService;
	/**
	 * 获取全部flows及相关信息
	 * 
	 * @param request
	 * @param page
	 * @param pagesize
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public ResponseData query(HttpServletRequest request, @RequestParam int page, @RequestParam int pagesize) {
        IRequest irequest = createRequestContext(request);
		String flowName = request.getParameter("flowName");
		String groupName = request.getParameter("groupName");
		String projectName = request.getParameter("projectName");
		String date = request.getParameter("date");
		ExecutionFlows exe = new ExecutionFlows();
		exe.setFlow_id(flowName);
		exe.setProject_name(projectName);
		exe.setGroup_name(groupName);
		exe.setLang(irequest.getLocale());
		if (date != null)
			exe.setStart_time(Long.parseLong(date));
		PageHelper.startPage(page, pagesize);
		List<ExecutionFlows> list = service.selectAll(exe);
		/**
		 * 查询flow的进度
		 */
		for (ExecutionFlows e : list) {
			int count=0;
			double d;
			ExecutionJobs ex=new ExecutionJobs();
			ex.setExec_id(e.getExec_id());
			ex.setFlow_id(e.getFlow_id());
			PageHelper.startPage(page, pagesize);
			List<ExecutionJobs> list2 = exeJobsService.selectJobsByFlow(ex);
			for (ExecutionJobs job : list2) {
				if(job.getStatus()==50)
					count++;
			}
			if(list2.size()!=0)
			d=Math.ceil(count/list2.size());
			else
			d=0;
			e.setRunning((new Double(d)).intValue()*100);
		}
		return new ResponseData(list);
	}

	/**
	 * 获取flow的job列表
	 * @param request
	 * @param page
	 * @param pagesize
	 * @return
	 */
	@RequestMapping("/queryjobs")
	@ResponseBody
	public ResponseData queryjobs(HttpServletRequest request,@RequestParam int page, @RequestParam int pagesize)
	{
		 IRequest irequest = createRequestContext(request);
		String flow_id = request.getParameter("flow_id");
		String exec_id = request.getParameter("exec_id");
		ExecutionJobs exe=new ExecutionJobs();
		exe.setExec_id(Integer.valueOf(exec_id));
		exe.setFlow_id(flow_id);
		exe.setLang(irequest.getLocale());
		PageHelper.startPage(page, pagesize);
		List<ExecutionJobs> list = exeJobsService.selectJobsByFlow(exe);
		/**
		 * 计算每个job运行占得时间比
		 */
		Long endtime=1L;
		Long starttime=0L;
		if(list.size()>0)
		{
		for (int i = list.size()-1; i >=0; i--) {
			if(list.get(i).getEnd_time()!=-1)
			endtime=list.get(i).getEnd_time();
			break;
		}
		
		if(list.get(0).getStart_time()!=-1)
			starttime=list.get(0).getStart_time();
		}
		
		for (ExecutionJobs job : list) {
			if(job.getEnd_time()<0)
				job.setRunning((job.getStart_time()-starttime)+",0,"+(endtime-starttime));
			else
				job.setRunning((job.getStart_time()-starttime)+","+(job.getEnd_time()-starttime)+","+(endtime-starttime));

		}
		return new ResponseData(list);
	}
	
	/**
	 * 获取flow的job日志
	 * @param request
	 * @param page
	 * @param pagesize
	 * @return
	 */
	@RequestMapping("/getjoblog")
	@ResponseBody
	public ResultObj getjoblog(HttpServletRequest request,@RequestParam int exec_id, @RequestParam String job_id)
	{
		Map<String,Object>map=new HashMap<>();
		map.put("execid", exec_id);
		map.put("jobId", job_id);
		map.put("offset", 0);
		map.put("length", 10000000);
		return exeFlowservice.fetchJobLogs(map);
	}
	/**
	 * 重跑失败流
	 * @param request
	 * @param exec_id
	 * @return
	 */
	@RequestMapping("/retryfailflow")
	@ResponseBody
	public ResultObj retryfailflow(HttpServletRequest request,@RequestParam int exec_id)
	{
		Map<String,Object>map=new HashMap<>();
		System.out.println(exec_id);
		map.put("execid", exec_id);
		return exeFlowservice.retryFlow(map);
	}
	/**
	 * 执行
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/startflow")
	@ResponseBody
	public ResultObj start(HttpServletRequest request, @RequestBody List<ExecutionFlows> list) {
		ResultObj obj = new ResultObj();
		for (ExecutionFlows e : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("project", e.getProject_name());
			map.put("flow", e.getFlow_id());
			ExeFlow f = exeFlowservice.ExecuteFlow(map);
			if (f.isError()) {
				obj.setMessage(f.getError());
				obj.setCode(0);
			} else {
				obj.setMessage(f.getMessage());
				obj.setCode(1);
			}
		}
		return obj;
	}

	/**
	 * 恢复执行group
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/resumeflow")
	@ResponseBody
	public ResultObj resume(HttpServletRequest request, @RequestBody List<ExecutionFlows> list) {
		ResultObj obj = new ResultObj();
		for (ExecutionFlows e : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("execid", e.getExec_id());
			ExeFlow f = exeFlowservice.ResumeFlow(map);
			if (f.isError()) {
				obj.setMessage(f.getError());
				obj.setCode(0);
			} else {
				obj.setMessage("success");
				obj.setCode(1);
			}
		}
		return obj;
	}

	/**
	 * 暂停group
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/pauseflow")
	@ResponseBody
	public ResultObj pause(HttpServletRequest request, @RequestBody List<ExecutionFlows> list) {
		ResultObj obj = new ResultObj();

		for (ExecutionFlows e : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("execid", e.getExec_id());
			ExeFlow f = exeFlowservice.PauseFlow(map);
			if (f.isError()) {
				obj.setMessage(f.getError());
				obj.setCode(0);
			} else {
				obj.setMessage("success");
				obj.setCode(1);
			}
		}
		return obj;
	}

	/**
	 * 停止group
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/stopflow")
	@ResponseBody
	public ResultObj stop(HttpServletRequest request, @RequestBody List<ExecutionFlows> list) {
		ResultObj obj = new ResultObj();
		for (ExecutionFlows e : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("execid", e.getExec_id());
			ExeFlow f = exeFlowservice.CancelFlow(map);
			if (f.isError()) {
				obj.setMessage(f.getError());
				obj.setCode(0);
			} else {
				obj.setMessage("success");
				obj.setCode(1);
			}
		}
		return obj;
	}

	/**
	 * 重跑group
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/freshflow")
	@ResponseBody
	public ResultObj fresh(HttpServletRequest request, @RequestBody List<ExecutionFlows> list) {
		ResultObj obj = new ResultObj();
		for (ExecutionFlows e : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("execid", e.getExec_id());
			exeFlowservice.CancelFlow(map);
			map.clear();
			map.put("project", e.getProject_name());
			map.put("flow", e.getFlow_id());
			ExeFlow f = exeFlowservice.ExecuteFlow(map);
			if (f.isError()) {
				obj.setMessage(f.getError());
				obj.setCode(0);
			} else {
				obj.setMessage(f.getMessage());
				obj.setCode(1);
			}
		}
		return obj;
	}
}
