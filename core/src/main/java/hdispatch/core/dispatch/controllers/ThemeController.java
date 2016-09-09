package hdispatch.core.dispatch.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hdispatch.core.dispatch.dto.theme.Theme;
import hdispatch.core.dispatch.service.ThemeService;
import hdispatch.core.dispatch.service.impl.ThemeServiceImpl;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.context.ThemeSource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyz on 2016/9/6.
 * yazheng.yang@hand-china.com
 */
@Controller
public class ThemeController extends BaseController {
    private Logger logger = Logger.getLogger(ThemeController.class);
    @Autowired
    private ThemeService themeService;

    @RequestMapping(path = "/dispatcher/theme/query", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData getThemes(HttpServletRequest request, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, @RequestParam String themeName, @RequestParam String description) {
        IRequest requestContext = createRequestContext(request);
        Theme theme = new Theme();
        if(null != themeName){
            themeName = themeName.trim();
        }
        if(null != description){
            description = description.trim();
        }
        if("".equals(themeName) || (null != themeName && themeName.trim().equals(""))){
            themeName = null;
        }
        if("".equals(description) || (null != description && description.trim().equals(""))){
            description = null;
        }
        theme.setThemeName(themeName);
        theme.setDescription(description);
        List<Theme> themeList = themeService.selectByTheme(requestContext, theme, page, pageSize);
        ResponseData responseData = new ResponseData(themeList);
        return responseData;
    }

    @RequestMapping(path = "/dispatcher/theme/submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData addTheme(@RequestBody List<Theme> themeList,BindingResult result, HttpServletRequest request) {
//        String themes_data_array = request.getParameter("themes_data_array");
//        System.out.println(themes_data_array);
//
//        JSONArray jsonArray = new JSONArray(themes_data_array);
//        int len = jsonArray.length();
//        List<Theme> themeList = new ArrayList<Theme>();
//
//        boolean[] isSuccessArray = new boolean[len];
//        for (int i = 0; i < jsonArray.length(); i++) {
//            Object o = jsonArray.get(i);
//            System.out.println(o);
//            JSONObject jsonObject = new JSONObject(o.toString());
//            String themeName = jsonObject.getString("themeName");
//            String description = jsonObject.getString("description");
//            String projectName = jsonObject.getString("projectName");
//            String projectDescription = jsonObject.getString("projectDescription");
//
//            Theme themeTemp = new Theme();
//            themeTemp.setThemeName(themeName);
//            themeTemp.setDescription(description);
//            themeTemp.setProjectName(projectName);
//            themeTemp.setProjectDescription(projectDescription);
//
//            themeList.add(themeTemp);
////            //TODO 到时候这个对象直接spring注入
////            ThemeService themeService = new ThemeServiceImpl();
////            boolean isSuccess = true;
////            //TODO 这里底层调用的Mybatis mapper还没有被注入，暂时注释掉
//////            isSuccess = themeService.create(themeName, description, projectName, projectDescription);
////            isSuccessArray[i] = isSuccess;
//        }
////        getAllTheme(request, response);
//
//        //后台验证
//        getValidator().validate(themeList, result);
//        if (result.hasErrors()) {
//            ResponseData rd = new ResponseData(false);
//            rd.setMessage(getErrorMessage(result, request));
//            return rd;
//        }
//        IRequest requestContext = createRequestContext(request);
//        try {
//            List<Theme> themeListReturn = themeService.batchUpdate(requestContext, themeList);
//            return new ResponseData(themeListReturn);
//        } catch (Exception e) {
//            logger.error("添加主题失败", e);
//            ResponseData rd = new ResponseData(false);
//            rd.setMessage(getErrorMessage(result, request));
//            return rd;
//        }

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
        for(int i = 0; i < themeList.size(); i++){
            if(isExist[i]){
                if(!flag){
                    sb.append(themeList.get(i).getThemeName());
                }
                else {
                    sb.append(","+themeList.get(i).getThemeName());
                }
                flag = true;
            }
        }
        if(flag){
            rd = new ResponseData(false);
            rd.setMessage("以下主题已经存在:"+sb.toString());
            return rd;
        }
        IRequest requestContext = createRequestContext(request);

        try {
            rd = new ResponseData(themeService.batchUpdate(requestContext, themeList));
        } catch (Exception e) {
            logger.error("保存主题中途失败",e);
        }
        return rd;
    }
}
