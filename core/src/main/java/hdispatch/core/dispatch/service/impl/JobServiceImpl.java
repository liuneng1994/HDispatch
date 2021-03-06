package hdispatch.core.dispatch.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.IBaseService;
import hdispatch.core.dispatch.dto.job.Job;
import hdispatch.core.dispatch.mapper_hdispatch.JobMapper;
import hdispatch.core.dispatch.service.JobService;
import org.apache.log4j.Logger;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 任务service接口实现类<br>
 */
@Service
public class JobServiceImpl extends HdispatchBaseServiceImpl<Job> implements JobService {
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
    @Transactional(transactionManager = "hdispatchTM",propagation = Propagation.SUPPORTS)
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
    @Transactional(transactionManager = "hdispatchTM",propagation = Propagation.SUPPORTS)
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
    @Transactional(transactionManager = "hdispatchTM",rollbackFor = Exception.class)
    public List<Job> batchUpdate(IRequest requestContext, List<Job> jobList, Map<String,String> feedbackMsg) {
        IBaseService<Job> self = ((IBaseService<Job>) AopContext.currentProxy());
        for (Job job : jobList) {
            if (job.get__status() != null) {
                switch (job.get__status()) {
                    case DTOStatus.ADD:
                        job.setJobActive(1L);
                        jobMapper.create(job);
                        job.set__status("");
                        break;
                    case DTOStatus.UPDATE:
                        jobMapper.updateById(job);
                        //防止反复提交
                        job.set__status("");
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
