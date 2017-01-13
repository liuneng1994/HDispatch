package hdispatch.core.dispatch.controllers;

import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hdispatch.core.dispatch.dto.DepFlows;
import hdispatch.core.dispatch.dto.workflow.Workflow;
import hdispatch.core.dispatch.dto.workflow.WorkflowDependency;
import hdispatch.core.dispatch.service.DepFlowsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/depflows")
public class DepFlowsController extends BaseController {
	Logger logger = Logger.getLogger(DepFlowsController.class);
	@Autowired
	DepFlowsService service;
	/**
	 * 增加依赖流
	 * @param request
	 * @param flows
	 * @return
	 */
	@RequestMapping("/insertDep")
	@ResponseBody
	public ResponseData insertDep(HttpServletRequest request, @RequestBody List<DepFlows>flows)
	{
		Locale locale = RequestContextUtils.getLocale(request);
		ResponseData result = null;
		result = new ResponseData(true);
		String message="";
		for (DepFlows flow:flows) {
			flow.setProject_id(service.selectIdByName(flow.getProject_name()));
			if(service.isExitDep(flow)>0)
			{
				message+=flow.getFlow_id()+getMessageSource().getMessage("hdispatch.depflow.set_dep.error_duplicate", null, locale);
			}else
			{
			int i=service.insertDep(flow);
			message+=flow.getFlow_id()+getMessageSource().getMessage("hdispatch.create_success", null, locale);
			}
		}
		result.setMessage(message);
		return result;
	}
	/**
	 * 设置互斥流
	 * @param request
	 * @param flows
	 * @return
	 */
	@RequestMapping("/insertMut")
	@ResponseBody
	public ResponseData insertMut(HttpServletRequest request, @RequestBody List<DepFlows>flows)
	{
		Locale locale = RequestContextUtils.getLocale(request);
		ResponseData obj= null;
		obj = new ResponseData(true);
		String message="";
		for (DepFlows flow:flows) {
			flow.setProject_id(service.selectIdByName(flow.getProject_name()));
			if(service.isExitMut(flow)>0)
			{
				message+=flow.getFlow_id()+getMessageSource().getMessage("hdispatch.depflow.set_dep.error_duplicate", null, locale);
			}else
			{
			int i=service.insertMut(flow);
			message+=flow.getFlow_id()+getMessageSource().getMessage("hdispatch.create_success", null, locale);
			}
		}
		obj.setMessage(message);
		return obj;
	}
	/**
	 * 删除依赖关系
	 * @param request
	 * @param flows
	 * @return
	 */
	@RequestMapping("/deleteDep")
	@ResponseBody
	public ResponseData deleteDep(HttpServletRequest request, @RequestBody List<DepFlows>flows)
	{
		Locale locale = RequestContextUtils.getLocale(request);
		ResponseData obj= null;
		String message="";
		for (DepFlows flow:flows) {
			flow.setProject_id(service.selectIdByName(flow.getProject_name()));
			service.deleteDep(flow);
			message+=flow.getFlow_id()+getMessageSource().getMessage("hdispatch.tip.delete_success",null,locale);
			obj=new ResponseData(true);
		}
		obj.setMessage(message);
		return obj;
	}
	/**
	 * 删除互斥关系
	 * @param request
	 * @param flows
	 * @return
	 */
	@RequestMapping("/deleteMut")
	@ResponseBody
	public ResponseData deleteMut(HttpServletRequest request, @RequestBody List<DepFlows>flows)
	{
		Locale locale = RequestContextUtils.getLocale(request);
		ResponseData obj= null;
		String message="";
		for (DepFlows flow:flows) {
			flow.setProject_id(service.selectIdByName(flow.getProject_name()));
			service.deleteMut(flow);
			message+=flow.getFlow_id()+getMessageSource().getMessage("hdispatch.tip.delete_success",null,locale);
			obj=new ResponseData(true);
		}
		obj.setMessage(message);
		return obj;
	}
	/**
	 * 查询依赖流
	 * @param request
	 * @param flow_id
	 * @param project_name
	 * @return
	 */
	@RequestMapping("/queryDep")
	@ResponseBody
	public ResponseData queryDep(HttpServletRequest request,
			@RequestParam("flow_id") String flow_id,
			@RequestParam("project_name") String project_name
			)
	{

		return new ResponseData(service.selectDepWithId(flow_id,service.selectIdByName(project_name)));
	}
	/**
	 * 查询互斥流
	 * @param request
	 * @param flow_id
	 * @param project_name
	 * @return
	 */
	@RequestMapping("/queryMut")
	@ResponseBody
	public ResponseData queryMut(HttpServletRequest request,
			@RequestParam("flow_id") String flow_id,
			@RequestParam("project_name") String project_name
			)
	{
		return new ResponseData(service.selectMutWithId(flow_id, service.selectIdByName(project_name)));
	}
}
