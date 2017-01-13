package hdispatch.core.dispatch.service;

import hdispatch.core.dispatch.dto.ExecutionFlows;

import java.util.List;

import com.hand.hap.core.IRequest;

/**
 * 任务流service接口类
 * created by dengzhilong
 * zhilong.deng@hand-china.com
 */
public interface ExecutionFlowsService{

	/**
	 * 查询所有流
	 * @param requestContext
	 * @param exe
     * @return
     */
	List<ExecutionFlows> selectAll(IRequest requestContext,ExecutionFlows exe);

}
