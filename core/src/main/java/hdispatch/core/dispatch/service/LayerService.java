package hdispatch.core.dispatch.service;

import com.hand.hap.core.IRequest;
import hdispatch.core.dispatch.dto.layer.Layer;

import java.util.List;

/**
 * Created by yyz on 2016/9/7.
 * yazheng.yang@hand-china.com
 */
public interface LayerService {
    boolean create(Layer layer);
    List<Layer> batchUpdate(IRequest requestContext, List<Layer> layerList) throws Exception;
    //检查在当前主题下是否已经存在同名的层
    boolean[] checkIsExist(List<Layer> layerList);
    void deleteInLogic(Layer layer);
    List<Layer> selectActiveLayersByThemeId(IRequest requestContext,int page, int pageSize,Layer layer);
    List<Layer> selectActiveLayersByThemeIdWithoutPaging(IRequest requestContext,Layer layer);
    List<Layer> selectAllActiveLayersWithoutPaging(IRequest requestContext);

    public static final String DUPLICATE_LAYER_NAME_UNDER_THEME="duplicate layer name under same theme";

    /**
     * 获取传入的列表中挂载任务或任务流的层次列表，
     * 层次下面没有任务或任务流的层次将被过滤掉
     * @param layerList 挂载任务或任务流的层次列表
     * @return
     */
    List<Layer> checkIsMountJobOrWorkflow(List<Layer> layerList);


}
