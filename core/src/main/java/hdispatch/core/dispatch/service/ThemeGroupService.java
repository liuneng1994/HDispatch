package hdispatch.core.dispatch.service;

import com.hand.hap.core.IRequest;
import hdispatch.core.dispatch.dto.authority.ThemeGroup;

import java.util.List;

/**
 * 主题组service接口<br>
 * Created by yyz on 2016/10/17.
 *
 * @author yazheng.yang@hand-china.com
 */
public interface ThemeGroupService {
    /**
     * 根据ThemeGroup进行模糊查询
     * @param requestContext
     * @param themeGroup
     * @param page
     * @param pageSize
     * @return
     */
    List<ThemeGroup> selectByThemeGroup(IRequest requestContext, ThemeGroup themeGroup, int page, int pageSize);

    /**
     * 批量操作（增加和修改）
     * @param requestContext
     * @param themeGroupList
     * @return
     */
    List<ThemeGroup> batchUpdate(IRequest requestContext, List<ThemeGroup> themeGroupList);

    /**
     * 批量删除
     * @param requestContext
     * @param themeGroupList
     * @param cannotRemoveList
     */
    void batchDelete(IRequest requestContext, List<ThemeGroup> themeGroupList, List<ThemeGroup> cannotRemoveList);
}
