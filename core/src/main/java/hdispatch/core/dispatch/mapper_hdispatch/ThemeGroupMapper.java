package hdispatch.core.dispatch.mapper_hdispatch;

import com.hand.hap.mybatis.common.Mapper;
import hdispatch.core.dispatch.dto.authority.ThemeGroup;

import java.util.List;

/**
 * 任务组mapper接口<br>
 */
public interface ThemeGroupMapper extends Mapper<ThemeGroup> {

    void save(ThemeGroup themeGroup);
    List<ThemeGroup> selectAll();
    List<ThemeGroup> selectByThemeGroup(ThemeGroup themeGroup);
    /**
     * 根据id对job进行更新
     * @param themeGroup
     */
    void updateById(ThemeGroup themeGroup);

    /**
     * 删除主题组
     * @param themeGroup
     */
    void deleteThemeGroup(ThemeGroup themeGroup);
}
