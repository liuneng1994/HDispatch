package hdispatch.core.dispatch.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.TokenException;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hdispatch.core.dispatch.dto.theme.Theme;
import hdispatch.core.dispatch.service.ThemeService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 主题控制器<br>
 */
@Controller
public class ThemeController extends BaseController {
    private Logger logger = Logger.getLogger(ThemeController.class);
    @Autowired
    private ThemeService themeService;

    /**
     * 带分页功能的主题搜索（支持themeName、themeDescription模糊搜索）
     * @param request
     * @param page 第几页
     * @param pageSize 分页条数
     * @param themeName 主题名称
     * @param themeDescription 主题的描述
     * @return
     */
    @RequestMapping(value = "/dispatcher/theme/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getThemes(HttpServletRequest request,
                                  @RequestParam(name = "page", defaultValue = DEFAULT_PAGE) int page,
                                  @RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                                  @RequestParam(name = "themeName", defaultValue = "") String themeName,
                                  @RequestParam(name = "themeDescription", defaultValue = "") String themeDescription) {
        IRequest requestContext = createRequestContext(request);
        Theme theme = new Theme();
        themeName = themeName.trim();
        themeDescription = themeDescription.trim();
        if ("".equals(themeName)) {
            themeName = null;
        }
        if ("".equals(themeDescription)) {
            themeDescription = null;
        }
        theme.setThemeName(themeName);
        theme.setThemeDescription(themeDescription);
        List<Theme> themeList = themeService.selectByTheme(requestContext, theme, page, pageSize);
        ResponseData responseData = new ResponseData(themeList);
        return responseData;
    }

    /**
     * 用于提供具有操作权限的主题列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/dispatcher/theme/queryAll_opt", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData getAllThemes_operate(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        List<Theme> themeList = themeService.selectAll_opt(requestContext);
        ResponseData responseData = new ResponseData(themeList);
        return responseData;
    }

    /**
     * 用于提供具有操作权限或者读权限的主题列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/dispatcher/theme/queryAll_read", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData getAllThemes_read(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);

        List<Theme> themeList = themeService.selectAll_read(requestContext);
        ResponseData responseData = new ResponseData(themeList);
        return responseData;
    }

    /**
     * 批量创建主题
     * @param themeList
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(value = "/dispatcher/theme/submit", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseData addThemes(@RequestBody List<Theme> themeList, BindingResult result, HttpServletRequest request) throws Exception {

        ResponseData rd = null;
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(themeService.batchUpdate(requestContext,themeList,initFeedbackMsg(request)));
    }

    /**
     * 批量删除主题（首先检查主题下是否不存在层次，删除不存在层次的主题）
     * @param themeList
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(value = "/dispatcher/theme/remove", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseData deleteThemes(@RequestBody List<Theme> themeList, BindingResult result, HttpServletRequest request) throws Exception {

        ResponseData rd = null;
        IRequest requestContext = createRequestContext(request);
        //获取语言环境
        Locale locale = RequestContextUtils.getLocale(request);
//        hdispatch.server.error_tips.already_exist  已经存在（多语言消息配置）
        //检查是否主题的下面有层次存在
        List<Theme> themeListExist = themeService.checkIsMountThemes(requestContext,themeList);
        if(0 < themeListExist.size()){
            String warningMsg = getMessageSource().getMessage("hdispatch.theme.delete.tips", null, locale);
            StringBuilder sb = new StringBuilder(warningMsg+":");
            boolean isFirstFlag = true;
            for(Theme temp : themeListExist){
                if(isFirstFlag){
                    sb.append(temp.getThemeName());
                    isFirstFlag = false;
                }
                else {
                    sb.append(","+temp.getThemeName());
                }
            }

            rd = new ResponseData(themeListExist);
            rd.setSuccess(false);
            rd.setMessage(sb.toString());

            return rd;
        }

        return new ResponseData(themeService.batchUpdate(requestContext,themeList,initFeedbackMsg(request)));
    }

    /**
     * 判断当前用户是否有操作主题的权限<br>
     * @param request
     * @return ResponseData中的total如果大于0表示有操作权限；否则，没有操作权限
     */
    @RequestMapping(value = "/dispatcher/theme/hasOperatePermission", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData hasOperatePermission(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        ResponseData rd = null;
        List<String> list = new ArrayList<>();
        boolean hasPermission = themeService.hasOperatePermission(requestContext);
        if(hasPermission) {
            list.add("YOU");
            list.add("HAVE");
            list.add("THIS");
            list.add("PAGE");
            list.add("OPERATION");
            list.add("PERMISSION");
        }
        rd = new ResponseData(list);
        return rd;
    }


    /**
     * 批量更新主题
     * @param themeList
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(value = "/dispatcher/theme/update", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseData updateThemes(@RequestBody List<Theme> themeList, BindingResult result, HttpServletRequest request) throws Exception {

        checkToken(request, themeList);
        getValidator().validate(themeList, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }

        ResponseData rd = null;
        //获取语言环境
        Locale locale = RequestContextUtils.getLocale(request);
        IRequest requestContext = createRequestContext(request);
        rd = new ResponseData(themeService.batchUpdate(requestContext,themeList,initFeedbackMsg(request)));
        return rd;
    }

    private Map<String,String> initFeedbackMsg(HttpServletRequest request){
        Map<String,String> feedbackMsg = new HashedMap();
        Locale locale = RequestContextUtils.getLocale(request);
        feedbackMsg.put("ALREADY_EXIST",getMessageSource().getMessage("hdispatch.server.error_tips.already_exist", null, locale));
        return feedbackMsg;
    }
}
