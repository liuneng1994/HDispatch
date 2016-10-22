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
 * Created by yyz on 2016/10/18.
 *
 * @author yazheng.yang@hand-china.com
 */
@Service
public class ThemeGroupThemeServiceImpl implements ThemeGroupThemeService {
    private Logger logger = Logger.getLogger(ThemeGroupThemeServiceImpl.class);
    @Autowired
    private ThemeGroupThemeMapper themeGroupThemeMapper;
    @Override
    public List<ThemeGroupTheme> selectThemesNotInThemeGroup(IRequest requestContext, ThemeGroupTheme themeGroupTheme, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        Map<String,Object> map = new HashMap<>();
        map.put("themeGroupId",themeGroupTheme.getThemeGroupId());
        map.put("themeName",themeGroupTheme.getThemeName());
        map.put("themeDescription",themeGroupTheme.getThemeDescription());
        return themeGroupThemeMapper.selectNotInThemeGroup(map);
    }

    @Override
    public List<ThemeGroupTheme> selectThemesInThemeGroup(IRequest requestContext, ThemeGroupTheme themeGroupTheme, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        Map<String,Object> map = new HashMap<>();
        map.put("themeGroupId",themeGroupTheme.getThemeGroupId());
        map.put("themeName",themeGroupTheme.getThemeName());
        map.put("themeDescription",themeGroupTheme.getThemeDescription());
        return themeGroupThemeMapper.selectInThemeGroup(map);
    }

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
