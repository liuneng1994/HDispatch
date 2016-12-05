package hdispatch.core.dispatch.service.impl;


import hdispatch.core.dispatch.dto.DepFlows;
import hdispatch.core.dispatch.mapper.DepFlowsMapper;
import hdispatch.core.dispatch.service.DepFlowsService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *互斥依赖流实现类
 * Created by dengzhilong on 2016/9/5.
 * zhilong.deng@hand-china.com
 */
@Service
@Transactional
public class DepFlowsServiceImpl implements DepFlowsService {
	@Autowired
	DepFlowsMapper mapper;

	/**
	 * 插入依赖流
	 * @param record
	 * @return
     */
	@Override
	@Transactional
	public int insertDep(DepFlows record) {
		return mapper.insertDep(record);
	}

	/**
	 * 插入互斥流
	 * @param record
     * @return
     */
	@Override
	@Transactional
	public int insertMut(DepFlows record) {
		return mapper.insertMut(record);
	}

	/**
	 * 通过flowid和projectid查询依赖流
	 * @param flow_id
	 * @param project_id
     * @return
     */
	@Override
	@Transactional
	public List<DepFlows> selectDepWithId(String flow_id,Integer project_id) {
		return mapper.selectDepWithId(flow_id,project_id);
	}

	/**
	 * 通过flowid projectid查询互斥流
	 * @param flow_id
	 * @param project_id
     * @return
     */
	@Override
	@Transactional
	public List<DepFlows> selectMutWithId(String flow_id,Integer project_id) {
		return mapper.selectMutWithId(flow_id,project_id);
	}

	/**
	 * 删除依赖流
	 * @param record
     * @return
     */
	@Override
	@Transactional
	public int deleteDep(DepFlows record) {
		return mapper.deleteDep(record);
	}

	/**
	 * 删除互斥流
	 * @param record
     * @return
     */
	@Override
	@Transactional
	public int deleteMut(DepFlows record) {
		return mapper.deleteMut(record);
	}

	/**
	 * 判断是否存在依赖流
	 * @param record
     * @return
     */
	@Override
	@Transactional
	public int isExitDep(DepFlows record) {
		return mapper.isExitDep(record);
	}

	/**
	 * 判断是否存在互斥流
	 * @param record
     * @return
     */
	@Override
	@Transactional
	public int isExitMut(DepFlows record) {
		return mapper.isExitMut(record);
	}

	/**
	 * 通过名字返回peojectid
	 * @param project_name
     * @return
     */
	@Override
	@Transactional
	public int selectIdByName(String project_name) {
		return mapper.selectIdByName(project_name);
	}

}
