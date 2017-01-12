package hdispatch.core.dispatch.mapper_hdispatch;

import com.hand.hap.mybatis.common.Mapper;
import hdispatch.core.dispatch.dto.job.Job;

import java.util.Collection;
import java.util.List;

/**
 * 任务mapper接口<br>
 * Created by yyz on 2016/9/11.
 * @author yazheng.yang@hand-china.com
 */
public interface JobMapper extends Mapper<Job>{
    /**
     * 根据job对象进行查询
     * @param job
     * @return
     */
    List<Job> selectByJob(Job job);

    /**
     * @author neng.liu@hand-china.com
     * 根据ID列表查询job
     * @param ids
     * @return
     */
    List<Job> getByIds(Collection<Long> ids);
    /**
     * 检测是否已经在当前层中存在,确保theme、layer存在
     */
    Job selectByNameAndActiveAndLayer(Job job);

    /**
     * 新建job
     * @param job
     */
    void create(Job job);

    /**
     * 逻辑删除job
     * @param job
     */
    void deleteInLogic(Job job);

    /**
     * 根据id对job进行更新
     * @param job
     */
    void updateById(Job job);
}
