package hdispatch.core.dispatch.mapper_hdispatch;

import com.hand.hap.mybatis.common.Mapper;
import hdispatch.core.dispatch.dto.layer.Layer;

import java.util.List;

/**
 * 层次mapper接口<br>
 */
public interface LayerMapper extends Mapper<Layer> {
    /**
     * 新建层
     * @param layer
     * @throws Exception
     */
    void save(Layer layer) throws Exception;

    /**
     * 查询特定主题下面是否存在层的名称相同
     * @param layer
     * @return
     */
    Layer selectByNameAndActiveAndThemeId(Layer layer);

    /**
     * 逻辑删除
     * @param layer
     */
    void deleteInLogic(Layer layer);

    /**
     * 获取主题下活跃的层
     * @param layer
     * @return
     */
    List<Layer> selectActiveLayersUnderTheme(Layer layer);

    /**
     * 根据id获取层
     * @param layer
     * @return
     */
    Layer selectById(Layer layer);

    /**
     * 获取所有活跃的层
     * @return
     */
    List<Layer> selectAllActiveLayers();

    /**
     * 更新层
     * @param layer
     */
    void update(Layer layer);
}
