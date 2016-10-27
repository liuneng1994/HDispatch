package hdispatch.core.dispatch.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import hdispatch.core.dispatch.dto.authority.PermissionParameter;
import hdispatch.core.dispatch.dto.theme.Theme;

import java.util.List;

/**
 * Created by yyz on 2016/9/5.
 * yazheng.yang@hand-china.com
 */
public interface ThemeService extends ProxySelf<ThemeService> {
    List<Theme> selectByTheme(IRequest requestContext, Theme theme, int page, int pageSize, PermissionParameter permissionParameter);
    List<Theme> selectAll(IRequest requestContext, int page, int pageSize);
    List<Theme> selectAllWithoutPaging(IRequest requestContext);
    List<Theme> batchUpdate(IRequest requestContext,List<Theme> themeList) throws Exception;
    boolean[] checkIsExist(List<Theme> themeList);

    void deleteInLogic(Theme theme);
    Theme selectActiveThemeById(Theme theme);
}
