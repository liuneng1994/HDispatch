package hdispatch.core.dispatch.service.impl;

import hdispatch.core.dispatch.dto.ExecutionJobs;
import hdispatch.core.dispatch.mapper.ExecutionJobsMapper;
import hdispatch.core.dispatch.service.ExecutionJobsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExecutionJobsServiceImpl implements ExecutionJobsService {
@Autowired
ExecutionJobsMapper mapper;

@Override
public List<ExecutionJobs> selectJobsByFlow(ExecutionJobs exe) {
	// TODO Auto-generated method stub
	return mapper.selectJobsByFlow(exe);
}



}
