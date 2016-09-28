package hdispatch.core.dispatch.service.impl;

import hdispatch.core.dispatch.dto.HdispatchGroup;
import hdispatch.core.dispatch.mapper.HdispatchGroupMapper;
import hdispatch.core.dispatch.service.HdispatchGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HdispatchGroupServiceImpl implements HdispatchGroupService {
    @Autowired
    private HdispatchGroupMapper mapper;

    public List<HdispatchGroup> selectAll() {
        /*List l=new ArrayList();
        l.add(mapper.selectByPrimaryKey(1L));*/
        return mapper.selectWithAll();
    }
}
