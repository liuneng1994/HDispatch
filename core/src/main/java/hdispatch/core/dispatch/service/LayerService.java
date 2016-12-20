package hdispatch.core.dispatch.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hdispatch.core.dispatch.dto.layer.Layer;

import java.util.List;
import java.util.Map;

/**
 * 层次service接口<br>
 * Created by yyz on 2016/9/7.
 * yazheng.yang@hand-china.com
 */
public interface LayerService extends IBaseService<Layer>, ProxySelf<LayerService> {
//    /**
//     * 创建层次
//     * @param layer
//     * @return
//     */
//    boolean create(Layer layer);

    /**
     * 批量编辑（增、删、改）
     * @param requestContext
     * @param layerList
     * @return
     * @throws Exception
     */
    List<Layer> batchUpdate(IRequest requestContext, List<Layer> layerList, Map<String,String> feedbackMsg) throws Exception;

//    /**
//     * 检查在当前主题下是否已经存在同名的层
//     * @param layerList
//     * @return
//     */
//    boolean[] checkIsExist(List<Layer> layerList);

    /**
     * 逻辑删除（在数据库中将active字段设置为1）
     * @param layer
     */
    void deleteInLogic(Layer layer);

    /**
     * 主题下，根据层次中的字段进行模糊查询（分页）
     * @param requestContext
     * @param page
     * @param pageSize
     * @param layer
     * @return
     */
    List<Layer> selectActiveLayersByThemeId(IRequest requestContext,int page, int pageSize,Layer layer);

    /**
     * 主题下，根据层次中的字段进行模糊查询
     * @param requestContext
     * @param layer
     * @return
     */
    List<Layer> selectActiveLayersByThemeIdWithoutPaging(IRequest requestContext,Layer layer);

//    /**
//     * 获取所有主题下的所有层次
//     * @param requestContext
//     * @return
//     */
//    List<Layer> selectAllActiveLayersWithoutPaging(IRequest requestContext);

    public static final String DUPLICATE_LAYER_NAME_UNDER_THEME="duplicate layer name under same theme";

    /**
     * 获取传入的列表中挂载任务或任务流的层次列表，
     * 层次下面没有任务或任务流的层次将被过滤掉,
     * 用于删除判断
     * @param layerList 层次列表
     * @return 挂载任务或任务流的层次列表
     */
    List<Layer> checkIsMountJobOrWorkflow(List<Layer> layerList);


}
