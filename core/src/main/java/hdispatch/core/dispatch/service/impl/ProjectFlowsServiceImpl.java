package hdispatch.core.dispatch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import hdispatch.core.dispatch.dto.ProjectFlows;
import hdispatch.core.dispatch.mappers.ProjectFlowsMapper;
import hdispatch.core.dispatch.service.ProjectFlowsService;
@Service
@Transactional("hdispatchTM")
public class ProjectFlowsServiceImpl implements ProjectFlowsService {
@Autowired
ProjectFlowsMapper mapper;

@Override
public List<ProjectFlows> selectAllWithDistinct() {
	// TODO Auto-generated method stub
	return mapper.selectAllWithDistinct();
}


}
