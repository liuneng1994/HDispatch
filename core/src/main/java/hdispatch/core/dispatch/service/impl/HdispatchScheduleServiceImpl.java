package hdispatch.core.dispatch.service.impl;

import com.hand.hap.core.IRequest;
import hdispatch.core.dispatch.dto.HdispatchSchedule;
import hdispatch.core.dispatch.mapper_hdispatch.HdispatchScheduleMapper;
import hdispatch.core.dispatch.service.HdispatchScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional("hdispatchTM")
public class HdispatchScheduleServiceImpl implements HdispatchScheduleService {
@Autowired
HdispatchScheduleMapper mapper;

@Override
public List<HdispatchSchedule> selectAll(IRequest requestContext,HdispatchSchedule s) {
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

@Override
public int selectByFlowAndProject(HdispatchSchedule s) {
	// TODO Auto-generated method stub
	return mapper.selectByFlowAndProject(s).size();
}


}
