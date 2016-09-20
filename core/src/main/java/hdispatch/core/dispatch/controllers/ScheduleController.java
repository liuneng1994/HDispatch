package hdispatch.core.dispatch.controllers;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

import hdispatch.core.dispatch.azkaban.entity.flow.ExeFlow;
import hdispatch.core.dispatch.azkaban.entity.schedule.JobsOfFlow;
import hdispatch.core.dispatch.azkaban.entity.schedule.Schedule;
import hdispatch.core.dispatch.azkaban.entity.schedule.ScheduleFlow;
import hdispatch.core.dispatch.azkaban.service.ExeFlowService;
import hdispatch.core.dispatch.azkaban.service.ScheduleFlowService;
import hdispatch.core.dispatch.azkaban.util.ResultObj;
import hdispatch.core.dispatch.dto.ExecutionFlows;
import hdispatch.core.dispatch.dto.ExecutionJobs;
import hdispatch.core.dispatch.dto.HdispatchGroup;
import hdispatch.core.dispatch.dto.HdispatchSchedule;
import hdispatch.core.dispatch.dto.ProjectFlows;
import hdispatch.core.dispatch.service.ExecutionFlowsService;
import hdispatch.core.dispatch.service.ExecutionJobsService;
import hdispatch.core.dispatch.service.HdispatchGroupService;
import hdispatch.core.dispatch.service.HdispatchScheduleService;
import hdispatch.core.dispatch.service.ProjectFlowsService;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/schedule")
public class ScheduleController extends BaseController {
	@Autowired
	private ProjectFlowsService projectFlowsService;
	@Autowired
	private ScheduleFlowService scheduleFlowService;
	@Autowired
	private HdispatchScheduleService hdispatchScheduleService;
	@Autowired
	private ExeFlowService exeFlowService;

	/**
	 * 获取全部flows及相关信息
	 * 
	 * @param request
	 * @param page
	 * @param pagesize
	 * @return
	 */
	@RequestMapping("/queryschedule")
	@ResponseBody
	public ResponseData query(HttpServletRequest request, @RequestParam int page, @RequestParam int pagesize,
			HttpServletResponse response) {
		
        IRequest i = createRequestContext(request);
        System.out.println(i.getLocale()+"---"+i.getCompanyId()+"---"+i.getRoleId()+"---");
        
		String submitdate = request.getParameter("submitdate");
		String project_name = request.getParameter("project_name");
		String flow_id = request.getParameter("flow_id");
		HdispatchSchedule sch = new HdispatchSchedule();
		sch.setFlow_id(flow_id);
		sch.setProject_name(project_name);
		if (submitdate != null) {
			Date d = new Date(Long.valueOf(submitdate));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sch.setSubmit_date(sdf.format(d));
		}
		PageHelper.startPage(page, pagesize);
		List<HdispatchSchedule> list = hdispatchScheduleService.selectAll(sch);
		List<ScheduleFlow> list2 = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		for (HdispatchSchedule p : list) {
			map.put("projectId", p.getProject_id());
			map.put("flowId", p.getFlow_id());
			Schedule s = scheduleFlowService.fetchschedule(map);
			ScheduleFlow ss = new ScheduleFlow();
			if (s.IsNotNull()) {
				ss.setScheduleId(s.getSchdule().getString("scheduleId"));
				ss.setFirstSchedTime(s.getSchdule().getString("firstSchedTime"));
				ss.setNextExecTime(s.getSchdule().getString("nextExecTime"));
				ss.setPeriod(s.getSchdule().getString("period"));
				ss.setSubmitUser(s.getSchdule().getString("submitUser"));
				ss.setFlowId(p.getFlow_id());
				ss.setProjectName(p.getProject_name());
				ss.setProjectId(p.getProject_id());
				ss.setHasSla(scheduleFlowService.hasSla(Integer.parseInt((s.getSchdule().getString("scheduleId")))));
				list2.add(ss);
			}
		}
		return new ResponseData(list2);
	}

	/**
	 * 取消调度流
	 * 
	 * @param request
	 * @param schId
	 * @return
	 */
	@RequestMapping("/unschedule")
	@ResponseBody
	public ResultObj unschedule(HttpServletRequest request, @RequestParam Long schId,@RequestParam String fid,@RequestParam Integer pid) {
		HdispatchSchedule s=new HdispatchSchedule();
		s.setFlow_id(fid);
		s.setProject_id(pid);
		hdispatchScheduleService.delete(s);
		return scheduleFlowService.unscheduleFlow(schId);
	}

	/**
	 * 获取flow的joblist
	 * 
	 * @param request
	 * @param flow_id
	 * @param project
	 * @return
	 */
	@RequestMapping("/getjoblist")
	@ResponseBody
	public List<JobsOfFlow> getjoblist(HttpServletRequest request, @RequestParam String flow_id,
			@RequestParam String project) {
		List<JobsOfFlow> list = new ArrayList<>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("project", project);
		map.put("flow", flow_id);
		ExeFlow f = exeFlowService.FetchJobs(map);
		JSONArray array = f.getNodes();
		Iterator<Object> it = array.iterator();

		while (it.hasNext()) {
			JobsOfFlow jobs = new JobsOfFlow();
			JSONObject obj = (JSONObject) it.next();
			jobs.setJob_id(obj.getString("id"));
			list.add(jobs);
		}
		return list;
	}

	@RequestMapping("/setsla")
	@ResponseBody
	public ResultObj setSla(HttpServletRequest request, @RequestParam Long scheduleId, @RequestParam String slaEmails,
			@RequestParam Object settings) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("scheduleId", scheduleId);
		map.put("slaEmails", slaEmails);
		map.put("settings", settings);
		return scheduleFlowService.setsla(map);
	}
}
