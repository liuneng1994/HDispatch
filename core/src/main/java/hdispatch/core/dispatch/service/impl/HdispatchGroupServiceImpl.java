package hdispatch.core.dispatch.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import hdispatch.core.dispatch.azkaban.service.ProjectService;
import hdispatch.core.dispatch.azkaban.service.impl.ProjectServiceImpl;
import hdispatch.core.dispatch.dto.HdispatchGroup;
import hdispatch.core.dispatch.mapper.HdispatchGroupMapper;
import hdispatch.core.dispatch.mapper.ThemeMapper;
import hdispatch.core.dispatch.service.HdispatchGroupService;
import hdispatch.core.dispatch.service.ThemeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class HdispatchGroupServiceImpl implements HdispatchGroupService {
   @Autowired
    private HdispatchGroupMapper mapper;

    public List<HdispatchGroup> selectAll(){
        /*List l=new ArrayList();
        l.add(mapper.selectByPrimaryKey(1L));*/
        return mapper.selectWithAll();
    }
}
