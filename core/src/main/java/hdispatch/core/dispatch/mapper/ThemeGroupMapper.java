package hdispatch.core.dispatch.mapper;

import hdispatch.core.dispatch.dto.authority.ThemeGroup;

import java.util.List;

/**
 * Created by yyz on 2016/10/14.
 *
 * @author yazheng.yang@hand-china.com
 */
public interface ThemeGroupMapper {

    void save(ThemeGroup themeGroup);
    List<ThemeGroup> selectAll();
    List<ThemeGroup> selectByThemeGroup(ThemeGroup themeGroup);
    /**
     * 根据id对job进行更新
     * @param themeGroup
     */
    void updateById(ThemeGroup themeGroup);

}
