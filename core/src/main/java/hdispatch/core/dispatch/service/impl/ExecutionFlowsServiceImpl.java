package hdispatch.core.dispatch.service.impl;

import hdispatch.core.dispatch.dto.ExecutionFlows;
import hdispatch.core.dispatch.mappers.ExecutionFlowsMapper;
import hdispatch.core.dispatch.service.ExecutionFlowsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;

import java.util.List;

@Service
@Transactional("hdispatchTM")
public class ExecutionFlowsServiceImpl implements ExecutionFlowsService {
@Autowired
ExecutionFlowsMapper mapper;

@Override
public List<ExecutionFlows> selectAll(IRequest requestContext,ExecutionFlows exe) {
	return mapper.selectAllExes(exe);
}
}
