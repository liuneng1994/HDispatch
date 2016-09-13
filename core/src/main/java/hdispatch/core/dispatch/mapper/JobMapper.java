package hdispatch.core.dispatch.mapper;

import hdispatch.core.dispatch.dto.job.Job;

import java.util.List;

/**
 * Created by yyz on 2016/9/11.
 * yazheng.yang@hand-china.com
 */
public interface JobMapper {
    List<Job> selectByJob(Job job);
    /**
     * 检测是否已经在当前层中存在,确保theme、layer存在
     */
    Job selectByNameAndActiveAndLayer(Job job);

    void create(Job job);

    void deleteInLogic(Job job);
}
