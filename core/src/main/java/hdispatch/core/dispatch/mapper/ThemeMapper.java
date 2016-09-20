package hdispatch.core.dispatch.mapper;


import hdispatch.core.dispatch.dto.theme.Theme;

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

}
