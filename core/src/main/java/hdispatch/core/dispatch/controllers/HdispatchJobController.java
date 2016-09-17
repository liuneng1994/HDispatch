package hdispatch.core.dispatch.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hdispatch.core.dispatch.dto.job.Job;
import hdispatch.core.dispatch.dto.job.TreeNode;
import hdispatch.core.dispatch.service.JobService;
import hdispatch.core.dispatch.service.SvnFileSysService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by yyz on 2016/9/11.
 * yazheng.yang@hand-china.com
 *
 * note:重新命名，因为在HAP原先的系统中存在JobController这个类，冲突了，改名为HdispatchJobController
 */
@Controller
public class HdispatchJobController extends BaseController {
    private Logger logger = Logger.getLogger(HdispatchJobController.class);
    @Autowired
    private JobService jobService;
    @Autowired
    private SvnFileSysService svnFileSysService;

    @RequestMapping(value = "/dispatcher/job/query", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData getJobs(HttpServletRequest request,
                                  @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                                  @RequestParam(defaultValue = "-100") Long themeId,
                                  @RequestParam(defaultValue = "-100") Long layerId,
                                  @RequestParam(defaultValue = "") String jobName) {
        IRequest requestContext = createRequestContext(request);
        Job job = new Job();
        if(!jobName.trim().equals("")){
            job.setJobName(jobName.trim());
        }
        job.setThemeId(themeId);
        job.setLayerId(layerId);
        job.setJobId(-100L);
        List<Job> jobList = jobService.selectByJob(requestContext, job, page, pageSize);

        ResponseData responseData = new ResponseData(jobList);

        return responseData;
    }

    @RequestMapping(value = "/dispatcher/job/submit", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseData addJobs(@RequestBody List<Job> jobList, BindingResult result, HttpServletRequest request) {

        ResponseData rd = null;
        //后台验证
        getValidator().validate(jobList, result);
        if (result.hasErrors()) {
            rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }

        //从后台判断是否存在
        boolean[] isExist = jobService.checkIsExist(jobList);
        StringBuilder sb = new StringBuilder();
        boolean flag = false;
        for (int i = 0; i < jobList.size(); i++) {
            if (isExist[i]) {
                if (!flag) {
                    sb.append(jobList.get(i).getJobName());
                } else {
                    sb.append("," + jobList.get(i).getJobName());
                }
                flag = true;
            }
        }
        if (flag) {
            rd = new ResponseData(false);
            rd.setMessage("以下任务已经存在:" + sb.toString());
            return rd;
        }
        IRequest requestContext = createRequestContext(request);

        try {
            rd = new ResponseData(jobService.batchUpdate(requestContext, jobList));
        } catch (Exception e) {
            logger.error("保存任务中途失败", e);
        }
        return rd;
    }

    @RequestMapping(value = "/dispatcher/job/remove", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseData deleteJobs(@RequestBody List<Job> jobList, BindingResult result, HttpServletRequest request) {

        ResponseData rd = null;
        //后台验证
        getValidator().validate(jobList, result);
        if (result.hasErrors()) {
            rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }

        IRequest requestContext = createRequestContext(request);

        try {
            List<Job> jobListReturn = jobService.batchUpdate(requestContext, jobList);
            rd = new ResponseData(jobListReturn);
        } catch (Exception e) {
            logger.error("删除任务中途失败", e);
            rd = new ResponseData(false);
            return rd;
        }
        return rd;
    }


    @RequestMapping(value = "/dispatcher/job/svnTree/query", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData getSvnTreeNodes(HttpServletRequest request,
                                @RequestParam(defaultValue = "") String nodeId) {
        IRequest requestContext = createRequestContext(request);
        ResponseData responseData = null;
//        if(!nodeId.trim().equals("")){
//            responseData = new ResponseData(false);
//            responseData.setMessage("非法访问");
//            return responseData;
//        }
        nodeId = nodeId.trim();
        TreeNode treeNode = new TreeNode();
//        //访问根目录
//        if(nodeId.equals("rootNode")){
//            treeNode.setNodeId("");
//        }else {
//            treeNode.setNodeId(nodeId.trim());
//        }
        treeNode.setNodeId(nodeId.trim());
        try {
            List<TreeNode> treeNodeList = svnFileSysService.fetchSubNodes(treeNode);
            responseData = new ResponseData(treeNodeList);
        } catch (Exception e) {
            logger.error("访问SVN服务器出错",e);
        }
        return responseData;
    }
}
