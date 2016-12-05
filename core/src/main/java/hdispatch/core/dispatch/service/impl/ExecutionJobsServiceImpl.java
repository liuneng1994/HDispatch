package hdispatch.core.dispatch.service.impl;

import hdispatch.core.dispatch.dto.ExecutionJobs;
import hdispatch.core.dispatch.mapper_hdispatch.ExecutionJobsMapper;
import hdispatch.core.dispatch.service.ExecutionJobsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * azkaban作业实现类
 * created by dengzhilong
 * zhilong.deng@hand-china.com
 */
@Service
@Transactional("hdispatchTM")
public class ExecutionJobsServiceImpl implements ExecutionJobsService {
	@Autowired
	ExecutionJobsMapper mapper;

	/**
	 * 通过流查询作业列表
	 * @param exe
     * @return
     */
	@Override
	@Transactional
	public List<ExecutionJobs> selectJobsByFlow(ExecutionJobs exe) {
		// TODO Auto-generated method stub
		return mapper.selectJobsByFlow(exe);
	}



}
