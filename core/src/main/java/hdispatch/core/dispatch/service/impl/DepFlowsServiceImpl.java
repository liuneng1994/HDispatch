package hdispatch.core.dispatch.service.impl;


import hdispatch.core.dispatch.dto.DepFlows;
import hdispatch.core.dispatch.mapper.DepFlowsMapper;
import hdispatch.core.dispatch.service.DepFlowsService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DepFlowsServiceImpl implements DepFlowsService {
@Autowired
DepFlowsMapper mapper;

@Override
public int insertDep(DepFlows record) {
	// TODO Auto-generated method stub
	return mapper.insertDep(record);
}

@Override
public int insertMut(DepFlows record) {
	// TODO Auto-generated method stub
	return mapper.insertMut(record);
}

@Override
public List<DepFlows> selectDepWithId(String flow_id,Integer project_id) {
	// TODO Auto-generated method stub
	return mapper.selectDepWithId(flow_id,project_id);
}

@Override
public List<DepFlows> selectMutWithId(String flow_id,Integer project_id) {
	// TODO Auto-generated method stub
	return mapper.selectMutWithId(flow_id,project_id);
}

@Override
public int deleteDep(DepFlows record) {
	// TODO Auto-generated method stub
	return mapper.deleteDep(record);
}

@Override
public int deleteMut(DepFlows record) {
	// TODO Auto-generated method stub
	return mapper.deleteMut(record);
}

@Override
public int isExitDep(DepFlows record) {
	// TODO Auto-generated method stub
	return mapper.isExitDep(record);
}

@Override
public int isExitMut(DepFlows record) {
	// TODO Auto-generated method stub
	return mapper.isExitMut(record);
}

}
