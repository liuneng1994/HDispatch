package hdispatch.core.dispatch.controllers;

import hdispatch.core.dispatch.service.ThemeService;
import hdispatch.core.dispatch.service.impl.ThemeServiceImpl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.context.ThemeSource;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by yyz on 2016/9/6.
 * yazheng.yang@hand-china.com
 */
@Controller
public class ThemeController {
//    @Autowired
//    private ThemeService themeService;

    @RequestMapping("/dispatcher/theme/query")
    public void getAllTheme(HttpServletRequest request, HttpServletResponse response){
        //TODO 这里模拟的是将相应数据以json的格式发送,后续需要完善
        String msg = "[\n" +
                "  {\"themeId\":\"themeId1\",\"themeName\":\"name1\",\"description\":\"description1\",\"projectName\":\"projectName1\",\"projectDescription\":\"projectDescription1\"},\n" +
                "  {\"themeId\":\"themeId2\",\"themeName\":\"name2\",\"description\":\"description2\",\"projectName\":\"projectName2\",\"projectDescription\":\"projectDescription2\"},\n" +
                "  {\"themeId\":\"themeId3\",\"themeName\":\"name3\",\"description\":\"description3\",\"projectName\":\"projectName3\",\"projectDescription\":\"projectDescription3\"},\n" +
                "  {\"themeId\":\"themeId4\",\"themeName\":\"name4\",\"description\":\"description4\",\"projectName\":\"projectName4\",\"projectDescription\":\"projectDescription4\"},\n" +
                "  {\"themeId\":\"themeId5\",\"themeName\":\"name5\",\"description\":\"description5\",\"projectName\":\"projectName5\",\"projectDescription\":\"projectDescription5\"},\n" +
                "  {\"themeId\":\"themeId6\",\"themeName\":\"name6\",\"description\":\"description6\",\"projectName\":\"projectName6\",\"projectDescription\":\"projectDescription6\"},\n" +
                "  {\"themeId\":\"themeId7\",\"themeName\":\"name7\",\"description\":\"description7\",\"projectName\":\"projectName7\",\"projectDescription\":\"projectDescription7\"},\n" +
                "  {\"themeId\":\"themeId8\",\"themeName\":\"name8\",\"description\":\"description8\",\"projectName\":\"projectName8\",\"projectDescription\":\"projectDescription8\"}\n" +
                "]";
//        String themeName = request.getParameter("themeName");
//        System.out.println(themeName);
        try {
            PrintWriter writer = response.getWriter();
            writer.write(msg);

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @RequestMapping("/dispatcher/theme/submit")
    public void addTheme(HttpServletRequest request, HttpServletResponse response){
        String themes_data_array = request.getParameter("themes_data_array");
        System.out.println(themes_data_array);

        JSONArray jsonArray = new JSONArray(themes_data_array);
        int len = jsonArray.length();
        boolean[] isSuccessArray = new boolean[len];
        for(int i=0; i < jsonArray.length(); i++){
            Object o = jsonArray.get(i);
            System.out.println(o);
            JSONObject jsonObject = new JSONObject(o.toString());
            String themeName = jsonObject.getString("themeName");
            String description = jsonObject.getString("description");
            String projectName = jsonObject.getString("projectName");
            String projectDescription = jsonObject.getString("projectDescription");

            //TODO 到时候这个对象直接spring注入
            ThemeService themeService = new ThemeServiceImpl();
            boolean isSuccess = true;
            //TODO 这里底层调用的Mybatis mapper还没有被注入，暂时注释掉
//            isSuccess = themeService.create(themeName, description, projectName, projectDescription);

            isSuccessArray[i] = isSuccess;
        }

        getAllTheme(request,response);

    }
}
