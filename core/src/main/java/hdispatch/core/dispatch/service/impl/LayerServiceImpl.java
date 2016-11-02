package hdispatch.core.dispatch.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import hdispatch.core.dispatch.dto.job.Job;
import hdispatch.core.dispatch.dto.layer.Layer;
import hdispatch.core.dispatch.dto.workflow.SimpleWorkflow;
import hdispatch.core.dispatch.mapper.JobMapper;
import hdispatch.core.dispatch.mapper.LayerMapper;
import hdispatch.core.dispatch.mapper.WorkflowMapper;
import hdispatch.core.dispatch.service.LayerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 层次service接口实现类<br>
 * Created by yyz on 2016/9/7.
 * yazheng.yang@hand-china.com
 */
@Service
public class LayerServiceImpl implements LayerService {
    private Logger logger = Logger.getLogger(LayerServiceImpl.class);
    @Autowired
    private LayerMapper layerMapper;
    @Autowired
    private JobMapper jobMapper;
    @Autowired
    private WorkflowMapper workflowMapper;
    /**
     * 创建层
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
                        //获取当前ID的layer，判断如果名称改变，那么需要查看是否会引起重复冲突

                        Layer layer1FromDb = layerMapper.selectById(layer);
                        if(null == layer1FromDb){
                            logger.error("illegal access for missing layer_id",new Exception("illegal access for missing layer_id"));
                        }else {
                            if(layer.getLayerName().equals(layer1FromDb.getLayerName())){
                                layerMapper.update(layer);
                            }else {
                                Layer layerReturn = layerMapper.selectByNameAndActiveAndThemeId(layer);
                                if(null != layerReturn){
                                    throw new Exception(DUPLICATE_LAYER_NAME_UNDER_THEME);
                                }
                                else {
                                    layerMapper.update(layer);
                                }
                            }
                        }
                        break;
                    case DTOStatus.DELETE:
                        layerMapper.deleteInLogic(layer);
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

    @Override
    public List<Layer> checkIsMountJobOrWorkflow(List<Layer> layerList) {
        List<Layer> returnList = new ArrayList<>();
        for(Layer layer : layerList){
            Job job = new Job();
            job.setLayerId(layer.getLayerId());
            List<Job> jobs = jobMapper.selectByJob(job);
            if(null != job && 0 < jobs.size()){
                returnList.add(layer);
            }else {
                List<SimpleWorkflow> simpleWorkflows = workflowMapper.queryWorkflowUnderLayer(layer.getLayerId());
                if(null != simpleWorkflows && 0 < simpleWorkflows.size()){
                    returnList.add(layer);
                }
            }
        }
        return returnList;
    }
}
