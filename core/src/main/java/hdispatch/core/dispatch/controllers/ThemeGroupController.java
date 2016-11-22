package hdispatch.core.dispatch.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hdispatch.core.dispatch.dto.authority.HdispatchAuthority;
import hdispatch.core.dispatch.dto.authority.ThemeGroup;
import hdispatch.core.dispatch.dto.authority.ThemeGroupTheme;
import hdispatch.core.dispatch.service.HdispatchAuthorityService;
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
 * 任务组控制器<br>
 * Created by yyz on 2016/10/17.
 * @author yazheng.yang@hand-china.com
 */
@Controller
public class ThemeGroupController  extends BaseController {
    private Logger logger = Logger.getLogger(ThemeGroupController.class);
    @Autowired
    private ThemeGroupService themeGroupService;
    @Autowired
    private ThemeGroupThemeService themeGroupThemeService;
    @Autowired
    private HdispatchAuthorityService hdispatchAuthorityService;

    /**
     * 模糊查询主题组
     * @param request
     * @param page
     * @param pageSize
     * @param themeGroupName
     * @param themeGroupDesc
     * @return
     */
    @RequestMapping(value = "/dispatch/themeGroup/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getThemeGroups(HttpServletRequest request,
                                  @RequestParam(name = "page", defaultValue = DEFAULT_PAGE) int page,
                                  @RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                                  @RequestParam(name = "themeGroupName", defaultValue = "") String themeGroupName,
                                  @RequestParam(name = "themeGroupDesc", defaultValue = "") String themeGroupDesc) {
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
     * 删除主题组
     * @param themeGroupList
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(value = "/dispatch/themeGroup/remove", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseData removeThemeGroups(@RequestBody List<ThemeGroup> themeGroupList, BindingResult result, HttpServletRequest request) {

        //判断是否有用户或者主题挂载在这个主题组下面，若有，不允许删除
        ResponseData rd = null;
        IRequest requestContext = createRequestContext(request);
        List<ThemeGroup> cannotRemove = new ArrayList<>();
        List<ThemeGroup> themeGroupsReturn = themeGroupService.batchDelete(requestContext, themeGroupList, cannotRemove);
        if(0 == cannotRemove.size()){
            rd = new ResponseData(themeGroupsReturn);
            return rd;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("无法移除，挂载的主题或用户需要先移除掉：");
        for(ThemeGroup group : cannotRemove){
            stringBuilder.append(group.getThemeGroupName()+",");
        }
        rd = new ResponseData(false);
        rd.setMessage(stringBuilder.toString());
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
    @RequestMapping(value = "/dispatch/themeGroup/themeGroupTheme/queryNotInThemeGroup", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getThemesNotInThemeGroup(HttpServletRequest request,
                                       @RequestParam(name = "page", defaultValue = DEFAULT_PAGE) int page,
                                       @RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                                       @RequestParam(name = "themeName", defaultValue = "") String themeName,
                                       @RequestParam(name = "themeDescription", defaultValue = "") String themeDescription,
                                       @RequestParam(name = "themeGroupId", defaultValue = "-100") Long themeGroupId) {
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
    @RequestMapping(value = "/dispatch/themeGroup/themeGroupTheme/queryUnderThemeGroup", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getThemesInThemeGroup(HttpServletRequest request,
                                                 @RequestParam(name = "page", defaultValue = DEFAULT_PAGE) int page,
                                                 @RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                                                 @RequestParam(name = "themeName", defaultValue = "") String themeName,
                                                 @RequestParam(name = "themeDescription", defaultValue = "") String themeDescription,
                                                 @RequestParam(name = "themeGroupId", defaultValue = "-100") Long themeGroupId) {
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
            if(null == temp.getThemeGroupId() || null == temp.getThemeId()){
                continue;
            }else {
                filterList.add(temp);
            }
        }

        rd = new ResponseData(themeGroupThemeService.batchUpdate(requestContext, filterList));
        return rd;
    }

    /**
     * 从主题组移除主题
     * @param themeGroupThemeList
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(value = "/dispatch/themeGroup/themeGroupTheme/remove", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseData removeThemes(@RequestBody List<ThemeGroupTheme> themeGroupThemeList, BindingResult result, HttpServletRequest request) {

        ResponseData rd = null;
        IRequest requestContext = createRequestContext(request);

        List<ThemeGroupTheme> filterList = new ArrayList<>();
        for(ThemeGroupTheme temp : themeGroupThemeList){
            if(null == temp.getThemeGroupThemeId()){
                continue;
            }else {
                filterList.add(temp);
            }
        }
        List<ThemeGroupTheme> themeGroupThemesReturn = themeGroupThemeService.batchUpdate(requestContext, filterList);
        rd = new ResponseData(themeGroupThemesReturn);
        return rd;
    }


    /**
     * 获取主题组下的所有已经分配权限的用户
     * @param request
     * @param page
     * @param pageSize
     * @param userName 用户名称
     * @param themeGroupId 主题组id
     * @return
     */
    @RequestMapping(value = "/dispatch/themeGroup/authorityUser/queryUnderThemeGroup", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getUsersInThemeGroup(HttpServletRequest request,
                                              @RequestParam(name = "page", defaultValue = DEFAULT_PAGE) int page,
                                              @RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                                              @RequestParam(name = "userName", defaultValue = "") String userName,
                                              @RequestParam(name = "themeGroupId", defaultValue = "-100") Long themeGroupId) {
        ResponseData responseData = null;
        if(null == themeGroupId || themeGroupId < 0){
            responseData = new ResponseData(false);
            return responseData;
        }

        IRequest requestContext = createRequestContext(request);
        HdispatchAuthority hdispatchAuthority = new HdispatchAuthority();
        hdispatchAuthority.setThemeGroupId(themeGroupId);
        userName = userName.trim();
        if ("".equals(userName)) {
            userName = null;
        }
        hdispatchAuthority.setUserName(userName);
        List<HdispatchAuthority> users = hdispatchAuthorityService.selectUsers(requestContext, hdispatchAuthority);
        List<HdispatchAuthority> authorityList = hdispatchAuthorityService.selectInThemeGroup(requestContext, hdispatchAuthority, users, page, pageSize);
        responseData = new ResponseData(authorityList);
        return responseData;
    }

    /**
     * 获取不在主题组下的用户
     * @param request
     * @param page
     * @param pageSize
     * @param userName 用户名称
     * @param themeGroupId 主题组id
     * @return
     */
    @RequestMapping(value = "/dispatch/themeGroup/authorityUser/queryNotInThemeGroup", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getUsersNotInThemeGroup(HttpServletRequest request,
                                             @RequestParam(name = "page", defaultValue = DEFAULT_PAGE) int page,
                                             @RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                                             @RequestParam(name = "userName", defaultValue = "") String userName,
                                             @RequestParam(name = "themeGroupId", defaultValue = "-100") Long themeGroupId) {
        ResponseData responseData = null;
        if(null == themeGroupId || themeGroupId < 0){
            responseData = new ResponseData(false);
            return responseData;
        }

        IRequest requestContext = createRequestContext(request);
        HdispatchAuthority hdispatchAuthority = new HdispatchAuthority();
        hdispatchAuthority.setThemeGroupId(themeGroupId);
        userName = userName.trim();
        if ("".equals(userName)) {
            userName = null;
        }
        hdispatchAuthority.setUserName(userName);
        List<HdispatchAuthority> users = hdispatchAuthorityService.selectUsersInThemeGroup(requestContext, hdispatchAuthority);
        List<HdispatchAuthority> authorityList = hdispatchAuthorityService.selectNotInThemeGroup(requestContext, hdispatchAuthority, users, page, pageSize);
        responseData = new ResponseData(authorityList);
        return responseData;
    }

    /**
     * 主题组下批量添加、更新、删除用户
     * @param authorityList
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(value = "/dispatch/themeGroup/authorityUser/submit_update_delete", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseData operateUsers(@RequestBody List<HdispatchAuthority> authorityList, BindingResult result, HttpServletRequest request) {

        ResponseData rd = null;
        IRequest requestContext = createRequestContext(request);
        //获取语言环境
        Locale locale = RequestContextUtils.getLocale(request);

        List<HdispatchAuthority> filterList = new ArrayList<>();
        for(HdispatchAuthority temp : authorityList){
            if(null == temp.getThemeGroupId() || null == temp.getUserId()){
                continue;
            }else {
                filterList.add(temp);
            }
        }

        rd = new ResponseData(hdispatchAuthorityService.batchUpdate(requestContext, filterList));
        return rd;
    }

}
