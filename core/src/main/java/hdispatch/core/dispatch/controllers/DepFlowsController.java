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
	public ResultObj insertDep(HttpServletRequest request, @RequestBody List<DepFlows>flows)
	{
		ResultObj obj=new ResultObj();
		String message="";
		for (DepFlows flow:flows) {
			if(service.isExitDep(flow)>0)
			{
				message+=flow.getFlow_id()+"设置依赖重复  ";
			}else
			{
			int i=service.insertDep(flow);
			message+=flow.getFlow_id()+"设置成功  ";
			}
		}
		obj.setMessage(message);
		return obj;
	}
	/**
	 * 设置互斥流
	 * @param request
	 * @param flows
	 * @return
	 */
	@RequestMapping("/insertMut")
	@ResponseBody
	public ResultObj insertMut(HttpServletRequest request, @RequestBody List<DepFlows>flows)
	{
		ResultObj obj=new ResultObj();
		String message="";
		for (DepFlows flow:flows) {
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
	public ResultObj deleteDep(HttpServletRequest request, @RequestBody List<DepFlows>flows)
	{
		ResultObj obj=new ResultObj();
		String message="";
		for (DepFlows flow:flows) {
			service.deleteDep(flow);
			message+=flow.getFlow_id()+"刪除成功  ";
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
	public ResultObj deleteMut(HttpServletRequest request, @RequestBody List<DepFlows>flows)
	{
		ResultObj obj=new ResultObj();
		String message="";
		for (DepFlows flow:flows) {

			service.deleteMut(flow);
			message+=flow.getFlow_id()+"刪除成功  ";
		}
		obj.setMessage(message);
		return obj;
	}
	/**
	 * 查询依赖流
	 * @param request
	 * @param flow_id
	 * @param project_id
	 * @return
	 */
	@RequestMapping("/queryDep")
	@ResponseBody
	public List<DepFlows> queryDep(HttpServletRequest request,
			@RequestParam String flow_id,
			@RequestParam Integer project_id
			)
	{
		return service.selectDepWithId(flow_id, project_id);
	}
	/**
	 * 查询互斥流
	 * @param request
	 * @param flow_id
	 * @param project_id
	 * @return
	 */
	@RequestMapping("/queryMut")
	@ResponseBody
	public List<DepFlows> queryMut(HttpServletRequest request,
			@RequestParam String flow_id,
			@RequestParam Integer project_id
			)
	{
		return service.selectMutWithId(flow_id, project_id);
	}
}
