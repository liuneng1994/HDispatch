package hdispatch.core.dispatch.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import hdispatch.core.dispatch.dto.theme.Theme;
import hdispatch.core.dispatch.mapper.ThemeMapper;
import hdispatch.core.dispatch.service.ThemeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 主题Service实现类
 * <br>
 * Created by yyz on 2016/9/5.
 * yazheng.yang@hand-china.com
 */
@Service
public class ThemeServiceImpl implements ThemeService {
    @Autowired
    private ThemeMapper themeMapper;
    private Logger logger = Logger.getLogger(ThemeServiceImpl.class);

    /**
     * 根据主题模糊选择主题列表
     * @param requestContext
     * @param theme
     * @param page
     * @param pageSize
     * @return
     */
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

    /**
     * 获取所有主题（带分页）
     * @param requestContext
     * @param page
     * @param pageSize
     * @return
     */
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

    /**
     * 查询所有主题（无分页）
     * @param requestContext
     * @return
     */
    @Override
    public List<Theme> selectAllWithoutPaging(IRequest requestContext) {
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

    /**
     * 批量编辑（目前只是新增）
     * @param requestContext
     * @param themeList
     * @return
     * @throws Exception
     */
    @Override
    public List<Theme> batchUpdate(IRequest requestContext, List<Theme> themeList) throws Exception {
        for (Theme theme : themeList) {
            if (theme.get__status() != null) {
                switch (theme.get__status()) {
                    case DTOStatus.ADD:
                        themeMapper.save(theme);
                        theme.setThemeActive(1L);
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
     * 检查数据库中是否存在处于生效(active)状态，主题名称相同，
     * 用于新增主题之前检测是否已经有同名的主题存在
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

    /**
     * 逻辑删除主题
     * @param theme
     */
    @Override
    public void deleteInLogic(Theme theme) {
        if(null != theme && null != theme.getThemeId()){
            themeMapper.deleteInLogic(theme);
        }
    }

    /**
     * 根据id查找active(没被删除)的主题
     * @param theme
     * @return
     */
    @Override
    public Theme selectActiveThemeById(Theme theme) {
        Theme themeReturn = themeMapper.selectById(theme);
        return themeReturn;
    }

//    private boolean insertTheme(String themeName, String description, String projectName) throws Exception {
//        boolean isSuccess = true;
//        Theme theme = new Theme();
//        theme.setThemeDescription(description);
//        theme.setThemeName(themeName);
//        theme.setProjectName(projectName);
//        theme.setProjectVersion(1);
//        themeMapper.save(theme);
//        return isSuccess;
//    }


    //-------------------------以下是经过权限验证的方法：方法名称带_validated-------------------------

    /**
     * 模糊查询主题列表（经过权限验证）
     * 需要读权限
     * @param requestContext
     * @param theme
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List<Theme> selectByTheme_validated(IRequest requestContext, Theme theme, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Theme> list;
        if(null == themeMapper){
            list = new ArrayList<>();
            logger.info("themeMapper没有注入");
        }
        else {
            //权限验证下的查询
            list = themeMapper.selectByTheme_validated(theme.getThemeId(),requestContext.getUserId(),theme.getThemeName(),theme.getThemeDescription());
        }
        return list;
    }

    /**
     * 获取所有主题（带分页并且经过权限验证）
     * @param requestContext
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List<Theme> selectAll_validated(IRequest requestContext, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Theme> list;
        if(null == themeMapper){
            list = new ArrayList<>();
            logger.info("themeMapper没有注入");
        }
        else {
            list = themeMapper.selectAll_validated(requestContext.getUserId());
        }
        return list;
    }

    /**
     * 获取所有主题（无分页、经过权限验证）
     * @param requestContext
     * @return
     */
    @Override
    public List<Theme> selectAllWithoutPaging_validated(IRequest requestContext) {
        List<Theme> list;
        if(null == themeMapper){
            list = new ArrayList<>();
            logger.info("themeMapper没有注入");
        }
        else {
            list = themeMapper.selectAll_validated(requestContext.getUserId());
        }
        return list;
    }

    /**
     * 批量编辑主题(经过权限验证,新增没有验证权限，删除需验证权限)
     * @param requestContext
     * @param themeList
     * @return
     * @throws Exception
     */
    @Override
    public List<Theme> batchUpdate_validated(IRequest requestContext, List<Theme> themeList) throws Exception {
        for (Theme theme : themeList) {
            if (theme.get__status() != null) {
                switch (theme.get__status()) {
                    case DTOStatus.ADD:
                        themeMapper.save(theme);
                        theme.setThemeActive(1L);
                        break;
                    case DTOStatus.UPDATE:

                        break;
                    case DTOStatus.DELETE:
                        themeMapper.deleteInLogic_validated(theme.getThemeId(),requestContext.getUserId());
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
     * checkIsExist方法是用于检测是否在数据库中已经存在，防止重名。
     * 因此在本方法中调用的是不经过权限验证的selectByNameAndActive方法
     * @see ThemeMapper#selectByNameAndActive(Theme) selectByNameAndActive
     * @param themeList 需要查重的主题列表
     * @return
     */
    @Override
    public boolean[] checkIsExist_validated(List<Theme> themeList) {
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

    /**
     * 逻辑删除主题(经过权限验证)
     * @param requestContext
     * @param theme
     */
    @Override
    public void deleteInLogic_validated(IRequest requestContext,Theme theme) {
        if(null != theme && null != theme.getThemeId()){
            themeMapper.deleteInLogic_validated(theme.getThemeId(),requestContext.getUserId());
        }
    }

    /**
     * 根据themeId获取主题(经过权限验证)
     * @param requestContext
     * @param theme
     * @return
     */
    @Override
    public Theme selectActiveThemeById_validated(IRequest requestContext,Theme theme) {
        Theme themeReturn = themeMapper.selectById_validated(theme.getThemeId(),requestContext.getUserId());
        return themeReturn;
    }
}
