package hdispatch.core.dispatch.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hdispatch.core.dispatch.dto.theme.Theme;
import hdispatch.core.dispatch.azkaban.service.ProjectService;
import hdispatch.core.dispatch.azkaban.service.impl.ProjectServiceImpl;
import hdispatch.core.dispatch.mapper.ThemeMapper;
import hdispatch.core.dispatch.service.ThemeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyz on 2016/9/5.
 * yazheng.yang@hand-china.com
 */
@Service
public class ThemeServiceImpl implements ThemeService {
    @Autowired
    private ThemeMapper themeMapper;
    private Logger logger = Logger.getLogger(ThemeServiceImpl.class);

    @Override
    public List<Theme> selectByTheme(IRequest requestContext, Theme theme, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Theme> list;
        if(null == themeMapper){
            list = new ArrayList<>();
            logger.info("themeMapper没有注入");
        }
        else {
            list = themeMapper.selectByTheme(theme);
        }
        return list;
    }

    @Override
    public List<Theme> selectAll(IRequest requestContext, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Theme> list;
        if(null == themeMapper){
            list = new ArrayList<>();
            logger.info("themeMapper没有注入");
        }
        else {
            list = themeMapper.selectAll();
        }
        return list;
    }

    @Override
    public boolean create(String themeName, String description, String projectName, String projectDescription) {

        //插入projects表判断是否成功
        ProjectService projectService = new ProjectServiceImpl();
        boolean isSuccess = projectService.createProject(projectName, description);
        if (!isSuccess) {
            logger.info("插入theme过程中，project信息录入失败");
            return false;
        }
        //插入theme基本表
        try {
            this.insertTheme(themeName, description, projectName);
        } catch (Exception e) {
            isSuccess = false;
            logger.error("插入theme失败", e);
            return false;
        }
        return isSuccess;
    }

    @Override
    public List<Theme> batchUpdate(IRequest requestContext, List<Theme> themeList) throws Exception {
        for (Theme theme : themeList) {
            if (theme.get__status() != null) {
                switch (theme.get__status()) {
                    case DTOStatus.ADD:
                        themeMapper.save(theme);
                        break;
                    case DTOStatus.UPDATE:

                        break;
                    case DTOStatus.DELETE:

                        break;
                    default:
                        break;
                }
            }
        }
        return themeList;
    }

    /**
     * 检查数据库中是否存在处于生效(active)状态，主题名称相同
     */
    @Override
    public boolean[] checkIsExist(List<Theme> themeList) {
        boolean[] isExist = new boolean[themeList.size()];
        int i = 0;
        for(Theme theme : themeList){
            Theme themeReturn = themeMapper.selectByNameAndActive(theme);
            if(null != themeReturn){
                isExist[i] = true;
            }
            i ++;
        }

        return isExist;
    }

    @Override
    public void deleteInLogic(Theme theme) {
        if(null != theme){
            themeMapper.deleteInLogic(theme);
        }
    }

    private boolean insertTheme(String themeName, String description, String projectName) throws Exception {
        boolean isSuccess = true;
        Theme theme = new Theme();
        theme.setDescription(description);
        theme.setThemeName(themeName);
        theme.setProjectName(projectName);
        theme.setProjectVersion(1);
        themeMapper.save(theme);
        return isSuccess;
    }

}
