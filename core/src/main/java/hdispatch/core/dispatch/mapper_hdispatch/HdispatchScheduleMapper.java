package hdispatch.core.dispatch.mapper_hdispatch;

import com.hand.hap.mybatis.common.BaseMapper;
import com.hand.hap.mybatis.common.Mapper;
import hdispatch.core.dispatch.dto.HdispatchSchedule;

import java.util.List;

public interface HdispatchScheduleMapper extends Mapper<HdispatchSchedule>{

	List<HdispatchSchedule> selectAllSchedule(HdispatchSchedule s);

    List<HdispatchSchedule> selectByFlowAndProject(HdispatchSchedule s);

    int deleteByProject(HdispatchSchedule s);

    int insertByProject(HdispatchSchedule s);
}