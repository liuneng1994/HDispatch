package hdispatch.core.dispatch.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hdispatch.core.dispatch.dto.job.Job;

import java.util.List;
import java.util.Map;

/**
 * 任务service接口<br>
 * Created by yyz on 2016/9/11.
 * @author yazheng.yang@hand-china.com
 */
public interface JobService extends IBaseService<Job>, ProxySelf<JobService> {
    /**
     * 根据job对象进行查询
     */
    List<Job> selectByJob(IRequest requestContext, Job job, int page, int pageSize);

    /**
     * 检查是否已经在数据库中存在
     */
    boolean[] checkIsExist(List<Job> jobList);

    /**
     * 批量操作：插入、删除、更新
     */
    List<Job> batchUpdate(IRequest requestContext, List<Job> jobList, Map<String,String> feedbackMsg);
}
