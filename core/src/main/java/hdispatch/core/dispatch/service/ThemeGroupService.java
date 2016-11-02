package hdispatch.core.dispatch.service;

import com.hand.hap.core.IRequest;
import hdispatch.core.dispatch.dto.authority.ThemeGroup;

import java.util.List;

/**
 * 任务组service接口<br>
 * Created by yyz on 2016/10/17.
 * @author yazheng.yang@hand-china.com
 */
public interface ThemeGroupService {
    /**
     * 根据任务组模糊查询
     * @param requestContext
     * @param themeGroup
     * @param page
     * @param pageSize
     * @return
     */
    List<ThemeGroup> selectByThemeGroup(IRequest requestContext, ThemeGroup themeGroup, int page, int pageSize);

    /**
     * 批量编辑任务组（增加和修改）
     * @param requestContext
     * @param themeGroupList
     * @return
     */
    List<ThemeGroup> batchUpdate(IRequest requestContext, List<ThemeGroup> themeGroupList);

    /**
     * 批量删除任务组,<br>
     *     如果任务组下没有挂载主题或用户，那么可以删除；否则，不删除，并返回不可以删除的列表
     * @param requestContext
     * @param themeGroupList 待删除的主题组列表
     * @param cannotRemoveList 不可以删除的列表
     */
    void batchDelete(IRequest requestContext, List<ThemeGroup> themeGroupList, List<ThemeGroup> cannotRemoveList);
}
