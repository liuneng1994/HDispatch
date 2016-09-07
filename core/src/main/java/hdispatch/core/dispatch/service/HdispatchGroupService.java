package hdispatch.core.dispatch.service;

import java.util.List;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hdispatch.core.dispatch.dto.HdispatchGroup;

/**
 * Created by yyz on 2016/9/5.
 * yazheng.yang@hand-china.com
 */
public interface HdispatchGroupService{

	List<HdispatchGroup> selectAll();
}
