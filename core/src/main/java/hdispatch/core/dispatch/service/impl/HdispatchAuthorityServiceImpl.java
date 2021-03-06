package hdispatch.core.dispatch.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.account.dto.User;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import hdispatch.core.dispatch.dto.authority.HdispatchAuthority;
import hdispatch.core.dispatch.dto.theme.Theme;
import hdispatch.core.dispatch.mapper.HdispatchUserMapper;
import hdispatch.core.dispatch.mapper_hdispatch.HdispatchAuthorityMapper;
import hdispatch.core.dispatch.service.HdispatchAuthorityService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 任务调度权限service接口实现类<br>
 */
@Service
public class HdispatchAuthorityServiceImpl implements HdispatchAuthorityService {
    private Logger logger = Logger.getLogger(HdispatchAuthorityServiceImpl.class);
    @Autowired
    private HdispatchAuthorityMapper hdispatchAuthorityMapper;
    @Autowired
    private HdispatchUserMapper hdispatchUserMapper;

    /**
     * 模糊查询主题组下面的所有已分配权限的用户
     * @param requestContext
     * @param hdispatchAuthority 用到：(themeGroupId),
     * @param usersMatchUserName 已经匹配userName的用户列表
     * @return
     */
    @Override
    @Transactional("hdispatchTM")
    public List<HdispatchAuthority> selectInThemeGroup(IRequest requestContext, HdispatchAuthority hdispatchAuthority, List<HdispatchAuthority> usersMatchUserName, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);

        Set<Long> userIdCollection = new HashSet<Long>();
        Map<Long,String> userIdToNameMap = new HashMap<Long,String>();
        usersMatchUserName.forEach(user -> {
            userIdCollection.add(user.getUserId());
            userIdToNameMap.put(user.getUserId(),user.getUserName());
        });

        Map<String,Object> map = new HashMap();
        map.put("themeGroupId",hdispatchAuthority.getThemeGroupId());
        map.put("userIdCollection",userIdCollection);
        List<HdispatchAuthority> authorityList = hdispatchAuthorityMapper.selectInThemeGroup(map);

        authorityList.forEach(authority -> authority.setUserName(userIdToNameMap.getOrDefault(authority.getUserId(),"")));

        return authorityList;
    }

    /**
     * 模糊查询用户列表
     * @param requestContext
     * @param hdispatchAuthorit 用到userId、userName
     * @return
     */
    @Override
    public List<HdispatchAuthority> selectUsers(IRequest requestContext, HdispatchAuthority hdispatchAuthorit) {
        return hdispatchUserMapper.selectUser(null,hdispatchAuthorit.getUserName());
    }

    /**
     * 获取主题组下的所有用户id
     * @param hdispatchAuthority 用到：(themeGroupId),
     * @return
     */
    @Override
    public List<HdispatchAuthority> selectUsersInThemeGroup(IRequest requestContext, HdispatchAuthority hdispatchAuthority) {
        Map<String,Object> parameterMap = new HashedMap();
        parameterMap.put("themeGroupId",hdispatchAuthority.getThemeGroupId());
        parameterMap.put("userName",null);
        return hdispatchAuthorityMapper.selectNotInThemeGroup(parameterMap);
    }

    /**
     * 模糊查询主题组没有分配权限的用户
     * @param requestContext
     * @param hdispatchAuthority 用到：(userName),
     * @param usersInThemeGroup 在主题组中已经分配权限的用户id列表
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    @Transactional("hdispatchTM")
    public List<HdispatchAuthority> selectNotInThemeGroup(IRequest requestContext,HdispatchAuthority hdispatchAuthority, List<HdispatchAuthority> usersInThemeGroup, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        if(null == usersInThemeGroup || 0 == usersInThemeGroup.size()){
            return hdispatchUserMapper.selectNotInThemeGroup(null,hdispatchAuthority.getUserName());
        }
        Set<Long> userIdsInThemeGroup = new HashSet<>();
        usersInThemeGroup.forEach(user -> userIdsInThemeGroup.add(user.getUserId()));
        return hdispatchUserMapper.selectNotInThemeGroup(userIdsInThemeGroup,hdispatchAuthority.getUserName());
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
