package hdispatch.core.dispatch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.system.service.impl.BaseServiceImpl;

import hdispatch.core.dispatch.dto.ExecutionFlows;
import hdispatch.core.dispatch.dto.ExecutionJobs;
import hdispatch.core.dispatch.mapper.ExecutionFlowsMapper;
import hdispatch.core.dispatch.mapper.ExecutionJobsMapper;
import hdispatch.core.dispatch.service.ExecutionFlowsService;
import hdispatch.core.dispatch.service.ExecutionJobsService;
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
