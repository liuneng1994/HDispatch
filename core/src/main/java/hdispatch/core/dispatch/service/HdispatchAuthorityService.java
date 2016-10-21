package hdispatch.core.dispatch.service;

import com.hand.hap.core.IRequest;
import hdispatch.core.dispatch.dto.authority.HdispatchAuthority;

import java.util.List;

/**
 * Created by yyz on 2016/10/20.
 *
 * @author yazheng.yang@hand-china.com
 *
 * 任务调度权限服务接口类
 */
public interface HdispatchAuthorityService {
    /**
     * 模糊查询主题组下面的所有已分配权限的用户
     * @param hdispatchAuthority 用到：(themeGroupId、userName),
     * @return
     */
    List<HdispatchAuthority> selectInThemeGroup(IRequest requestContext, HdispatchAuthority hdispatchAuthority, int page, int pageSize);

    /**
     * 模糊查询不在当前主题组的所有主题
     * @param hdispatchAuthority 用到：(themeGroupId、userName),
     * @return
     */
    List<HdispatchAuthority> selectNotInThemeGroup(IRequest requestContext,HdispatchAuthority hdispatchAuthority, int page, int pageSize);


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

    List<HdispatchAuthority> batchUpdate(IRequest requestContext, List<HdispatchAuthority> filterList);
}
