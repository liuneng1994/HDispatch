package hdispatch.core.dispatch.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.IBaseService;
import hdispatch.core.dispatch.dto.HdispatchSchedule;

import java.util.List;

/**
 * 调度计划service实现类
 * created by dengzhilong
 * zhilong.deng@hand-china.com
 */
public interface HdispatchScheduleService{

	/**
	 * 查询所有的调度计划
	 * @param requestContext
	 * @param s
     * @return
     */
	List<HdispatchSchedule> selectAll(IRequest requestContext,HdispatchSchedule s);

	/**
	 * 插入调度计划
	 * @param s
	 * @return
     */
	int insert(HdispatchSchedule s);

	/**
	 * 删除调度计划
	 * @param s
	 * @return
     */
	int delete(HdispatchSchedule s);

	/**
	 * 通过flow和peoject查询调度计划
	 * @param s
	 * @return
     */
	int selectByFlowAndProject(HdispatchSchedule s);
}
