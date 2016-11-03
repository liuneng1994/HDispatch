package hdispatch.core.dispatch.controllers;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hdispatch.core.dispatch.azkaban.entity.flow.ExeFlow;
import hdispatch.core.dispatch.azkaban.entity.schedule.JobsOfFlow;
import hdispatch.core.dispatch.azkaban.entity.schedule.Schedule;
import hdispatch.core.dispatch.azkaban.entity.schedule.ScheduleFlow;
import hdispatch.core.dispatch.azkaban.flow.FlowObj;
import hdispatch.core.dispatch.azkaban.service.ExeFlowService;
import hdispatch.core.dispatch.azkaban.service.ScheduleFlowService;
import hdispatch.core.dispatch.azkaban.util.ResultObj;
import hdispatch.core.dispatch.dto.HdispatchSchedule;
import hdispatch.core.dispatch.service.HdispatchScheduleService;
import hdispatch.core.dispatch.service.ProjectFlowsService;

import org.apache.xerces.util.SynchronizedSymbolTable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public ResponseData query(HttpServletRequest request, @RequestParam("page") int page, @RequestParam("pagesize") int pagesize,
                              HttpServletResponse response) {

        IRequest i = createRequestContext(request);
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
        List<HdispatchSchedule> list = hdispatchScheduleService.selectAll(i,sch);
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
                ss.setCronExpression(s.getSchdule().get("cronExpression").toString());
                ss.setFlowId(p.getFlow_id());
                ss.setThemeId(p.getTheme_id());
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
    public ResultObj unschedule(HttpServletRequest request, @RequestParam("schId") Long schId, @RequestParam("fid") String fid, @RequestParam("pid") Integer pid) {
        HdispatchSchedule s = new HdispatchSchedule();
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
    public List<JobsOfFlow> getjoblist(HttpServletRequest request, @RequestParam("flow_id") String flow_id,
                                       @RequestParam("project") String project) {
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

    /**
     * 为schedule设置sla
     *
     * @param request
     * @param scheduleId
     * @param slaEmails
     * @param settings
     * @return
     */
    @RequestMapping("/setsla")
    @ResponseBody
    public ResultObj setSla(HttpServletRequest request, @RequestParam("scheduleId") Long scheduleId, @RequestParam("slaEmails") String slaEmails,
                            @RequestParam("settings") String settings) {
    	 Map<String, Object> map = new HashMap<String, Object>();
         map.put("scheduleId", scheduleId);
         map.put("slaEmails", slaEmails);
    	JSONObject object=new JSONObject(settings);
    	for (int i = 0; i < object.length(); i++) {
			map.put("settings["+i+"]", object.get(""+i+""));
		}
        return scheduleFlowService.setsla(map);
    }

    /**
     * 获取sch的sla信息
     *
     * @param request
     * @param scheduleId
     * @return
     */
    @RequestMapping("/slaInfo")
    @ResponseBody
    public ResultObj slaInfo(HttpServletRequest request, @RequestParam("scheduleId") Long scheduleId) {
    	 Map<String, Object> map = new HashMap<String, Object>();
         map.put("scheduleId", scheduleId);         
        return scheduleFlowService.slaInfo(map);
    }
    /**
     * 调度
     * @param request
     * @param projectName
     * @param flow
     * @param datetime
     * @param isrecurring
     * @return
     * @throws ParseException 
     */
    @RequestMapping("/schedule")
    @ResponseBody
    public ResultObj schedule(HttpServletRequest request,
                              @RequestParam("projectName") String projectName,
                              @RequestParam("flow") String flow,
                              @RequestParam("datetime") Long datetime,
                              @RequestParam("isrecurring") boolean isrecurring
    ) throws ParseException {
    	Long projectId=exeFlowService.Fetchflows(projectName);
        SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy hh,mm,a,zzz",Locale.US);
        Date d=new Date(datetime);
        FlowObj obj = new FlowObj(projectName, projectId, flow, sdf.format(d).split(" ")[1], sdf.format(d).split(" ")[0]);
        if(isrecurring) {
            obj.setIs_recurring();
            String period=request.getParameter("period");
            String periodVal=request.getParameter("periodVal");
            obj.setPeriod(period);
            obj.setPeriodVal(Integer.parseInt(periodVal));
        }
        HdispatchSchedule hds=new HdispatchSchedule();
        hds.setProject_id(Integer.parseInt(String.valueOf(projectId)));
        hds.setProject_name(projectName);
        hds.setFlow_id(flow);
        hdispatchScheduleService.delete(hds);
        hds=new HdispatchSchedule();
        hds.setProject_id(Integer.parseInt(String.valueOf(projectId)));
        hds.setProject_name(projectName);
        hds.setFlow_id(flow);

		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        hds.setSubmit_date(sdf.format(d));
       
        int i=hdispatchScheduleService.insert(hds);
        System.out.println("-----------------------------"+i);
        return scheduleFlowService.scheduleFlow(obj);

    }
    /**
     * 调度
     * @param request
     * @param projectName
     * @return
     * @throws ParseException 
     */
    @RequestMapping("/schedulecron")
    @ResponseBody
    public ResponseData schedulecron(HttpServletRequest request,
                              @RequestParam("projectName") String projectName,
                              @RequestParam("flowId") String flowId,
                              @RequestParam("cronExpression") String cronExpression
    ) throws ParseException {
        ResponseData obj=scheduleFlowService.scheduleCronFlow(projectName, flowId, cronExpression);
    	Long projectId=exeFlowService.Fetchflows(projectName);
        HdispatchSchedule hds=new HdispatchSchedule();
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("flowId", flowId);
        Schedule s = scheduleFlowService.fetchschedule(map);
        if(obj.isSuccess())
        {
        hds.setSubmit_date(s.getSchdule().getString("firstSchedTime"));
        hds.setProject_id(Integer.parseInt(String.valueOf(projectId)));
        hds.setFlow_id(flowId);
        hds.setProject_name(projectName);
        hdispatchScheduleService.delete(hds);
        hdispatchScheduleService.insert(hds);
        }
        return obj;

    }
    /**
     * 执行流
     * @param request
     * @param project
     * @param flow
     * @return
     */
    @RequestMapping("/exeflow")
	@ResponseBody
	public ResultObj exeFlow(HttpServletRequest request, @RequestParam("project") String project,
                             @RequestParam("flow") String flow) {
    	String disabled=request.getParameter("disabled");
    	String successEmails=request.getParameter("successEmails");
    	String failureEmails=request.getParameter("failureEmails");
    	String successEmailsOverride=request.getParameter("successEmailsOverride");
    	String failureEmailsOverride =request.getParameter("failureEmailsOverride");
    	String notifyFailureFirst =request.getParameter("notifyFailureFirst");
    	String notifyFailureLast =request.getParameter("notifyFailureLast");
    	String failureAction=request.getParameter("failureAction");
    	String concurrentOption =request.getParameter("concurrentOption");
		ResultObj obj = new ResultObj();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("project",project.replaceAll("\"", ""));
			map.put("flow", flow.replaceAll("\"", ""));
			if(successEmails!=null)
			map.put("successEmails", successEmails.replaceAll("\"", ""));
			if(failureEmails!=null)
			map.put("failureEmails",failureEmails.replaceAll("\"", ""));
			if(successEmailsOverride!=null)
			map.put("successEmailsOverride", successEmailsOverride.replaceAll("\"", ""));
			if(failureEmailsOverride!=null)
			map.put("failureEmailsOverride", failureEmailsOverride.replaceAll("\"", ""));
			if(notifyFailureFirst!=null)
			map.put("notifyFailureFirst", notifyFailureFirst.replaceAll("\"", ""));
			if(notifyFailureLast!=null)
			map.put("notifyFailureLast", notifyFailureLast.replaceAll("\"", ""));
			if(failureAction!=null)
			map.put("failureAction", failureAction.replaceAll("\"", ""));
			if(concurrentOption!=null)
			map.put("concurrentOption", concurrentOption.replaceAll("\"", ""));
			if(disabled!=null)
			map.put("disabled", disabled);	
			ExeFlow f = exeFlowService.ExecuteFlow(map);
			if (f.isError()) {
				obj.setMessage(f.getError());
				obj.setCode(0);
			} else {
				obj.setMessage(f.getMessage());
				obj.setCode(1);
			}
		System.out.println(obj.getMessage());
		return obj;
	}

}
