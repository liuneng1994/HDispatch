package hdispatch.core.dispatch.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;
import hdispatch.core.dispatch.dto.HdispatchSchedule;
import hdispatch.core.dispatch.mapper_hdispatch.HdispatchScheduleMapper;
import hdispatch.core.dispatch.service.HdispatchScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 调度实现类
 * created by dengzhilong
 * zhilong.deng@hand-china.com
 */
@Service
@Transactional("hdispatchTM")
public class HdispatchScheduleServiceImpl extends HdispatchBaseServiceImpl<HdispatchSchedule> implements HdispatchScheduleService {
@Autowired
HdispatchScheduleMapper mapper;

	/**
	 * 查询所有调度计划
	 * @param requestContext
	 * @param s
     * @return
     */
	@Override
	@Transactional
	public List<HdispatchSchedule> selectAll(IRequest requestContext,HdispatchSchedule s) {
		// TODO Auto-generated method stub
		return mapper.selectAllSchedule(s);
	}

	/**
	 * 通过flow和project查询调度计划的数量
	 * @param
     * @return
     */
	@Override
	@Transactional
	public int selectByFlowAndProject(HdispatchSchedule s) {
		// TODO Auto-generated method stub
		return mapper.selectByFlowAndProject(s).size();
	}

	@Override
	public int deleteByProject(HdispatchSchedule s) {
		return mapper.deleteByProject(s);
	}

	@Override
	public int insertByProject(HdispatchSchedule s) {
		return mapper.insertByProject(s);
	}
}
