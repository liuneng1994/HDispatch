package hdispatch.core.dispatch.service;

import com.hand.hap.core.IRequest;
import hdispatch.core.dispatch.dto.HdispatchSchedule;

import java.util.List;

public interface HdispatchScheduleService{

	List<HdispatchSchedule> selectAll(IRequest requestContext,HdispatchSchedule s);
	int insert(HdispatchSchedule s);
	int delete(HdispatchSchedule s);
	HdispatchSchedule selectByFlowAndProject(HdispatchSchedule s);
}
