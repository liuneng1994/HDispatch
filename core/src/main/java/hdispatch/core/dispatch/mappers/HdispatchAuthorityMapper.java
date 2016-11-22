package hdispatch.core.dispatch.mappers;

import hdispatch.core.dispatch.dto.authority.HdispatchAuthority;
import hdispatch.core.dispatch.dto.theme.Theme;

import java.util.List;
import java.util.Map;

/**
 * 任务调度权限mapper接口<br>
 * Created by yyz on 2016/10/20.
 *
 * @author yazheng.yang@hand-china.com
 */
public interface HdispatchAuthorityMapper {
    /**
     * 模糊查询主题组下面的所有已分配权限的用户
     * @param params Map里面的内容：(themeGroupId、userName),
     *               其中themeGroupId不可为null，userName可以为null
     * @return
     */
    List<HdispatchAuthority> selectInThemeGroup(Map params);

    /**
     * 模糊查询不在当前主题组的所有主题
     * @param params Map里面的内容：(themeGroupId、userName),
     *               其中themeGroupId不可为null，userName可以为null
     * @return
     */
    List<HdispatchAuthority> selectNotInThemeGroup(Map params);


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
     * 权限校验,查询对特定主题有相关权限的用户列表
     * @param params Map里面的内容：（themeId、userId、authRead、authOperate），
     *               其中，themeId、userId不为null，authRead、authOperate至多一个为null
     * @return
     */
    List<HdispatchAuthority> selectAuthorityForValidate(Map params);

    /**
     * 获取用户可以读、操作的主题列表,
     * @param params Map里面的内容：（userId、authRead、authOperate），
     *               其中，userId不为null，authRead、authOperate至多一个为null
     * @return
     */
    List<Theme> selectThemesUnderUser(Map params);
}
