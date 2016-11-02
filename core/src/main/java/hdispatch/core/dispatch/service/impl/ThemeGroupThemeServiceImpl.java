package hdispatch.core.dispatch.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import hdispatch.core.dispatch.dto.authority.ThemeGroupTheme;
import hdispatch.core.dispatch.mapper.ThemeGroupThemeMapper;
import hdispatch.core.dispatch.service.ThemeGroupThemeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理主题组和主题之间的挂载关系的service接口实现类<br>
 * Created by yyz on 2016/10/18.
 *
 * @author yazheng.yang@hand-china.com
 */
@Service
public class ThemeGroupThemeServiceImpl implements ThemeGroupThemeService {
    private Logger logger = Logger.getLogger(ThemeGroupThemeServiceImpl.class);
    @Autowired
    private ThemeGroupThemeMapper themeGroupThemeMapper;

    /**
     * 获取不在主题组下的主题列表
     * @param requestContext
     * @param themeGroupTheme
     * @param page
     * @param pageSize
     * @return 不在主题组下的主题列表
     */
    @Override
    public List<ThemeGroupTheme> selectThemesNotInThemeGroup(IRequest requestContext, ThemeGroupTheme themeGroupTheme, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        Map<String,Object> map = new HashMap<>();
        map.put("themeGroupId",themeGroupTheme.getThemeGroupId());
        map.put("themeName",themeGroupTheme.getThemeName());
        map.put("themeDescription",themeGroupTheme.getThemeDescription());
        return themeGroupThemeMapper.selectNotInThemeGroup(map);
    }

    /**
     * 获取在主题组下的主题列表
     * @param requestContext
     * @param themeGroupTheme
     * @param page
     * @param pageSize
     * @return 在主题组下的主题列表
     */
    @Override
    public List<ThemeGroupTheme> selectThemesInThemeGroup(IRequest requestContext, ThemeGroupTheme themeGroupTheme, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        Map<String,Object> map = new HashMap<>();
        map.put("themeGroupId",themeGroupTheme.getThemeGroupId());
        map.put("themeName",themeGroupTheme.getThemeName());
        map.put("themeDescription",themeGroupTheme.getThemeDescription());
        return themeGroupThemeMapper.selectInThemeGroup(map);
    }

    /**
     * 批量编辑（增加和删除，不需要修改）
     * @param requestContext
     * @param filterList
     * @return
     */
    @Override
    public List<ThemeGroupTheme> batchUpdate(IRequest requestContext, List<ThemeGroupTheme> filterList) {
        for (ThemeGroupTheme themeGroupTheme : filterList) {
            if (themeGroupTheme.get__status() != null) {
                switch (themeGroupTheme.get__status()) {
                    case DTOStatus.ADD:
                        themeGroupThemeMapper.save(themeGroupTheme);
                        break;
                    case DTOStatus.UPDATE:

                        break;
                    case DTOStatus.DELETE:
                        themeGroupThemeMapper.deleteTheme(themeGroupTheme);
                        break;
                    default:
                        break;
                }
            }
        }
        return filterList;
    }
}
