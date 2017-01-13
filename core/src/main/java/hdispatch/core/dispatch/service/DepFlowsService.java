package hdispatch.core.dispatch.service;

import hdispatch.core.dispatch.dto.DepFlows;
import hdispatch.core.dispatch.dto.ExecutionFlows;

import java.util.List;

/**
 * 互斥依赖流service接口类
 * created by dengzhilong
 * zhilong.deng@hand-china.com
 */
public interface DepFlowsService{
    /**
     * 插入依赖流
     * @param record
     * @return
     */
	int insertDep(DepFlows record);

    /**
     * 插入互斥流
     * @param record
     * @return
     */
    int insertMut(DepFlows record);

    /**
     * 查询依赖流
     * @param flow_id
     * @param project_id
     * @return
     */
    List<DepFlows> selectDepWithId(String flow_id,Integer project_id);

    /**
     * 查询互斥流
     * @param flow_id
     * @param project_id
     * @return
     */
    List<DepFlows> selectMutWithId(String flow_id,Integer project_id);

    /**
     * 删除依赖流
     * @param record
     * @return
     */
    int deleteDep(DepFlows record);

    /**
     * 删除互斥流
     * @param record
     * @return
     */
    int deleteMut(DepFlows record);

    /**
     * 判断是否存在依赖流
     * @param record
     * @return
     */
    int isExitDep(DepFlows record);

    /**
     * 判断是否存在互斥流
     * @param record
     * @return
     */
    int isExitMut(DepFlows record);

    /**
     * 通过name返回peojectid
     * @param project_name
     * @return
     */
    int selectIdByName(String project_name);
}
