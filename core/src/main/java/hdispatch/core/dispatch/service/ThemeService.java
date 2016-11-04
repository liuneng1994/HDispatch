package hdispatch.core.dispatch.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import hdispatch.core.dispatch.dto.theme.Theme;
import hdispatch.core.dispatch.mapper.ThemeMapper;

import java.util.List;

/**
 * 主题service接口<br>
 * Created by yyz on 2016/9/5.
 * yazheng.yang@hand-china.com
 */
public interface ThemeService extends ProxySelf<ThemeService> {
    /**
     * 根据主题模糊查询（当前用户可见的）主题列表
     * @param requestContext
     * @param theme
     * @param page
     * @param pageSize
     * @return
     */
    List<Theme> selectByTheme(IRequest requestContext, Theme theme, int page, int pageSize);

    /**
     * 获取当前用户可见的所有主题
     * @param requestContext
     * @return
     */
    List<Theme> selectAll_read(IRequest requestContext);

    /**
     * 获取当前用户可以操作的所有主题
     * @param requestContext
     * @return
     */
    List<Theme> selectAll_opt(IRequest requestContext);


    /**
     * 批量编辑（目前只是新增和删除，删除没有检查主题下是否有层次）
     * @param requestContext
     * @param themeList
     * @return
     * @throws Exception
     */
    List<Theme> batchUpdate(IRequest requestContext,List<Theme> themeList) throws Exception;

    /**
     * 检查数据库中是否存在处于生效(active)状态，主题名称相同，
     * 用于新增主题之前检测是否已经有同名的主题存在
     * @param themeList
     * @return
     */
    boolean[] checkIsExist(List<Theme> themeList);

    /**
     * 逻辑删除主题
     * @param theme
     */
    void deleteInLogic(Theme theme);

    /**
     * 根据id查找active(没被删除)的主题
     * @param theme
     * @return
     */
    Theme selectActiveThemeById(Theme theme);

    /**
     * 获取传入的列表中没有挂载层次的主题（用于删除之前的检查）
     * @param requestContext
     * @param themeList
     * @return
     */
    List<Theme> checkIsMountThemes(IRequest requestContext, List<Theme> themeList);

    /**
     * 判断当前用户是否有操作主题的权限<br>
     * @param requestContext
     * @return
     */
    boolean hasOperatePermission(IRequest requestContext);
}
