package hdispatch.core.dispatch.service.impl;

import hdispatch.core.dispatch.dto.ExecutionFlows;
import hdispatch.core.dispatch.mapper.ExecutionFlowsMapper;
import hdispatch.core.dispatch.service.ExecutionFlowsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;

import java.util.List;

/**
 * 任务流实现类
 * created by dengzhilong
 * zhilong.deng@hand-china.com
 */
@Service
@Transactional
public class ExecutionFlowsServiceImpl implements ExecutionFlowsService {
	@Autowired
	ExecutionFlowsMapper mapper;

	/**
	 * 查询所有流
 	 * @param requestContext
	 * @param exe
     * @return
     */
	@Override
	@Transactional
	public List<ExecutionFlows> selectAll(IRequest requestContext,ExecutionFlows exe) {
		return mapper.selectAllExes(exe);
	}
}
