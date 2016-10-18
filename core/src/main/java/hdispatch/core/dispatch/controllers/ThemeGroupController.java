package hdispatch.core.dispatch.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hdispatch.core.dispatch.dto.authority.ThemeGroup;
import hdispatch.core.dispatch.service.ThemeGroupService;
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
 * Created by yyz on 2016/10/17.
 *
 * @author yazheng.yang@hand-china.com
 */
@Controller
public class ThemeGroupController  extends BaseController {
    private Logger logger = Logger.getLogger(ThemeGroupController.class);
    @Autowired
    private ThemeGroupService themeGroupService;

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
