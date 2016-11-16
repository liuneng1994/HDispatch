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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理主题组和主题映射关系的service接口实现类<br>
 * Created by yyz on 2016/10/18.
 * @author yazheng.yang@hand-china.com
 */
@Service
public class ThemeGroupThemeServiceImpl implements ThemeGroupThemeService {
    private Logger logger = Logger.getLogger(ThemeGroupThemeServiceImpl.class);
    @Autowired
    private ThemeGroupThemeMapper themeGroupThemeMapper;

    /**
     * 根据 ThemeGroupTheme 模糊查询不在主题组下的主题
     * @param requestContext
     * @param themeGroupTheme
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    @Transactional
    public List<ThemeGroupTheme> selectThemesNotInThemeGroup(IRequest requestContext, ThemeGroupTheme themeGroupTheme, int page, int pageSize) {
        Assert.notNull(themeGroupTheme);
        PageHelper.startPage(page, pageSize);
        Map<String,Object> map = new HashMap<>();
        map.put("themeGroupId",themeGroupTheme.getThemeGroupId());
        map.put("themeName",themeGroupTheme.getThemeName());
        map.put("themeDescription",themeGroupTheme.getThemeDescription());
        return themeGroupThemeMapper.selectNotInThemeGroup(map);
    }

    /**
     * 根据 ThemeGroupTheme 模糊查询主题组下的主题
     * @param requestContext
     * @param themeGroupTheme
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    @Transactional
    public List<ThemeGroupTheme> selectThemesInThemeGroup(IRequest requestContext, ThemeGroupTheme themeGroupTheme, int page, int pageSize) {
        Assert.notNull(themeGroupTheme);
        PageHelper.startPage(page, pageSize);
        Map<String,Object> map = new HashMap<>();
        map.put("themeGroupId",themeGroupTheme.getThemeGroupId());
        map.put("themeName",themeGroupTheme.getThemeName());
        map.put("themeDescription",themeGroupTheme.getThemeDescription());
        return themeGroupThemeMapper.selectInThemeGroup(map);
    }

    /**
     * 批量编辑（增加和删除，没有必要修改）
     * @param requestContext
     * @param filterList
     * @return
     */
    @Override
    @Transactional
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
