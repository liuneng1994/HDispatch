package hdispatch.core.dispatch.mapper_hdispatch;

import java.util.List;

import hdispatch.core.dispatch.dto.ProjectFlows;
import hdispatch.core.dispatch.dto.ProjectFlowsKey;

public interface ProjectFlowsMapper {
    int deleteByPrimaryKey(ProjectFlowsKey key);

    int insert(ProjectFlows record);

    int insertSelective(ProjectFlows record);

    ProjectFlows selectByPrimaryKey(ProjectFlowsKey key);

    int updateByPrimaryKeySelective(ProjectFlows record);

    int updateByPrimaryKeyWithBLOBs(ProjectFlows record);

    int updateByPrimaryKey(ProjectFlows record);

	List<ProjectFlows> selectAllWithDistinct();
}