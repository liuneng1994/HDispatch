package hdispatch.core.dispatch.service.impl;

import hdispatch.core.dispatch.mapper_hdispatch.ExecutionFlowsMapper;
import hdispatch.core.dispatch.mapper_hdispatch.ExecutionJobsMapper;
import hdispatch.core.dispatch.service.HdispatchLogCleanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * Created by yyz on 2017/1/11.
 *
 * @author yazheng.yang@hand-china.com
 */
@Service
public class HdispatchLogCleanServiceImpl implements HdispatchLogCleanService {
    @Autowired
    private ExecutionFlowsMapper executionFlowsMapper;
    @Autowired
    private ExecutionJobsMapper executionJobsMapper;

    @Override
    public void cleanLogs() {
        cleanLogsBefore(new Date());
    }

    @Override
    @Transactional(transactionManager = "hdispatchTM",rollbackFor = Exception.class)
    public void cleanLogsBefore(Date beforeDate) {
        Assert.notNull(beforeDate);
        executionJobsMapper.cleanLogsBefore(new Long(beforeDate.getTime()));
        executionFlowsMapper.cleanLogsBefore(new Long(beforeDate.getTime()));
    }
}
