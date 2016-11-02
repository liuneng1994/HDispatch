package hdispatch.core.dispatch.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.dto.ResponseData;
import hdispatch.core.dispatch.dto.job.Job;
import hdispatch.core.dispatch.dto.job.TreeNode;
import hdispatch.core.dispatch.service.JobService;
import hdispatch.core.dispatch.service.SvnFileSysService;
import hdispatch.core.dispatch.utils.group.Create;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 任务控制器<br>
 *     note:重新命名，因为在HAP原先的系统中存在JobController这个类，冲突了，改名为HdispatchJobController
 * Created by yyz on 2016/9/11.
 * @author yazheng.yang@hand-china.com
 */
@Controller
public class HdispatchJobController extends BaseController {
    private Logger logger = Logger.getLogger(HdispatchJobController.class);
    @Autowired
    private JobService jobService;
    @Autowired
    private SvnFileSysService svnFileSysService;


    /**
     * 根据主题id，层次id以及job名称模糊查询job
     */
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

    /**
     * 批量新增job
     * @param jobList 新建的job list
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(value = "/dispatcher/job/submit", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseData addJobs(@RequestBody @Validated(Create.class) List<Job> jobList, BindingResult result, HttpServletRequest request) {

        ResponseData rd = null;
        //后台验证
        getValidator().validate(jobList, result);
        if (result.hasErrors()) {
            rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }

        rd = doCreateJob(jobList,request);
        return rd;
    }

    /**
     * 供addJobs和batchCreateJobs复用
     * description:检查待插入的job是否已经存在；若存在，给出提示信息；若不存在，执行插入
     */
    private ResponseData doCreateJob(List<Job> jobList,HttpServletRequest request){
        ResponseData rd = null;

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
        //获取语言环境
        Locale locale = RequestContextUtils.getLocale(request);
        if (flag) {
            rd = new ResponseData(false);
            //以下任务已经存在
            String errorMsg = getMessageSource().getMessage("hdispatch.job.job_create.job_name_already_exist", null, locale);
            rd.setMessage(errorMsg+":" + sb.toString());
            return rd;
        }
        IRequest requestContext = createRequestContext(request);

        try {
            rd = new ResponseData(jobService.batchUpdate(requestContext, jobList));
        } catch (Exception e) {
            //保存任务中途失败
            String errorMsg = getMessageSource().getMessage("hdispatch.job.job_create.error_during_saving", null, locale);
            logger.error(errorMsg, e);
            rd = new ResponseData(false);
            rd.setMessage(errorMsg);
            return rd;
        }

        return rd;
    }

    /**
     * 批量删除job
     */
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
        //获取语言环境
        Locale locale = RequestContextUtils.getLocale(request);
        try {
            List<Job> jobListReturn = jobService.batchUpdate(requestContext, jobList);
            rd = new ResponseData(true);
            rd.setMessage("success");
        } catch (Exception e) {
            //删除任务中途失败
            String errorMsg = getMessageSource().getMessage("hdispatch.job.job_create.error_during_deleting", null, locale);
            logger.error(errorMsg, e);
            rd = new ResponseData(false);
            rd.setMessage(errorMsg);
            return rd;
        }
        return rd;
    }


    /**
     * 根据节点ID获取Kettle文件结构
     * @param request
     * @param nodeId 当前节点相对于设定的根目录的完整路径
     * @return
     */
    @RequestMapping(value = "/dispatcher/job/svnTree/query", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData getSvnTreeNodes(HttpServletRequest request,
                                @RequestParam(defaultValue = "") String nodeId) {
        IRequest requestContext = createRequestContext(request);
        ResponseData responseData = null;
        nodeId = nodeId.trim();
        TreeNode treeNode = new TreeNode();
        treeNode.setNodeId(nodeId.trim());
        //获取语言环境
        Locale locale = RequestContextUtils.getLocale(request);
        try {
            List<TreeNode> treeNodeList = svnFileSysService.fetchSubNodes(treeNode);
            responseData = new ResponseData(treeNodeList);
        } catch (Exception e) {
            //访问SVN服务器出错
            String errorMsg = getMessageSource().getMessage("hdispatch.job.job_create.error_during_access_svn_server", null, locale);
            logger.error(errorMsg,e);
            responseData = new ResponseData(false);
            responseData.setMessage(errorMsg);
            return responseData;
        }
        return responseData;
    }


    /**
     * 根据Kettle文件列表批量创建job（每一个kettle文件对应一个job，job的名称为kettle文件名）
     */
    @RequestMapping(value = "/dispatcher/job/batchSubmit", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseData batchCreateJobs(HttpServletRequest request,
                                        @RequestBody JSONObject batchCreateJob_data) {
        Long themeId = batchCreateJob_data.getLong("themeId");
        Long layerId = batchCreateJob_data.getLong("layerId");
        String jobSvn = batchCreateJob_data.getString("jobSvn");
        //后台验证
        String[] jobSvns = jobSvn.split(",");
        if(themeId < 0 || layerId < 0 || jobSvn.trim().equals("") || jobSvns.length < 1){
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage("illegal parameters");
            return responseData;
        }

        List<Job> jobList = new ArrayList<Job>();
        for(String s : jobSvns){
            Job jobTemp = new Job();
            jobTemp.setThemeId(themeId);
            jobTemp.setLayerId(layerId);
            jobTemp.setJobSvn(s);
            jobTemp.set__status(DTOStatus.ADD);

            int startIndex = s.lastIndexOf("/");
            int endIndex = s.lastIndexOf(".");
            String jobName_temp = s.substring(startIndex+1,endIndex);

            jobTemp.setJobName(jobName_temp);

            jobList.add(jobTemp);
        }

        return this.doCreateJob(jobList,request);
    }

    /**
     * 批量更新job
     * @param jobList
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(value = "/dispatcher/job/update", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseData updateJobs(@RequestBody List<Job> jobList, BindingResult result, HttpServletRequest request) {

        ResponseData rd = null;
        //后台验证
        getValidator().validate(jobList, result);
        if (result.hasErrors()) {
            rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        rd = new ResponseData(jobService.batchUpdate(requestContext,jobList));
        return rd;
    }
}
