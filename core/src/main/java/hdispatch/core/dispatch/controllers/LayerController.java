package hdispatch.core.dispatch.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hdispatch.core.dispatch.dto.layer.Layer;
import hdispatch.core.dispatch.dto.theme.Theme;
import hdispatch.core.dispatch.service.LayerService;
import hdispatch.core.dispatch.service.ThemeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by yyz on 2016/9/7.
 * yazheng.yang@hand-china.com
 */
@Controller
public class LayerController extends BaseController {
    private Logger logger = Logger.getLogger(LayerController.class);
    // TODO 暂时注释掉没有注入的功能
    @Autowired
    private LayerService layerService;
    @Autowired
    private ThemeService themeService;

    /**
     * 获取当前主题下处于active的所有layer
     */
    @RequestMapping(path = "/dispatcher/layer/query",method = RequestMethod.GET)
    public ResponseData getLayersByThemeId(HttpServletRequest request, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                           @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, @RequestParam(defaultValue = "-100") int themeId){
        IRequest requestContext = createRequestContext(request);
        //        查询theme是否存在并且处于active状态
        ResponseData rd = null;
        if(themeId < 0){
            rd = new ResponseData(false);
            rd.setMessage("themeId不合法");
            logger.info("尝试访问数据：themeId不合法");
            return rd;
        }
        //检查主题是否被删除
        Theme themeTemp = new Theme();
        themeTemp.setThemeId(themeId);
        Theme themeReturn = themeService.selectActiveThemeById(themeTemp);
        if(null == themeReturn){
            rd = new ResponseData(false);
            rd.setMessage("主题已删除");
            logger.info("尝试访问数据：theme已删除");
            return rd;
        }
        //读取主题下面没有被删除的层
        Layer layer = new Layer();
        layer.setThemeId(themeId);
        List<Layer> layerList = layerService.selectActiveLayersByThemeId(requestContext,page,pageSize,layer);

        rd = new ResponseData(layerList);
        return rd;

    }
    @RequestMapping(path = "/dispatcher/layer/submit",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData addLayers(@RequestBody List<Layer> layerList, BindingResult result, HttpServletRequest request){
        ResponseData rd = null;
        //后台验证
        getValidator().validate(layerList, result);
        if (result.hasErrors()) {
            rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        //从后台判断是否存在
        boolean[] isExist = layerService.checkIsExist(layerList);
        StringBuilder sb = new StringBuilder();
        boolean flag = false;
        for(int i = 0; i < layerList.size(); i++){
            if(isExist[i]){
                if(!flag){
                    sb.append(layerList.get(i).getLayerName());
                }
                else {
                    sb.append(","+layerList.get(i).getLayerName());
                }
                flag = true;
            }
        }
        if(flag){
            rd = new ResponseData(false);
            rd.setMessage("以下层已经存在:"+sb.toString());
            return rd;
        }
        IRequest requestContext = createRequestContext(request);

        try {
            rd = new ResponseData(layerService.batchUpdate(requestContext, layerList));
        } catch (Exception e) {
            logger.error("保存层中途失败",e);
        }
        return rd;
    }
}
