package hdispatch.core.dispatch.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import hdispatch.core.dispatch.dto.layer.Layer;
import hdispatch.core.dispatch.dto.theme.Theme;
import hdispatch.core.dispatch.mapper.LayerMapper;
import hdispatch.core.dispatch.mapper.ThemeMapper;
import hdispatch.core.dispatch.service.ThemeService;
import hdispatch.core.dispatch.utils.ConfigUtil;
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
    @Autowired
    private LayerMapper layerMapper;

    /**
     * 根据主题模糊查询（当前用户可见的）主题列表
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
     * 获取当前用户可见的所有主题
     * @param requestContext
     * @return
     */
    @Override
    public List<Theme> selectAll_read(IRequest requestContext) {
        List<Theme> list;
        if(null == themeMapper){
            list = new ArrayList<>();
            logger.info("themeMapper没有注入");
        }
        else {
            list = themeMapper.selectAll_read();
        }
        return list;
    }

    /**
     * 获取当前用户可以操作的所有主题
     * @param requestContext
     * @return
     */
    @Override
    public List<Theme> selectAll_opt(IRequest requestContext) {
        List<Theme> list;
        if(null == themeMapper){
            list = new ArrayList<>();
            logger.info("themeMapper没有注入");
        }
        else {
            list = themeMapper.selectAll_opt();
        }
        return list;
    }

    /**
     * 批量编辑（目前只是新增和删除，删除没有检查主题下是否有层次）
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
                        deleteInLogic(theme);
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

    /**
     * 获取传入的列表中没有挂载层次的主题（用于删除之前的检查）
     * @param requestContext
     * @param themeList
     * @return
     */
    @Override
    public List<Theme> checkIsMountThemes(IRequest requestContext, List<Theme> themeList) {
        List<Theme> listFiltered = new ArrayList<>();
        for(Theme temp : themeList){
            Layer layer = new Layer();
            layer.setThemeId(temp.getThemeId());
            List<Layer> layersUnderTheme = layerMapper.selectActiveLayersUnderTheme(layer);
            if(0 < layersUnderTheme.size()){
                listFiltered.add(temp);
            }
        }
        return listFiltered;
    }

    /**
     * 判断当前用户是否有操作主题的权限<br>
     * @param requestContext
     * @return
     */
    @Override
    public boolean hasOperatePermission(IRequest requestContext) {
        String themeGroupName = ConfigUtil.getThemeLayer_themeGroupName();
        if(null == themeGroupName){
            logger.error("主题和层次（权限控制）：读取不到挂载主题和层次的主题组名称",
                    new RuntimeException("主题和层次（权限控制）：读取不到挂载主题和层次的主题组名称"));
        }
        Long rows = themeMapper.hasOperatePermission(themeGroupName);
        if(null == rows || rows < 1){
            return false;
        }
        return true;
    }

    /**
     * 批量更新主题<br>
     *     在更新之前检查是否会引起主题名称重复，将会引起重复的主题放置在duplicateThemesReturn返回
     * @param requestContext
     * @param themesToModify
     * @return List<Theme> duplicateThemesReturn——返回的重复主题列表
     */
    @Override
    public List<Theme> batchModify(IRequest requestContext, List<Theme> themesToModify) {
        List<Theme> duplicateThemesReturn = new ArrayList<>();
        for (Theme theme : themesToModify) {
            if (theme.get__status() != null) {
                switch (theme.get__status()) {
                    case DTOStatus.UPDATE:
                        Theme themeReturn = themeMapper.selectByNameAndActiveAndId(theme);
                        if(null != themeReturn){
                            duplicateThemesReturn.add(theme);
                            break;
                        }
                        themeMapper.updateById(theme);
                        break;
                    default:
                        break;
                }
            }
        }
        return duplicateThemesReturn;
    }
}
