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
@Transactional("hdispatchTM")
public class DepFlowsServiceImpl implements DepFlowsService {
@Autowired
DepFlowsMapper mapper;

@Override
public int insertDep(DepFlows record) {
	return mapper.insertDep(record);
}

@Override
public int insertMut(DepFlows record) {
	return mapper.insertMut(record);
}

@Override
public List<DepFlows> selectDepWithId(String flow_id,Integer project_id) {
	return mapper.selectDepWithId(flow_id,project_id);
}

@Override
public List<DepFlows> selectMutWithId(String flow_id,Integer project_id) {
	return mapper.selectMutWithId(flow_id,project_id);
}

@Override
public int deleteDep(DepFlows record) {
	return mapper.deleteDep(record);
}

@Override
public int deleteMut(DepFlows record) {
	return mapper.deleteMut(record);
}

@Override
public int isExitDep(DepFlows record) {
	return mapper.isExitDep(record);
}

@Override
public int isExitMut(DepFlows record) {
	return mapper.isExitMut(record);
}

	@Override
	public int selectIdByName(String project_name) {
		return mapper.selectIdByName(project_name);
	}

}
