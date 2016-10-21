package hdispatch.core.dispatch.mapper;

import hdispatch.core.dispatch.dto.authority.HdispatchAuthority;

import java.util.List;
import java.util.Map;

/**
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
}
