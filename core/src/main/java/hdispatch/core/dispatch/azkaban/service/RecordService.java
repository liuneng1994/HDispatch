package hdispatch.core.dispatch.azkaban.service;

import hdispatch.core.dispatch.azkaban.entity.record.RECExecutionHistory;
import hdispatch.core.dispatch.azkaban.entity.record.RECExecutionSince;

import java.util.Date;
import java.util.List;

/**
 * Created by yyz on 2016/9/1.
 * yazheng.yang@hand-china.com
 */
public interface RecordService {
    List<String> fetchExecutionJobLogs(String execId, String jobId, int offset, int length);

    /**
     * 获取一次执行流中所有的job
     */
    RECExecutionHistory fetchFlowExecution(String execId);

    /**
     * 获取自某个时刻之后被执行的job的信息
     */
    RECExecutionSince fetchFlowExecInfoSince(String execId, Date date);

    /**
     * 获取自某个时刻之后被执行的job的信息
     *
     * @param lastUpdateTime 距离1976年的毫秒数
     */
    RECExecutionSince fetchFlowExecInfoSince(String execId, long lastUpdateTime);

}
