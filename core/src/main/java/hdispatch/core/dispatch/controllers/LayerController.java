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
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by yyz on 2016/9/7.
 * yazheng.yang@hand-china.com
 */
@Controller
public class LayerController extends BaseController {
    private Logger logger = Logger.getLogger(LayerController.class);
    @Autowired
    private LayerService layerService;
    @Autowired
    private ThemeService themeService;

    /**
     * 获取当前主题下处于active的所有layer
     */
    @RequestMapping(path = "/dispatcher/layer/query",method = RequestMethod.GET)
    public ResponseData getLayersByThemeId(HttpServletRequest request,
                                           @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                           @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                                           @RequestParam(defaultValue = "-100") Long themeId){
        IRequest requestContext = createRequestContext(request);
        //        查询theme是否存在并且处于active状态
        ResponseData rd = null;
        //获取语言环境
        Locale locale = RequestContextUtils.getLocale(request);
        if(themeId < 0){
            rd = new ResponseData(false);
            //themeId不合法
            String errorMsg = getMessageSource().getMessage("hdispatch.layer.layer_create.illegal_themeId",null,locale);
            rd.setMessage(errorMsg);
            logger.info(errorMsg);
            return rd;
        }
        //检查主题是否被删除
        Theme themeTemp = new Theme();
        themeTemp.setThemeId(themeId);
        Theme themeReturn = themeService.selectActiveThemeById(themeTemp);
        if(null == themeReturn){
            rd = new ResponseData(false);
            //主题已删除
            String errorMsg = getMessageSource().getMessage("hdispatch.layer.layer_create.theme_not_exist",null,locale);
            rd.setMessage(errorMsg);
            logger.info(errorMsg);
            return rd;
        }
        //读取主题下面没有被删除的层
        Layer layer = new Layer();
        layer.setThemeId(themeId);
        List<Layer> layerList = layerService.selectActiveLayersByThemeId(requestContext,page,pageSize,layer);

        rd = new ResponseData(layerList);
        return rd;

    }

    @RequestMapping(path = "/dispatcher/layer/queryAll",method = RequestMethod.GET)
    public ResponseData getLayersByThemeIdWithoutPaging(HttpServletRequest request,
                                           @RequestParam(defaultValue = "-100") Long themeId){
        IRequest requestContext = createRequestContext(request);
        //        查询theme是否存在并且处于active状态
        ResponseData rd = null;
        List<Layer> layerList = new ArrayList<Layer>();
        //获取语言环境
        Locale locale = RequestContextUtils.getLocale(request);
        //如果没有设置themeId，那么查询所有的层
        if(themeId < 0){
            rd = new ResponseData(true);
            return rd;
        }

        //检查主题是否被删除
        Theme themeTemp = new Theme();
        themeTemp.setThemeId(themeId);
        Theme themeReturn = themeService.selectActiveThemeById(themeTemp);
        if(null == themeReturn){
            rd = new ResponseData(false);
            //主题已删除
            String errorMsg = getMessageSource().getMessage("hdispatch.layer.layer_create.theme_not_exist",null,locale);
            rd.setMessage(errorMsg);
            logger.info(errorMsg);
            return rd;
        }
        //读取主题下面没有被删除的层
        Layer layer = new Layer();
        layer.setThemeId(themeId);
        layerList = layerService.selectActiveLayersByThemeIdWithoutPaging(requestContext,layer);

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
        //获取语言环境
        Locale locale = RequestContextUtils.getLocale(request);
        if(flag){
            rd = new ResponseData(false);
            //以下层已经存在
            String errorMsg = getMessageSource().getMessage("hdispatch.layer.layer_create.layer_already_exist",null,locale);
            rd.setMessage(errorMsg+":"+sb.toString());
            return rd;
        }
        IRequest requestContext = createRequestContext(request);

        try {
            rd = new ResponseData(layerService.batchUpdate(requestContext, layerList));
        } catch (Exception e) {
            String errorMsg = getMessageSource().getMessage("hdispatch.layer.layer_create.error_during_create_layer",null,locale);
            logger.error(errorMsg,e);
        }
        return rd;
    }
}
