package hdispatch.core.dispatch.service.impl;

import hdispatch.core.dispatch.service.LayerService;

/**
 * Created by yyz on 2016/9/7.
 * yazheng.yang@hand-china.com
 */
public class LayerServiceImpl implements LayerService {
    /**
     * 创建层，同时创建层附带的流
     */
    @Override
    public boolean create() {
        //将流的id和层的名称默认设置相同
        long layerId;

        String name;
        String flowId;

        String description;
        long themeId;
        int seq;
        String successEmail;
        String failureEmail;

        int version = 1;
        String type = "flow";



/*
界面：
    所在主题
    层名称
    层描述
    层序号
    出错提示邮件
    成功提示邮件


 */



        return false;
    }
}
