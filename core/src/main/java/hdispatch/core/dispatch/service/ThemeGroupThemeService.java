package hdispatch.core.dispatch.service;

import com.hand.hap.core.IRequest;
import hdispatch.core.dispatch.dto.authority.ThemeGroupTheme;

import java.util.List;

/**
 * Created by yyz on 2016/10/18.
 *
 * @author yazheng.yang@hand-china.com
 */
public interface ThemeGroupThemeService {
    List<ThemeGroupTheme> selectThemesNotInThemeGroup(IRequest requestContext, ThemeGroupTheme themeGroupTheme, int page, int pageSize);
    List<ThemeGroupTheme> selectThemesInThemeGroup(IRequest requestContext, ThemeGroupTheme themeGroupTheme, int page, int pageSize);

    List<ThemeGroupTheme> batchUpdate(IRequest requestContext, List<ThemeGroupTheme> filterList);
}
