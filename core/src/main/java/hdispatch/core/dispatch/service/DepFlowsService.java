package hdispatch.core.dispatch.service;

import hdispatch.core.dispatch.dto.DepFlows;
import hdispatch.core.dispatch.dto.ExecutionFlows;

import java.util.List;

public interface DepFlowsService{

	int insertDep(DepFlows record);

    int insertMut(DepFlows record);
    
    List<DepFlows> selectDepWithId(String flow_id,Integer project_id);

    List<DepFlows> selectMutWithId(String flow_id,Integer project_id);
    
    int deleteDep(DepFlows record);

    int deleteMut(DepFlows record);
    
    int isExitDep(DepFlows record);
    
    int isExitMut(DepFlows record);

    int selectIdByName(String project_name);
}
