package hdispatch.core.dispatch.azkaban.service.impl;

import hdispatch.core.dispatch.azkaban.entity.theme.Theme;
import hdispatch.core.dispatch.azkaban.mapper.ThemeMapper;
import hdispatch.core.dispatch.azkaban.service.ProjectService;
import hdispatch.core.dispatch.azkaban.service.ThemeService;
import org.apache.log4j.Logger;

/**
 * Created by yyz on 2016/9/5.
 * yazheng.yang@hand-china.com
 */
public class ThemeServiceImpl implements ThemeService {
    private ThemeMapper themeMapper;
    private Logger logger = Logger.getLogger(ThemeServiceImpl.class);

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
