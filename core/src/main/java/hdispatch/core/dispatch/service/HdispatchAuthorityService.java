package hdispatch.core.dispatch.service;

import com.hand.hap.account.dto.User;
import com.hand.hap.core.IRequest;
import hdispatch.core.dispatch.dto.authority.HdispatchAuthority;
import hdispatch.core.dispatch.dto.theme.Theme;

import java.util.List;

/**
 * 任务调度权限service接口类<br>
 *
 */
public interface HdispatchAuthorityService {
    /**
     * 模糊查询主题组下面的所有已分配权限的用户
     * @param requestContext
     * @param hdispatchAuthority 用到：(themeGroupId),
     * @param usersMatchUserName 已经匹配userName的用户列表
     * @return
     */
    List<HdispatchAuthority> selectInThemeGroup(IRequest requestContext, HdispatchAuthority hdispatchAuthority, List<HdispatchAuthority> usersMatchUserName, int page, int pageSize);

    /**
     * 模糊查询不在当前主题组的所有主题
     * @param hdispatchAuthority 用到：(userName),
     * @param usersInThemeGroup 在主题组中已经分配权限的用户id列表
     * @return
     */
    List<HdispatchAuthority> selectNotInThemeGroup(IRequest requestContext,HdispatchAuthority hdispatchAuthority, List<HdispatchAuthority> usersInThemeGroup, int page, int pageSize);

    /**
     * 模糊查询用户列表
     * @param requestContext
     * @param hdispatchAuthorit 用到userId、userName
     * @return
     */
    List<HdispatchAuthority> selectUsers(IRequest requestContext, HdispatchAuthority hdispatchAuthorit);

    /**
     * 获取主题组下的所有用户id
     * @param hdispatchAuthority 用到：(themeGroupId),
     * @return
     */
    List<HdispatchAuthority> selectUsersInThemeGroup(IRequest requestContext,HdispatchAuthority hdispatchAuthority);


    /**
     * 保存用户权限
     * @param hdispatchAuthority
     */
    void save(HdispatchAuthority hdispatchAuthority);

    /**
     * 更新用户权限
     * @param hdispatchAuthority
     */
    void updateAuthority(HdispatchAuthority hdispatchAuthority);

    /**
     * 删除主题组下的用户
     * @param hdispatchAuthority
     */
    void deleteUser(HdispatchAuthority hdispatchAuthority);

    /**
     * 批量更新权限
     * @param requestContext
     * @param filterList
     * @return
     */
    List<HdispatchAuthority> batchUpdate(IRequest requestContext, List<HdispatchAuthority> filterList);

    /**
     * 是否对主题有：读权限
     * @param theme
     * @param user
     * @return
     */
    boolean hasReadPermission(Theme theme, User user);

    /**
     * 是否对主题有：读权限
     * @param themeId
     * @param userId
     * @return
     */
    boolean hasReadPermission(Long themeId, Long userId);

    /**
     * 是否对主题有：操作权限
     * @param theme
     * @param user
     * @return
     */
    boolean hasOperatePermission(Theme theme, User user);

    /**
     * 是否对主题有：操作权限
     * @param themeId
     * @param userId
     * @return
     */
    boolean hasOperatePermission(Long themeId, Long userId);

    /**
     * 是否对主题有：读和操作权限
     * @param theme
     * @param user
     * @return
     */
    boolean hasReadAndOperatePermission(Theme theme, User user);

    /**
     * 是否对主题有：读和操作权限
     * @param themeId
     * @param userId
     * @return
     */
    boolean hasReadAndOperatePermission(Long themeId, Long userId);

    /**
     * 用户可以访问(读)的主题
     * @param userId
     * @return
     */
    List<Theme> themesReadByUser(Long userId);

    /**
     * 用户可以操作的主题
     * @param userId
     * @return
     */
    List<Theme> themesOperateByUser(Long userId);

    /**
     * 用户可读、可操作的主题
     * @param userId
     * @return
     */
    List<Theme> themesReadAndOperateByUser(Long userId);
}
