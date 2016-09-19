package hdispatch.core.dispatch.mapper;

import java.util.List;

import hdispatch.core.dispatch.dto.HdispatchSchedule;

public interface HdispatchScheduleMapper {
    int deleteByPrimaryKey(HdispatchSchedule s);

    int insert(HdispatchSchedule record);

    int insertSelective(HdispatchSchedule record);

    HdispatchSchedule selectByPrimaryKey(Long sch_id);

    int updateByPrimaryKeySelective(HdispatchSchedule record);

    int updateByPrimaryKey(HdispatchSchedule record);

	List<HdispatchSchedule> selectAll(HdispatchSchedule s);
}