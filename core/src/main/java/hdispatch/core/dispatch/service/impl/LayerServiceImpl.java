package hdispatch.core.dispatch.service.impl;

import hdispatch.core.dispatch.dto.layer.Layer;
import hdispatch.core.dispatch.mapper.LayerMapper;
import hdispatch.core.dispatch.service.LayerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by yyz on 2016/9/7.
 * yazheng.yang@hand-china.com
 */
public class LayerServiceImpl implements LayerService {
    private Logger logger = Logger.getLogger(LayerServiceImpl.class);
    @Autowired
    private LayerMapper layerMapper;
    /**
     * 创建层，同时创建层附带的流
     */
    @Override
    public boolean create(Layer layer) {
        try{
            layerMapper.save(layer);
        } catch (Exception e) {
            logger.error("保存层失败",e);
            return false;
        }
        return true;
    }
}
