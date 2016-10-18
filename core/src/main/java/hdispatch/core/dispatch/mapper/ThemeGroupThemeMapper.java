package hdispatch.core.dispatch.mapper;

import hdispatch.core.dispatch.dto.authority.ThemeGroupTheme;

import java.util.List;
import java.util.Map;

/**
 * Created by yyz on 2016/10/17.
 *
 * @author yazheng.yang@hand-china.com
 */
public interface ThemeGroupThemeMapper {
    /**
     * 获取主题组下面的所有主题
     * @param themeGroupId
     * @return
     */
    List<ThemeGroupTheme> selectInThemeGroup(Long themeGroupId);

    /**
     * 模糊查询不在当前主题组的所有主题
     * Map里面的内容：(themeGroupId、themeName、themeDescription)
     * 其中themeGroupId不可为null，其他两个字段可以为null
     * @param params
     * @return
     */
    List<ThemeGroupTheme> selectNotInThemeGroup(Map params);

}
