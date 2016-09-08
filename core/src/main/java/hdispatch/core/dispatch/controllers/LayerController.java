package hdispatch.core.dispatch.controllers;

import hdispatch.core.dispatch.azkaban.entity.flow.DBFlow;
import hdispatch.core.dispatch.azkaban.service.FlowService;
import hdispatch.core.dispatch.azkaban.service.impl.FlowServiceImpl;
import hdispatch.core.dispatch.dto.layer.Layer;
import hdispatch.core.dispatch.service.LayerService;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyz on 2016/9/7.
 * yazheng.yang@hand-china.com
 */
@Controller
public class LayerController {
    private Logger logger = Logger.getLogger(LayerController.class);
    // TODO 暂时注释掉没有注入的功能
//    @Autowired
//    private FlowService flowService;
//    @Autowired
//    private LayerService layerService;

    @RequestMapping("/dispatcher/layer/submit")
    public void addLayer(HttpServletRequest request, HttpServletResponse response){
        try {
            long layerId = 0L;
            String name = request.getParameter("layerName");
            String flowId = name;
            String description = request.getParameter("layerDescription");
            String themeIdStr = request.getParameter("themeId");
            long themeId = Long.parseLong(themeIdStr);
            String seqStr = request.getParameter("seq");
            int seq = Integer.parseInt(seqStr);
            String successEmail = request.getParameter("successEmail");
            String failureEmail = request.getParameter("failureEmail");
            int version = 1;
            String type = "flow";

            DBFlow dbFlow = new DBFlow();
            dbFlow.setFlowId(flowId);
            List<String> successEmails = new ArrayList<String>();
            successEmails.add(successEmail);
            dbFlow.setSuccessEmail(successEmails);
            List<String> failureEmails = new ArrayList<String>();
            failureEmails.add(failureEmail);
            dbFlow.setFailureEmail(failureEmails);
            dbFlow.setType(type);
            dbFlow.setVersion(version);

            Layer layer = new Layer();
            layer.setName(name);
            layer.setDescription(description);
            layer.setFlowId(flowId);
            layer.setSeq(seq);
            layer.setThemeId(themeId);
            layer.setLayerId(layerId);

//            flowService.createFlow(dbFlow);
//            layerService.create(layer);

        }catch (Exception e){
            logger.error("创建层失败",e);
        }
    }
}
