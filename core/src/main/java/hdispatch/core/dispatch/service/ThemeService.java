package hdispatch.core.dispatch.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hdispatch.core.dispatch.dto.theme.Theme;

import java.util.List;

/**
 * Created by yyz on 2016/9/5.
 * yazheng.yang@hand-china.com
 */
public interface ThemeService extends ProxySelf<ThemeService> {
    List<Theme> selectByTheme(IRequest requestContext, Theme theme, int page, int pageSize);
    List<Theme> selectAll(IRequest requestContext, int page, int pageSize);
    boolean create(String themeName, String description, String projectName, String projectDescription);
    List<Theme> batchUpdate(IRequest requestContext,List<Theme> themeList) throws Exception;
    boolean[] checkIsExist(List<Theme> themeList);

    void deleteInLogic(Theme theme);
}
