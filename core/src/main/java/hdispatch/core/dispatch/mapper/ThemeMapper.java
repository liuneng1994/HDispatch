package hdispatch.core.dispatch.mapper;


import hdispatch.core.dispatch.dto.theme.Theme;

import java.util.List;

/**
 * Created by yyz on 2016/9/5.
 * yazheng.yang@hand-china.com
 */
public interface ThemeMapper {
    void save(Theme theme) throws Exception;
    List<Theme> selectAll();
    List<Theme> selectByTheme(Theme theme);
    Theme selectByNameAndActive(Theme theme);
    void deleteInLogic(Theme theme);

    Theme selectById(Theme theme);

}
