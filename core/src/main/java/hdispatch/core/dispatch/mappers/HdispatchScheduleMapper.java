package hdispatch.core.dispatch.mappers;

import hdispatch.core.dispatch.dto.HdispatchSchedule;

import java.util.List;

public interface HdispatchScheduleMapper {
    int deleteByPrimaryKey(HdispatchSchedule s);

    int insert(HdispatchSchedule record);

    int insertSelective(HdispatchSchedule record);

    HdispatchSchedule selectByPrimaryKey(Long sch_id);

    int updateByPrimaryKeySelective(HdispatchSchedule record);

    int updateByPrimaryKey(HdispatchSchedule record);

	List<HdispatchSchedule> selectAll(HdispatchSchedule s);

    List<HdispatchSchedule> selectByFlowAndProject(HdispatchSchedule s);
}