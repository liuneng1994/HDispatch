package hdispatch.core.dispatch.mapper_hdispatch;

import hdispatch.core.dispatch.dto.HdispatchGroup;

import java.util.List;

public interface HdispatchGroupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HdispatchGroup record);

    int insertSelective(HdispatchGroup record);

    HdispatchGroup selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HdispatchGroup record);

    int updateByPrimaryKey(HdispatchGroup record);

	List<HdispatchGroup> selectWithAll();
}