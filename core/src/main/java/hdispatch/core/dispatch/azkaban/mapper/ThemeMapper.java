package hdispatch.core.dispatch.azkaban.mapper;


import hdispatch.core.dispatch.azkaban.entity.theme.Theme;

/**
 * Created by yyz on 2016/9/5.
 * yazheng.yang@hand-china.com
 */
public interface ThemeMapper {
    void save(Theme theme) throws Exception;
}
