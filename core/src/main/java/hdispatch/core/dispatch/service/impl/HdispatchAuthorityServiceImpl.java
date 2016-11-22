package hdispatch.core.dispatch.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.account.dto.User;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import hdispatch.core.dispatch.dto.authority.HdispatchAuthority;
import hdispatch.core.dispatch.dto.theme.Theme;
import hdispatch.core.dispatch.mappers.HdispatchAuthorityMapper;
import hdispatch.core.dispatch.service.HdispatchAuthorityService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务调度权限service接口实现类<br>
 * Created by yyz on 2016/10/20.
 * @author yazheng.yang@hand-china.com
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
    @Transactional("hdispatchTM")
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
    @Transactional("hdispatchTM")
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
    @Transactional("hdispatchTM")
    public void save(HdispatchAuthority hdispatchAuthority) {
        hdispatchAuthorityMapper.save(hdispatchAuthority);
    }

    /**
     * 更新用户的权限
     * @param hdispatchAuthority
     */
    @Override
    @Transactional("hdispatchTM")
    public void updateAuthority(HdispatchAuthority hdispatchAuthority) {
        hdispatchAuthorityMapper.updateAuthority(hdispatchAuthority);
    }

    /**
     * 删除主题组下的用户
     * @param hdispatchAuthority
     */
    @Override
    @Transactional("hdispatchTM")
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
    @Transactional("hdispatchTM")
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

    /**
     * 是否对主题有：读权限
     * @param theme
     * @param user
     * @return
     */
    @Override
    @Transactional("hdispatchTM")
    public boolean hasReadPermission(Theme theme, User user) {
        return hasReadPermission(theme.getThemeId(),user.getUserId());
    }

    /**
     * 是否对主题有：读权限
     * @param themeId
     * @param userId
     * @return
     */
    @Override
    @Transactional("hdispatchTM")
    public boolean hasReadPermission(Long themeId, Long userId) {
        if(!checkNotNull(userId,themeId))
            return false;
        Map<String,Object> map = new HashMap();
        map.put("userId",userId);
        map.put("themeId",themeId);
        map.put("authRead","Y");
        map.put("authOperate",null);

        List<HdispatchAuthority> authorityList = hdispatchAuthorityMapper.selectAuthorityForValidate(map);

        return (null != authorityList) && (authorityList.size() > 0);
    }

    /**
     * 是否对主题有：操作权限
     * @param theme
     * @param user
     * @return
     */
    @Override
    @Transactional("hdispatchTM")
    public boolean hasOperatePermission(Theme theme, User user) {
        return hasOperatePermission(theme.getThemeId(),user.getUserId());
    }

    /**
     * 是否对主题有：操作权限
     * @param themeId
     * @param userId
     * @return
     */
    @Override
    @Transactional("hdispatchTM")
    public boolean hasOperatePermission(Long themeId, Long userId) {
        if(!checkNotNull(userId,themeId))
            return false;
        Map<String,Object> map = new HashMap();
        map.put("userId",userId);
        map.put("themeId",themeId);
        map.put("authRead",null);
        map.put("authOperate","Y");

        List<HdispatchAuthority> authorityList = hdispatchAuthorityMapper.selectAuthorityForValidate(map);

        return (null != authorityList) && (authorityList.size() > 0);
    }

    /**
     * 是否对主题有：读和操作权限
     * @param theme
     * @param user
     * @return
     */
    @Override
    @Transactional("hdispatchTM")
    public boolean hasReadAndOperatePermission(Theme theme, User user) {
        return hasReadAndOperatePermission(theme.getThemeId(),user.getUserId());
    }

    /**
     * 是否对主题有：读和操作权限
     * @param themeId
     * @param userId
     * @return
     */
    @Override
    @Transactional("hdispatchTM")
    public boolean hasReadAndOperatePermission(Long themeId, Long userId) {
        if(!checkNotNull(userId,themeId))
            return false;
        Map<String,Object> map = new HashMap();
        map.put("userId",userId);
        map.put("themeId",themeId);
        map.put("authRead","Y");
        map.put("authOperate","Y");

        List<HdispatchAuthority> authorityList = hdispatchAuthorityMapper.selectAuthorityForValidate(map);

        return (null != authorityList) && (authorityList.size() > 0);
    }

    /**
     * 用户可以访问(读)的主题
     * @param userId
     * @return 返回的只是主题id（themeId）列表
     */
    @Override
    @Transactional("hdispatchTM")
    public List<Theme> themesReadByUser(Long userId) {
        if(null == userId)
            return null;
        Map<String,Object> map = new HashMap();
        map.put("userId",userId);
        map.put("authRead","Y");
        map.put("authOperate",null);

        return hdispatchAuthorityMapper.selectThemesUnderUser(map);
    }

    /**
     * 用户可以操作的主题
     * @param userId
     * @return 返回的只是主题id（themeId）列表
     */
    @Override
    @Transactional("hdispatchTM")
    public List<Theme> themesOperateByUser(Long userId) {
        if(null == userId)
            return null;
        Map<String,Object> map = new HashMap();
        map.put("userId",userId);
        map.put("authRead",null);
        map.put("authOperate","Y");

        return hdispatchAuthorityMapper.selectThemesUnderUser(map);
    }

    /**
     * 用户可读、可操作的主题
     * @param userId
     * @return 返回的只是主题id（themeId）列表
     */
    @Override
    @Transactional("hdispatchTM")
    public List<Theme> themesReadAndOperateByUser(Long userId) {
        if(null == userId)
            return null;
        Map<String,Object> map = new HashMap();
        map.put("userId",userId);
        map.put("authRead","Y");
        map.put("authOperate","Y");

        return hdispatchAuthorityMapper.selectThemesUnderUser(map);
    }

    private boolean checkNotNull(Long userId, Long themeId){
        if(null == userId || null == themeId){
            return false;
        }
        return true;
    }
}
