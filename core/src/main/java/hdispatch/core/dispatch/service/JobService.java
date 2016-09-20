package hdispatch.core.dispatch.service;

import com.hand.hap.core.IRequest;
import hdispatch.core.dispatch.dto.job.Job;

import java.util.List;

/**
 * Created by yyz on 2016/9/11.
 * @author yazheng.yang@hand-china.com
 *
 * 任务服务接口类
 */
public interface JobService {
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
    List<Job> batchUpdate(IRequest requestContext, List<Job> jobList);
}
