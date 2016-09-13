package hdispatch.core.dispatch.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import hdispatch.core.dispatch.dto.job.Job;
import hdispatch.core.dispatch.mapper.JobMapper;
import hdispatch.core.dispatch.service.JobService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyz on 2016/9/11.
 * yazheng.yang@hand-china.com
 */
@Service
public class JobServiceImpl implements JobService {
    private Logger logger = Logger.getLogger(JobServiceImpl.class);
    @Autowired
    private JobMapper jobMapper;
    @Override
    public List<Job> selectByJob(IRequest requestContext, Job job, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Job> list;
        if(null == jobMapper){
            list = new ArrayList<Job>();
            logger.error("jobMapper没有注入");
        }
        else {
            list = jobMapper.selectByJob(job);
        }
        return list;
    }

    @Override
    public boolean[] checkIsExist(List<Job> jobList) {
        boolean[] isExist = new boolean[jobList.size()];
        int i = 0;
        for(Job job : jobList){
            Job jobReturn = jobMapper.selectByNameAndActiveAndLayer(job);
            if(null != jobReturn){
                isExist[i] = true;
            }
            i ++;
        }

        return isExist;
    }

    @Override
    public List<Job> batchUpdate(IRequest requestContext, List<Job> jobList) {
        for (Job job : jobList) {
            if (job.get__status() != null) {
                switch (job.get__status()) {
                    case DTOStatus.ADD:
                        jobMapper.create(job);
                        job.setJobActive(1);
                        break;
                    case DTOStatus.UPDATE:

                        break;
                    case DTOStatus.DELETE:
                        jobMapper.deleteInLogic(job);
                        job.setJobActive(0);
                        break;
                    default:
                        break;
                }
            }
        }
        return jobList;
    }
}
