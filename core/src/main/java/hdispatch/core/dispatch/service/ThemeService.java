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
     * 根据主题模糊选择主题列表
     * @param requestContext
     * @param theme
     * @param page
     * @param pageSize
     * @return
     */
    List<Theme> selectByTheme(IRequest requestContext, Theme theme, int page, int pageSize);

    /**
     * 获取当前用户可读的主题列表
     * @param requestContext
     * @return
     */
    List<Theme> selectAll_read(IRequest requestContext);

    /**
     * 获取当前用户可以操作的主题列表
     * @param requestContext
     * @return
     */
    List<Theme> selectAll_opt(IRequest requestContext);


    /**
     * 批量编辑（目前只是新增和删除）
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
     * 获取传入的列表中没有挂载层次的主题（用于批量删除主题之前的检查）
     * @param requestContext
     * @param themeList
     * @return 没有挂载层次的主题列表
     */
    List<Theme> checkIsMountThemes(IRequest requestContext, List<Theme> themeList);
}
