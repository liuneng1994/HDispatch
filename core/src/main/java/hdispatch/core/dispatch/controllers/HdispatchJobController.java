package hdispatch.core.dispatch.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hdispatch.core.dispatch.dto.job.Job;
import hdispatch.core.dispatch.service.JobService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping(value = "/dispatcher/job/query", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData getJobs(HttpServletRequest request,
                                  @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                                  @RequestParam(defaultValue = "-100") int themeId,
                                  @RequestParam(defaultValue = "-100") int layerId,
                                  @RequestParam(defaultValue = "") String jobName) {
        IRequest requestContext = createRequestContext(request);
        Job job = new Job();
        if(!jobName.trim().equals("")){
            job.setJobName(jobName.trim());
        }
        job.setThemeId(themeId);
        job.setLayerId(layerId);
        job.setJobId(-100);
        List<Job> jobList = jobService.selectByJob(requestContext, job, page, pageSize);

        ResponseData responseData = new ResponseData(jobList);

        return responseData;
    }

}
