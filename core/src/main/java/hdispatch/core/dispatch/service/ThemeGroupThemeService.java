package hdispatch.core.dispatch.service;

import com.hand.hap.core.IRequest;
import hdispatch.core.dispatch.dto.authority.ThemeGroupTheme;

import java.util.List;

/**
 * 处理主题组和主题映射关系的service接口<br>
 */
public interface ThemeGroupThemeService {
    /**
     * 根据 ThemeGroupTheme 模糊查询不在主题组下的主题
     * @param requestContext
     * @param themeGroupTheme
     * @param page
     * @param pageSize
     * @return
     */
    List<ThemeGroupTheme> selectThemesNotInThemeGroup(IRequest requestContext, ThemeGroupTheme themeGroupTheme, int page, int pageSize);

    /**
     * 根据 ThemeGroupTheme 模糊查询主题组下的主题
     * @param requestContext
     * @param themeGroupTheme
     * @param page
     * @param pageSize
     * @return
     */
    List<ThemeGroupTheme> selectThemesInThemeGroup(IRequest requestContext, ThemeGroupTheme themeGroupTheme, int page, int pageSize);

    /**
     * 批量编辑（增加和删除，没有必要修改）
     * @param requestContext
     * @param filterList
     * @return
     */
    List<ThemeGroupTheme> batchUpdate(IRequest requestContext, List<ThemeGroupTheme> filterList);
}
