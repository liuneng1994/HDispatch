package hdispatch.core.dispatch.controllers;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;

import hdispatch.core.dispatch.azkaban.service.ExeFlowService;
import hdispatch.core.dispatch.azkaban.util.ResultObj;
import hdispatch.core.dispatch.dto.HdispatchGroup;
import hdispatch.core.dispatch.service.HdispatchGroupService;
import hdispatch.core.dispatch.service.ThemeService;
import hdispatch.core.dispatch.service.impl.ThemeServiceImpl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by yyz on 2016/9/6.
 * yazheng.yang@hand-china.com
 */
@Controller
public class HdispatchGroupController {
   @Autowired
   private HdispatchGroupService service;
   
   @Autowired
   private ExeFlowService exeFlowservice;

   /**
    * 获取全部group及相关信息
    * @param request
    * @param page
    * @param pagesize
    * @return
    */
    @RequestMapping("/group/selectAll")
    @ResponseBody
    public ResponseData query(HttpServletRequest request,
                              @RequestParam int page,
                              @RequestParam int pagesize) {
        PageHelper.startPage(page,pagesize);
        List<HdispatchGroup> list=service.selectAll();
        return new ResponseData(list);
    }
    /**
     * 执行group
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/group/startGroup")
    @ResponseBody
    public ResultObj start(HttpServletRequest request,
                              @RequestParam String id
                              ) {
    	System.out.println("----------"+id);
    	//exeFlowservice.ExecuteFlow(null);
    	ResultObj obj=new ResultObj();
    	obj.setCode(1);
    	obj.setMessage("启动成功");
        return obj;
    }
    /**
     * 恢复执行group
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/group/resumeGroup")
    @ResponseBody
    public ResultObj resume(HttpServletRequest request,
                              @RequestParam String id
                              ) {
    	System.out.println("----------"+id);
    	//exeFlowservice.ExecuteFlow(null);
    	ResultObj obj=new ResultObj();
    	obj.setCode(1);
    	obj.setMessage("恢复成功");
        return obj;
    }
    /**
     * 暂停group
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/group/pauseGroup")
    @ResponseBody
    public ResultObj pause(HttpServletRequest request,
                              @RequestParam String id
                              ) {
    	System.out.println("----------"+id);
    	//exeFlowservice.ExecuteFlow(null);
    	ResultObj obj=new ResultObj();
    	obj.setCode(1);
    	obj.setMessage("暂停成功");
        return obj;
    }
    /**
     * 停止group
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/group/stopGroup")
    @ResponseBody
    public ResultObj stop(HttpServletRequest request,
                              @RequestParam String id
                              ) {
    	System.out.println("----------"+id);
    	//exeFlowservice.ExecuteFlow(null);
    	ResultObj obj=new ResultObj();
    	obj.setCode(1);
    	obj.setMessage("停止成功");
        return obj;
    }
}
