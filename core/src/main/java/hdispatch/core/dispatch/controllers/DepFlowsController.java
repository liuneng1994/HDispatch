package hdispatch.core.dispatch.controllers;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hdispatch.core.dispatch.azkaban.entity.flow.ExeFlow;
import hdispatch.core.dispatch.azkaban.service.ExeFlowService;
import hdispatch.core.dispatch.azkaban.util.ResultObj;
import hdispatch.core.dispatch.dto.DepFlows;
import hdispatch.core.dispatch.dto.ExecutionFlows;
import hdispatch.core.dispatch.dto.ExecutionJobs;
import hdispatch.core.dispatch.service.DepFlowsService;
import hdispatch.core.dispatch.service.ExecutionFlowsService;
import hdispatch.core.dispatch.service.ExecutionJobsService;
import hdispatch.core.dispatch.service.impl.DepFlowsServiceImpl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		ResponseData result = null;
		result = new ResponseData(true);
		String message="";
		for (DepFlows flow:flows) {
			flow.setProject_id(service.selectIdByName(flow.getProject_name()));
			if(service.isExitDep(flow)>0)
			{
				message+=flow.getFlow_id()+"设置依赖重复  ";
			}else
			{
			int i=service.insertDep(flow);
			message+=flow.getFlow_id()+"设置成功  ";
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
		ResponseData obj= null;
		obj = new ResponseData(true);
		String message="";
		for (DepFlows flow:flows) {
			flow.setProject_id(service.selectIdByName(flow.getProject_name()));
			if(service.isExitMut(flow)>0)
			{
				message+=flow.getFlow_id()+"设置互斥重复  ";
			}else
			{
			int i=service.insertMut(flow);
			message+=flow.getFlow_id()+"设置成功  ";
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
		ResponseData obj= null;
		String message="";
		for (DepFlows flow:flows) {
			flow.setProject_id(service.selectIdByName(flow.getProject_name()));
			service.deleteDep(flow);
			message+=flow.getFlow_id()+"刪除成功  ";
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
		ResponseData obj= null;
		String message="";
		for (DepFlows flow:flows) {
			flow.setProject_id(service.selectIdByName(flow.getProject_name()));
			service.deleteMut(flow);
			message+=flow.getFlow_id()+"刪除成功  ";
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
			@RequestParam String flow_id,
			@RequestParam String project_name
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
			@RequestParam String flow_id,
			@RequestParam String project_name
			)
	{
		return new ResponseData(service.selectMutWithId(flow_id, service.selectIdByName(project_name)));
	}
}
