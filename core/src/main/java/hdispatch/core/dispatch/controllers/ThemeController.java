package hdispatch.core.dispatch.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hdispatch.core.dispatch.dto.theme.Theme;
import hdispatch.core.dispatch.service.ThemeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

/**
 * Created by yyz on 2016/9/6.
 * yazheng.yang@hand-china.com
 */
@Controller
public class ThemeController extends BaseController {
    private Logger logger = Logger.getLogger(ThemeController.class);
    @Autowired
    private ThemeService themeService;

    @RequestMapping(value = "/dispatcher/theme/query", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData getThemes(HttpServletRequest request,
                                  @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                                  @RequestParam(defaultValue = "") String themeName,
                                  @RequestParam(defaultValue = "") String themeDescription) {
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

    @RequestMapping(value = "/dispatcher/theme/queryAll", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData getAllThemes(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);

        List<Theme> themeList = themeService.selectAllWithoutPaging(requestContext);
        ResponseData responseData = new ResponseData(themeList);
        return responseData;
    }

    @RequestMapping(value = "/dispatcher/theme/submit", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseData addThemes(@RequestBody List<Theme> themeList, BindingResult result, HttpServletRequest request) {

        ResponseData rd = null;
        //后台验证
        getValidator().validate(themeList, result);
        if (result.hasErrors()) {
            rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        //从后台判断是否存在
        boolean[] isExist = themeService.checkIsExist(themeList);
        StringBuilder sb = new StringBuilder();
        boolean flag = false;
        for (int i = 0; i < themeList.size(); i++) {
            if (isExist[i]) {
                if (!flag) {
                    sb.append(themeList.get(i).getThemeName());
                } else {
                    sb.append("," + themeList.get(i).getThemeName());
                }
                flag = true;
            }
        }
        //获取语言环境
        Locale locale = RequestContextUtils.getLocale(request);
        if (flag) {
            rd = new ResponseData(false);
            //
            String errorMsg = getMessageSource().getMessage("hdispatch.theme.theme_create.theme_name_already_exist", null, locale);
            rd.setMessage(errorMsg+":" + sb.toString());
            return rd;
        }
        IRequest requestContext = createRequestContext(request);

        try {
            rd = new ResponseData(themeService.batchUpdate(requestContext, themeList));
        } catch (Exception e) {
            //保存主题中途失败
            String errorMsg = getMessageSource().getMessage("hdispatch.theme.theme_create.error_during_saving", null, locale);
            logger.error(errorMsg, e);
        }
        return rd;
    }
}
