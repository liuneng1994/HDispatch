package hdispatch.core.dispatch.mapper;

import java.util.List;

import hdispatch.core.dispatch.dto.HdispatchGroup;

public interface HdispatchGroupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HdispatchGroup record);

    int insertSelective(HdispatchGroup record);

    HdispatchGroup selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HdispatchGroup record);

    int updateByPrimaryKey(HdispatchGroup record);

	List<HdispatchGroup> selectWithAll();
}