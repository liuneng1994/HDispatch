package hdispatch.core.dispatch.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.IBaseService;
import hdispatch.core.dispatch.dto.layer.Layer;
import hdispatch.core.dispatch.dto.theme.Theme;
import hdispatch.core.dispatch.mapper_hdispatch.LayerMapper;
import hdispatch.core.dispatch.mapper_hdispatch.ThemeMapper;
import hdispatch.core.dispatch.service.ThemeService;
import hdispatch.core.dispatch.utils.ConfigUtil;
import org.apache.log4j.Logger;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 主题Service实现类
 * <br>
 * Created by yyz on 2016/9/5.
 * yazheng.yang@hand-china.com
 */
@Service
public class ThemeServiceImpl extends HdispatchBaseServiceImpl<Theme> implements ThemeService {
    @Autowired
    private ThemeMapper themeMapper;
    private Logger logger = Logger.getLogger(ThemeServiceImpl.class);
    @Autowired
    private LayerMapper layerMapper;

    @Override
    @Transactional(transactionManager = "hdispatchTM",propagation = Propagation.SUPPORTS)
    public List<Theme> selectByTheme(IRequest requestContext, Theme theme, int page, int pageSize) {
        theme.setThemeActive(1L);
        return super.select(requestContext, theme, page, pageSize);
    }

    /**
     * 获取当前用户可见的所有主题
     * @param requestContext
     * @return
     */
    @Override
    @Transactional(transactionManager = "hdispatchTM",propagation = Propagation.SUPPORTS)
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
    @Transactional(transactionManager = "hdispatchTM",propagation = Propagation.SUPPORTS)
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

    @Override
    @Transactional(transactionManager = "hdispatchTM",rollbackFor = Exception.class)
    public List<Theme> batchUpdate(IRequest requestContext, @StdWho List<Theme> themeList, Map<String, String> feedbackMsg) throws Exception {
        IBaseService<Theme> self = ((IBaseService<Theme>) AopContext.currentProxy());
        Theme themeReturn = null;
        for (Theme theme : themeList) {
            if (theme.get__status() != null) {
                switch (theme.get__status()) {
                    case DTOStatus.ADD:
                        themeReturn = themeMapper.selectByNameAndActiveAndId(theme);
                        if(null != themeReturn){
                            throw new Exception(feedbackMsg.get("ALREADY_EXIST")+":"+theme.getThemeName());
                        }
                        theme.setThemeActive(1L);
                        self.insert(requestContext,theme);
                        break;
                    case DTOStatus.UPDATE:
                        themeReturn = themeMapper.selectByNameAndActiveAndId(theme);
                        if(null != themeReturn){
                            throw new Exception(feedbackMsg.get("ALREADY_EXIST")+":"+theme.getThemeName());
                        }
                        if (useSelectiveUpdate()) {
                            self.updateByPrimaryKeySelective(requestContext, theme);
                        } else {
                            self.updateByPrimaryKey(requestContext, theme);
                        }
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
     * 逻辑删除主题
     * @param theme
     */
    @Override
    @Transactional(transactionManager = "hdispatchTM",rollbackFor = Exception.class)
    public void deleteInLogic(Theme theme) {
        if(null != theme && null != theme.getThemeId()){
            themeMapper.deleteInLogic(theme);
        }
    }

    /**
     * 获取传入的列表中没有挂载层次的主题（用于删除之前的检查）
     * @param requestContext
     * @param themeList
     * @return
     */
    @Override
    @Transactional(transactionManager = "hdispatchTM",propagation = Propagation.SUPPORTS)
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
    @Transactional(transactionManager = "hdispatchTM",propagation = Propagation.SUPPORTS)
    public boolean hasOperatePermission(IRequest requestContext) {
        String themeGroupName = ConfigUtil.getThemeLayer_themeGroupName();
        if(null == themeGroupName){
            logger.error("主题和层次（权限控制）：读取不到挂载主题和层次的主题组名称",
                    new Exception("主题和层次（权限控制）：读取不到挂载主题和层次的主题组名称"));
        }
        Long rows = themeMapper.hasOperatePermission(themeGroupName);
        if(null == rows || rows < 1){
            return false;
        }
        return true;
    }

    @Override
    public boolean isLayersUnderTheme(Theme theme) {
        Layer layer = new Layer();
        layer.setThemeId(theme.getThemeId());
        List<Layer> layersUnderTheme = layerMapper.selectActiveLayersUnderTheme(layer);
        return 0 < layersUnderTheme.size();
    }
}
