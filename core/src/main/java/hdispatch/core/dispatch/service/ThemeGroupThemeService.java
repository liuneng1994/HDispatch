package hdispatch.core.dispatch.service;

import com.hand.hap.core.IRequest;
import hdispatch.core.dispatch.dto.authority.ThemeGroupTheme;

import java.util.List;

/**
 * 处理主题组和主题之间的挂载关系的service接口<br>
 *
 * Created by yyz on 2016/10/18.
 *
 * @author yazheng.yang@hand-china.com
 */
public interface ThemeGroupThemeService {
    /**
     * 获取不在主题组下的主题列表
     * @param requestContext
     * @param themeGroupTheme
     * @param page
     * @param pageSize
     * @return 不在主题组下的主题列表
     */
    List<ThemeGroupTheme> selectThemesNotInThemeGroup(IRequest requestContext, ThemeGroupTheme themeGroupTheme, int page, int pageSize);

    /**
     * 获取在主题组下的主题列表
     * @param requestContext
     * @param themeGroupTheme
     * @param page
     * @param pageSize
     * @return 在主题组下的主题列表
     */
    List<ThemeGroupTheme> selectThemesInThemeGroup(IRequest requestContext, ThemeGroupTheme themeGroupTheme, int page, int pageSize);

    /**
     * 批量编辑（增加和删除，不需要修改）
     * @param requestContext
     * @param filterList
     * @return
     */
    List<ThemeGroupTheme> batchUpdate(IRequest requestContext, List<ThemeGroupTheme> filterList);
}
