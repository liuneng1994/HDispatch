package hdispatch.core.dispatch.mapper;

import hdispatch.core.dispatch.dto.layer.Layer;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import java.util.List;
import java.util.Map;

/**
 * Created by yyz on 2016/9/7.
 * @author yazheng.yang@hand-china.com
 */
public interface LayerMapper {
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

    //-------------------------下面是经过权限验证的方法-----------------

    /**
     * 新建层(经过权限验证)
     * @param params 包含插入的Layer需要的数据，key-value与Layer对象的必要的成员变量一致，以及userId
     * @throws Exception
     */
    void save_validated(Map params) throws Exception;

    /**
     * 查询特定主题下面是否存在层的名称相同<br>
     *     用于查重，不做读权限的验证
     * @param layer
     * @return
     */
    Layer selectByNameAndActiveAndThemeId_validated(Layer layer);

    /**
     * 逻辑删除(经过权限验证)
     * @param params 至少包含themeId、userId、layerId
     */
    void deleteInLogic_validated(Map params);

    /**
     * 获取主题下活跃的层(经过权限验证)
     * @param themeId
     * @param userId
     * @return
     */
    List<Layer> selectActiveLayersUnderTheme_validated(@Param("themeId")Long themeId,@Param("userId")Long userId);

    /**
     * 根据id获取层(经过权限验证)
     * @param themeId
     * @param userId
     * @param layerId
     * @return
     */
    Layer selectById_validated(@Param("themeId")Long themeId, @Param("userId")Long userId, @Param("layerId")Long layerId);

    /**
     * 获取所有活跃的层
     * @return
     */
    List<Layer> selectAllActiveLayers_validated();

    /**
     * 更新层
     * @param layer
     */
    void update_validated(Layer layer);
}
