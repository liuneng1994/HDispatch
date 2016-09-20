package hdispatch.core.dispatch.service;

import java.util.List;

import com.hand.hap.system.service.IBaseService;

import hdispatch.core.dispatch.dto.ExecutionFlows;
import hdispatch.core.dispatch.dto.HdispatchSchedule;

public interface HdispatchScheduleService{

	List<HdispatchSchedule> selectAll(HdispatchSchedule s);
	int insert(HdispatchSchedule s);
	int delete(HdispatchSchedule s);
}
