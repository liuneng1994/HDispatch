package hdispatch.core.dispatch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.system.service.impl.BaseServiceImpl;

import hdispatch.core.dispatch.dto.ExecutionFlows;
import hdispatch.core.dispatch.dto.HdispatchSchedule;
import hdispatch.core.dispatch.mapper.ExecutionFlowsMapper;
import hdispatch.core.dispatch.mapper.HdispatchScheduleMapper;
import hdispatch.core.dispatch.service.ExecutionFlowsService;
import hdispatch.core.dispatch.service.HdispatchScheduleService;
@Service
@Transactional
public class HdispatchScheduleServiceImpl implements HdispatchScheduleService {
@Autowired
HdispatchScheduleMapper mapper;

@Override
public List<HdispatchSchedule> selectAll(HdispatchSchedule s) {
	// TODO Auto-generated method stub
	return mapper.selectAll(s);
}

@Override
public int insert(HdispatchSchedule s) {
	// TODO Auto-generated method stub
	return mapper.insert(s);
}

@Override
public int delete(HdispatchSchedule s) {
	// TODO Auto-generated method stub
	return mapper.deleteByPrimaryKey(s);
}


}
