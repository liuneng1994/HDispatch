package hdispatch.core.dispatch.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yyz on 2016/9/7.
 * yazheng.yang@hand-china.com
 */
@Controller
public class LayerController {

    @RequestMapping("/dispatcher/layer/submit")
    public void addTheme(HttpServletRequest request, HttpServletResponse response){
        //这里获取参数json

    }
}
