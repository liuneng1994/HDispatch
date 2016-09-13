package hdispatch.core.dispatch.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import hdispatch.core.dispatch.dto.layer.Layer;
import hdispatch.core.dispatch.mapper.LayerMapper;
import hdispatch.core.dispatch.service.LayerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yyz on 2016/9/7.
 * yazheng.yang@hand-china.com
 */
@Service
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

    @Override
    public List<Layer> batchUpdate(IRequest requestContext, List<Layer> layerList) throws Exception {
        for (Layer layer : layerList) {
            if (layer.get__status() != null) {
                switch (layer.get__status()) {
                    case DTOStatus.ADD:
                        layerMapper.save(layer);
                        layer.setLayerActive(1L);
                        break;
                    case DTOStatus.UPDATE:

                        break;
                    case DTOStatus.DELETE:

                        break;
                    default:
                        break;
                }
            }
        }
        return layerList;
    }

    @Override
    public boolean[] checkIsExist(List<Layer> layerList) {
        boolean[] isExist = new boolean[layerList.size()];
        int i = 0;
        for(Layer layer : layerList){
            Layer layerReturn = layerMapper.selectByNameAndActiveAndThemeId(layer);
            if(null != layerReturn){
                isExist[i] = true;
            }
            i ++;
        }
        return isExist;
    }

    @Override
    public void deleteInLogic(Layer layer) {
        if(null != layer){
            layerMapper.deleteInLogic(layer);
        }
    }

    @Override
    public List<Layer> selectActiveLayersByThemeId(IRequest requestContext, int page, int pageSize, Layer layer) {
        PageHelper.startPage(page, pageSize);
        List<Layer> layerList = layerMapper.selectActiveLayersUnderTheme(layer);
        return layerList;
    }

    @Override
    public List<Layer> selectActiveLayersByThemeIdWithoutPaging(IRequest requestContext, Layer layer) {
        List<Layer> layerList = layerMapper.selectActiveLayersUnderTheme(layer);
        return layerList;
    }

    @Override
    public List<Layer> selectAllActiveLayersWithoutPaging(IRequest requestContext) {
        List<Layer> layerList = layerMapper.selectAllActiveLayers();
        return layerList;
    }
}
