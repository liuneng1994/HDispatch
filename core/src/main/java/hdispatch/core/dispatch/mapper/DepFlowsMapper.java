package hdispatch.core.dispatch.mapper;

import java.util.List;

import hdispatch.core.dispatch.dto.DepFlows;

public interface DepFlowsMapper {
    int insertDep(DepFlows record);

    int insertMut(DepFlows record);
    
    List<DepFlows> selectDepWithId(String flow_id,Integer project_id);

    List<DepFlows> selectMutWithId(String flow_id,Integer project_id);

    int deleteDep(DepFlows record);

    int deleteMut(DepFlows record);

    int isExitDep(DepFlows record);
    
    int isExitMut(DepFlows record);
}