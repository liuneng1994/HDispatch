package hdispatch.core.dispatch.controllers;

import com.hand.hap.job.AbstractJob;
import hdispatch.core.dispatch.mapper_hdispatch.ExecutionFlowsMapper;
import hdispatch.core.dispatch.mapper_hdispatch.ExecutionJobsMapper;
import hdispatch.core.dispatch.service.HdispatchLogCleanService;
import hdispatch.core.dispatch.utils.ConfigUtil;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * 清理设置的N天前的日志
 *
 * Created by yyz on 2017/1/11.
 *
 * @author yazheng.yang@hand-china.com
 */
public class HdispatchLogCleaner extends AbstractJob{
    @Autowired
    private HdispatchLogCleanService hdispatchLogCleanService;
    public static final long MILLSECOND_EACH_DAY = 24*3600*1000;

    @Override
    public void safeExecute(JobExecutionContext jobExecutionContext) throws Exception {
        hdispatchLogCleanService.cleanLogsBefore(new Date(new Date().getTime()- ConfigUtil.getLogs_remain_days()*MILLSECOND_EACH_DAY));
    }

    @Override
    public boolean isRefireImmediatelyWhenException() {
        return false;
    }
}
