package hdispatch.core.dispatch.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import hdispatch.core.dispatch.dto.job.Job;
import hdispatch.core.dispatch.dto.layer.Layer;
import hdispatch.core.dispatch.dto.workflow.SimpleWorkflow;
import hdispatch.core.dispatch.mapper_hdispatch.JobMapper;
import hdispatch.core.dispatch.mapper_hdispatch.LayerMapper;
import hdispatch.core.dispatch.mapper_hdispatch.WorkflowMapper;
import hdispatch.core.dispatch.service.LayerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
     * 创建层次
     * @param layer
     * @return
     */
    @Override
    @Transactional("hdispatchTM")
    public boolean create(Layer layer) {
        try{
            layerMapper.save(layer);
        } catch (Exception e) {
            logger.error("保存层失败",e);
            return false;
        }
        return true;
    }

    /**
     * 批量编辑（增、删、改）
     * @param requestContext
     * @param layerList
     * @return
     * @throws Exception
     */
    @Override
    @Transactional("hdispatchTM")
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

    /**
     * 检查在当前主题下是否已经存在同名的层
     * @param layerList
     * @return
     */
    @Override
    @Transactional("hdispatchTM")
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

    /**
     * 逻辑删除（在数据库中将active字段设置为1）
     * @param layer
     */
    @Override
    @Transactional("hdispatchTM")
    public void deleteInLogic(Layer layer) {
        if(null != layer){
            layerMapper.deleteInLogic(layer);
        }
    }

    /**
     * 主题下，根据层次中的字段进行模糊查询（分页）
     * @param requestContext
     * @param page
     * @param pageSize
     * @param layer
     * @return
     */
    @Override
    @Transactional("hdispatchTM")
    public List<Layer> selectActiveLayersByThemeId(IRequest requestContext, int page, int pageSize, Layer layer) {
        PageHelper.startPage(page, pageSize);
        List<Layer> layerList = layerMapper.selectActiveLayersUnderTheme(layer);
        return layerList;
    }

    /**
     * 主题下，根据层次中的字段进行模糊查询
     * @param requestContext
     * @param layer
     * @return
     */
    @Override
    @Transactional("hdispatchTM")
    public List<Layer> selectActiveLayersByThemeIdWithoutPaging(IRequest requestContext, Layer layer) {
        List<Layer> layerList = layerMapper.selectActiveLayersUnderTheme(layer);
        return layerList;
    }

    /**
     * 获取所有主题下的所有层次
     * @param requestContext
     * @return
     */
    @Override
    @Transactional("hdispatchTM")
    public List<Layer> selectAllActiveLayersWithoutPaging(IRequest requestContext) {
        List<Layer> layerList = layerMapper.selectAllActiveLayers();
        return layerList;
    }

    /**
     * 获取传入的列表中挂载任务或任务流的层次列表，
     * 层次下面没有任务或任务流的层次将被过滤掉,
     * 用于删除判断
     * @param layerList 层次列表
     * @return 挂载任务或任务流的层次列表
     */
    @Override
    @Transactional("hdispatchTM")
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
