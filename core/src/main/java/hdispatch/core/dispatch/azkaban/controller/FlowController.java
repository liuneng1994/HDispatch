package hdispatch.core.dispatch.azkaban.controller;


import hdispatch.core.dispatch.azkaban.entity.flow.ExeFlow;
import hdispatch.core.dispatch.azkaban.flow.FlowObj;
import hdispatch.core.dispatch.azkaban.service.ExeFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 邓志龙 on 2016/8/31.
 */
@Controller
public class FlowController {
    @Autowired
    ExeFlowService service;

    /**
     * 获取工程下所有流
     *
     * @param projectName
     * @return
     */
    @ResponseBody
    public Object fetchFlows(@PathVariable("projectName") String projectName) {
        System.out.println(service.Fetchflows(projectName));
        return service.Fetchflows(projectName);
    }

    /**
     * 获取执行的流
     *
     * @param projectName
     * @param flowId
     * @param start
     * @param length
     * @return
     */
    @ResponseBody
    public Object fetchExeFlows(@PathVariable("projectName") String projectName, @PathVariable("flowId") String flowId, @PathVariable("start") Integer start, @PathVariable("length") Integer length) {
        //@RequestParam String projectName, @RequestParam Long flowId, @RequestParam  Integer start, @RequestParam  Integer length
        System.out.println(projectName + "--" + length);
        System.out.println(service.FetchExeFlows(projectName, flowId, start, length).toString());
        return service.FetchExeFlows(projectName, flowId, start, length).toString();
    }

    /**
     * 获取运行中的流
     *
     * @param project
     * @param flow
     * @return
     */
    @ResponseBody
    public Object fetchRunningFlow(@PathVariable("project") String project, @PathVariable("flow") String flow) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("project", project);
        map.put("flow", flow);
        return service.FetchRunningFlow(map).getExecids();
    }

    /**
     * 获取流的作业
     *
     * @param project
     * @param flow
     * @return
     */
    @ResponseBody
    public Object fetchJobs(@PathVariable("project") String project, @PathVariable("flow") String flow) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("project", project);
        map.put("flow", flow);
        System.out.println(project + "-------------" + flow);
        return service.FetchJobs(map).getNodes().toString();
    }

    /**
     * 执行流
     * 执行流的某个作业时 需要将流的其他作业设置为disabled=["job_name1","job_name2"]
     *
     * @return
     */
    @ResponseBody
    public Object ExecuteFlow() {
        FlowObj obj = new FlowObj();
        return service.ExecuteFlow(obj).toString();
    }

    /**
     * z
     * 取消流的执行
     *
     * @param execid
     * @return
     */
    @ResponseBody
    public String cancelFlow(@PathVariable("execid") Long execid) {
        //@RequestParam String projectName, @RequestParam Long flowId, @RequestParam  Integer start, @RequestParam  Integer length
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("execid", execid);
        return service.CancelFlow(map).getError();
    }

    /**
     * 暂停一个流的执行
     *
     * @param execid
     * @return
     */
    @ResponseBody
    public String pauseFlow(@PathVariable("execid") Long execid) {
        //@RequestParam String projectName, @RequestParam Long flowId, @RequestParam  Integer start, @RequestParam  Integer length
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("execid", execid);
        ExeFlow f = service.CancelFlow(map);
        if (f.isError()) {
            return f.getError();
        } else {
            return "";
        }

    }

    /**
     * 恢复流的运行
     *
     * @param execid
     * @return
     */
    @ResponseBody
    public String resumeFlow(@PathVariable("execid") Long execid) {
        //@RequestParam String projectName, @RequestParam Long flowId, @RequestParam  Integer start, @RequestParam  Integer length
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("execid", execid);
        ExeFlow f = service.ResumeFlow(map);
        if (f.isError()) {
            return f.getError();
        } else {
            return "SUCCESS";
        }

    }
}
