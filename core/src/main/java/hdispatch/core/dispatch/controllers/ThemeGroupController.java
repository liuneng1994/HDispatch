package hdispatch.core.dispatch.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hdispatch.core.dispatch.dto.authority.ThemeGroup;
import hdispatch.core.dispatch.dto.authority.ThemeGroupTheme;
import hdispatch.core.dispatch.service.ThemeGroupService;
import hdispatch.core.dispatch.service.ThemeGroupThemeService;
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
 * Created by yyz on 2016/10/17.
 *
 * @author yazheng.yang@hand-china.com
 */
@Controller
public class ThemeGroupController  extends BaseController {
    private Logger logger = Logger.getLogger(ThemeGroupController.class);
    @Autowired
    private ThemeGroupService themeGroupService;
    @Autowired
    private ThemeGroupThemeService themeGroupThemeService;

    /**
     * 模糊查询主题组
     * @param request
     * @param page
     * @param pageSize
     * @param themeGroupName
     * @param themeGroupDesc
     * @return
     */
    @RequestMapping(value = "/dispatch/themeGroup/query", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData getThemeGroups(HttpServletRequest request,
                                  @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                                  @RequestParam(defaultValue = "") String themeGroupName,
                                  @RequestParam(defaultValue = "") String themeGroupDesc) {
        IRequest requestContext = createRequestContext(request);
        ThemeGroup themeGroup = new ThemeGroup();
        themeGroupName = themeGroupName.trim();
        themeGroupDesc = themeGroupDesc.trim();
        if ("".equals(themeGroupName)) {
            themeGroupName = null;
        }
        if ("".equals(themeGroupDesc)) {
            themeGroupDesc = null;
        }
        themeGroup.setThemeGroupName(themeGroupName);
        themeGroup.setThemeGroupDesc(themeGroupDesc);
        List<ThemeGroup> themeList = themeGroupService.selectByThemeGroup(requestContext, themeGroup, page, pageSize);
        ResponseData responseData = new ResponseData(themeList);
        return responseData;
    }

    /**
     * 批量添加主题组
     * @param themeList
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(value = "/dispatch/themeGroup/submit", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseData addThemeGroups(@RequestBody List<ThemeGroup> themeList, BindingResult result, HttpServletRequest request) {

        ResponseData rd = null;
        //后台验证
        getValidator().validate(themeList, result);
        if (result.hasErrors()) {
            rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        //获取语言环境
        Locale locale = RequestContextUtils.getLocale(request);
        rd = new ResponseData(themeGroupService.batchUpdate(requestContext, themeList));
//        try {
//            rd = new ResponseData(themeGroupService.batchUpdate(requestContext, themeList));
//        } catch (Exception e) {
//            //保存主题中途失败
//            String errorMsg = getMessageSource().getMessage("hdispatch.theme.theme_create.error_during_saving", null, locale);
//            logger.error(errorMsg, e);
//        }
        return rd;
    }

    /**
     * 批量更新主题组
     * @param themeGroupList
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(value = "/dispatch/themeGroup/update", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseData updateThemeGroups(@RequestBody List<ThemeGroup> themeGroupList, BindingResult result, HttpServletRequest request) {

        ResponseData rd = null;
        //后台验证
        getValidator().validate(themeGroupList, result);
        if (result.hasErrors()) {
            rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        rd = new ResponseData(themeGroupService.batchUpdate(requestContext,themeGroupList));
        return rd;
    }

    /**
     * 获取所有不在某个主题组的主题
     * @param request
     * @param page
     * @param pageSize
     * @param themeName
     * @param themeDescription
     * @param themeGroupId
     * @return
     */
    @RequestMapping(value = "/dispatch/themeGroup/themeGroupTheme/queryNotInThemeGroup", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData getThemesNotInThemeGroup(HttpServletRequest request,
                                       @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                       @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                                       @RequestParam(defaultValue = "") String themeName,
                                       @RequestParam(defaultValue = "") String themeDescription,
                                       @RequestParam(defaultValue = "-100") Long themeGroupId) {
        ResponseData responseData = null;
        if(null == themeGroupId || themeGroupId < 0){
            responseData = new ResponseData(false);
            return responseData;
        }

        IRequest requestContext = createRequestContext(request);
        ThemeGroupTheme themeGroupTheme = new ThemeGroupTheme();
        themeGroupTheme.setThemeGroupId(themeGroupId);
        themeName = themeName.trim();
        themeDescription = themeDescription.trim();
        if ("".equals(themeName)) {
            themeName = null;
        }
        if ("".equals(themeDescription)) {
            themeDescription = null;
        }
        themeGroupTheme.setThemeName(themeName);

        themeGroupTheme.setThemeDescription(themeDescription);
        List<ThemeGroupTheme> themeList = themeGroupThemeService.selectThemesNotInThemeGroup(requestContext, themeGroupTheme, page, pageSize);
        responseData = new ResponseData(themeList);
        return responseData;
    }

    /**
     * 获取主题组下的所有主题
     * @param request
     * @param page
     * @param pageSize
     * @param themeName
     * @param themeDescription
     * @param themeGroupId
     * @return
     */
    @RequestMapping(value = "/dispatch/themeGroup/themeGroupTheme/queryUnderThemeGroup", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData getThemesInThemeGroup(HttpServletRequest request,
                                                 @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                                 @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                                                 @RequestParam(defaultValue = "") String themeName,
                                                 @RequestParam(defaultValue = "") String themeDescription,
                                                 @RequestParam(defaultValue = "-100") Long themeGroupId) {
        ResponseData responseData = null;
        if(null == themeGroupId || themeGroupId < 0){
            responseData = new ResponseData(false);
            return responseData;
        }

        IRequest requestContext = createRequestContext(request);
        ThemeGroupTheme themeGroupTheme = new ThemeGroupTheme();
        themeGroupTheme.setThemeGroupId(themeGroupId);
        themeName = themeName.trim();
        themeDescription = themeDescription.trim();
        if ("".equals(themeName)) {
            themeName = null;
        }
        if ("".equals(themeDescription)) {
            themeDescription = null;
        }
        themeGroupTheme.setThemeName(themeName);

        themeGroupTheme.setThemeDescription(themeDescription);
        List<ThemeGroupTheme> themeList = themeGroupThemeService.selectThemesInThemeGroup(requestContext, themeGroupTheme, page, pageSize);
        responseData = new ResponseData(themeList);
        return responseData;
    }


    /**
     * 主题组挂载主题
     * @param themeGroupThemeList
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(value = "/dispatch/themeGroup/themeGroupTheme/submit", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseData mountThemes(@RequestBody List<ThemeGroupTheme> themeGroupThemeList, BindingResult result, HttpServletRequest request) {

        ResponseData rd = null;
        IRequest requestContext = createRequestContext(request);
        //获取语言环境
        Locale locale = RequestContextUtils.getLocale(request);

        List<ThemeGroupTheme> filterList = new ArrayList<>();
        for(ThemeGroupTheme temp : themeGroupThemeList){
            if(null != temp.getThemeGroupThemeId() || null == temp.getThemeId()){
                continue;
            }else {
                filterList.add(temp);
            }
        }

        rd = new ResponseData(themeGroupThemeService.batchUpdate(requestContext, filterList));
        return rd;
    }

}
