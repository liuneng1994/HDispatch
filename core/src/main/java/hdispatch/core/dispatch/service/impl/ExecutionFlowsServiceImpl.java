package hdispatch.core.dispatch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.system.service.impl.BaseServiceImpl;

import hdispatch.core.dispatch.dto.ExecutionFlows;
import hdispatch.core.dispatch.mapper.ExecutionFlowsMapper;
import hdispatch.core.dispatch.service.ExecutionFlowsService;
@Service
@Transactional
public class ExecutionFlowsServiceImpl implements ExecutionFlowsService {
@Autowired
ExecutionFlowsMapper mapper;

@Override
public List<ExecutionFlows> selectAll(ExecutionFlows exe) {
	// TODO Auto-generated method stub
	return mapper.selectAllExes(exe);
}
}
