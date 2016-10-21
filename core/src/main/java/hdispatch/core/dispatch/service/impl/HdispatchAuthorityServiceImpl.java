package hdispatch.core.dispatch.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import hdispatch.core.dispatch.dto.authority.HdispatchAuthority;
import hdispatch.core.dispatch.mapper.HdispatchAuthorityMapper;
import hdispatch.core.dispatch.service.HdispatchAuthorityService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yyz on 2016/10/20.
 *
 * @author yazheng.yang@hand-china.com
 *
 * 任务调度权限服务实现类
 */
@Service
public class HdispatchAuthorityServiceImpl implements HdispatchAuthorityService {
    private Logger logger = Logger.getLogger(HdispatchAuthorityServiceImpl.class);
    @Autowired
    private HdispatchAuthorityMapper hdispatchAuthorityMapper;

    /**
     * 模糊查询主题组下的用户
     * @param requestContext
     * @param hdispatchAuthority 用到：(themeGroupId、userName),
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List<HdispatchAuthority> selectInThemeGroup(IRequest requestContext, HdispatchAuthority hdispatchAuthority, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        Map<String,Object> map = new HashMap();
        map.put("themeGroupId",hdispatchAuthority.getThemeGroupId());
        map.put("userName",hdispatchAuthority.getUserName());
        return hdispatchAuthorityMapper.selectInThemeGroup(map);
    }

    /**
     * 模糊查询主题组没有分配权限的用户
     * @param requestContext
     * @param hdispatchAuthority 用到：(themeGroupId、userName),
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List<HdispatchAuthority> selectNotInThemeGroup(IRequest requestContext,HdispatchAuthority hdispatchAuthority, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        Map<String,Object> map = new HashMap();
        map.put("themeGroupId",hdispatchAuthority.getThemeGroupId());
        map.put("userName",hdispatchAuthority.getUserName());
        return hdispatchAuthorityMapper.selectNotInThemeGroup(map);
    }

    /**
     * 创建用户的权限
     * @param hdispatchAuthority
     */
    @Override
    public void save(HdispatchAuthority hdispatchAuthority) {
        hdispatchAuthorityMapper.save(hdispatchAuthority);
    }

    /**
     * 更新用户的权限
     * @param hdispatchAuthority
     */
    @Override
    public void updateAuthority(HdispatchAuthority hdispatchAuthority) {
        hdispatchAuthorityMapper.updateAuthority(hdispatchAuthority);
    }

    /**
     * 删除主题组下的用户
     * @param hdispatchAuthority
     */
    @Override
    public void deleteUser(HdispatchAuthority hdispatchAuthority) {
        hdispatchAuthorityMapper.deleteUser(hdispatchAuthority);
    }

    /**
     * 批量更改
     * @param requestContext
     * @param filterList
     * @return
     */
    @Override
    public List<HdispatchAuthority> batchUpdate(IRequest requestContext, List<HdispatchAuthority> filterList) {
        for (HdispatchAuthority authority : filterList) {
            if (authority.get__status() != null) {
                switch (authority.get__status()) {
                    case DTOStatus.ADD:
                        hdispatchAuthorityMapper.save(authority);
                        break;
                    case DTOStatus.UPDATE:
                        hdispatchAuthorityMapper.updateAuthority(authority);
                        break;
                    case DTOStatus.DELETE:
                        hdispatchAuthorityMapper.deleteUser(authority);
                        break;
                    default:
                        break;
                }
            }
        }
        return filterList;
    }
}
