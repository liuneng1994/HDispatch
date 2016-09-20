package hdispatch.core.dispatch.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.BaseDTO;
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
 * @author yazheng.yang@hand-china.com
 *
 * 任务服务实现类
 */
@Service
public class JobServiceImpl implements JobService {
    private Logger logger = Logger.getLogger(JobServiceImpl.class);
    @Autowired
    private JobMapper jobMapper;

    /**
     * 根据job对象进行查询
     * @param requestContext
     * @param job
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List<Job> selectByJob(IRequest requestContext, Job job, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Job> list;
        if (null == jobMapper) {
            list = new ArrayList<Job>();
            logger.error("jobMapper没有注入");
        } else {
            list = jobMapper.selectByJob(job);
        }
        return list;
    }

    /**
     * 检查是否已经在数据库中存在
     * @param jobList
     * @return
     */
    @Override
    public boolean[] checkIsExist(List<Job> jobList) {
        boolean[] isExist = new boolean[jobList.size()];
        int i = 0;
        for (Job job : jobList) {
            Job jobReturn = jobMapper.selectByNameAndActiveAndLayer(job);
            if (null != jobReturn) {
                isExist[i] = true;
            }
            i++;
        }

        return isExist;
    }

    /**
     * 批量操作：插入、删除、更新
     * @param requestContext
     * @param jobList
     * @return
     */
    @Override
    public List<Job> batchUpdate(IRequest requestContext, List<Job> jobList) {
        for (Job job : jobList) {
            if (job.get__status() != null) {
                switch (job.get__status()) {
                    case DTOStatus.ADD:
                        jobMapper.create(job);
                        job.setJobActive(1L);
                        job.set__status("");
                        break;
                    case DTOStatus.UPDATE:

                        break;
                    case DTOStatus.DELETE:
                        jobMapper.deleteInLogic(job);
                        job.setJobActive(0L);
                        break;
                    default:
                        break;
                }
            }
        }
        return jobList;
    }
}
