package hdispatch.core.dispatch.mapper;


import hdispatch.core.dispatch.dto.theme.Theme;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yyz on 2016/9/5.
 * @author yazheng.yang@hand-china.com
 */
public interface ThemeMapper {
    /**
     * 新建主题
     * @param theme
     * @throws Exception
     */
    void save(Theme theme) throws Exception;

    /**
     * 查询所有活跃主题
     * @return
     */
    List<Theme> selectAll();

    /**
     * 通过theme进行查询(多条件，支持模糊查询)
     * @param theme
     * @return
     */
    List<Theme> selectByTheme(Theme theme);

    /**
     * 通过主题名称查询活跃的主题
     * @param theme
     * @return
     */
    Theme selectByNameAndActive(Theme theme);

    /**
     * 逻辑删除
     * @param theme
     */
    void deleteInLogic(Theme theme);

    /**
     * 根据id查询主题
     * @param theme
     * @return
     */
    Theme selectById(Theme theme);

    //--------------------权限验证-----------------------
    /**
     * 权限验证下：查询所有主题
     * @param userId
     * @return
     */
    List<Theme> selectAll_validated(@Param("userId") Long userId);

    /**
     *  权限验证下：主题模糊搜索，根据权限，过滤掉不可见的主题,<br>
     *  通过theme进行查询(多条件，支持模糊查询)
     * @param themeId
     * @param userId
     * @param themeName
     * @param themeDescription
     * @return
     */
    List<Theme> selectByTheme_validated(@Param("themeId") Long themeId, @Param("userId") Long userId,@Param("themeName") String themeName,@Param("themeDescription") String themeDescription);

    /**
     * 权限验证下：主题搜索(根据themeName选择active的主题)，根据权限，过滤掉不可见的主题<br>
     *     通过主题名称查询活跃的主题
     * @param themeName
     * @param userId
     * @return
     */
    Theme selectByNameAndActive_validated(@Param("themeName")String themeName,@Param("userId")Long userId);

    /**
     * 权限验证下：删除主题<br>
     *     逻辑删除
     * @param themeId
     * @param userId
     */
    void deleteInLogic_validated(@Param("themeId")Long themeId,@Param("userId")Long userId);

    /**
     * 权限验证下：根据id查询主题
     * @param themeId
     * @param userId
     * @return
     */
    Theme selectById_validated(@Param("themeId")Long themeId,@Param("userId")Long userId);
}
