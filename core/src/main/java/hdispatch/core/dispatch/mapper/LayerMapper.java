package hdispatch.core.dispatch.mapper;

import hdispatch.core.dispatch.dto.layer.Layer;

/**
 * Created by yyz on 2016/9/7.
 * yazheng.yang@hand-china.com
 */
public interface LayerMapper {
    void save(Layer layer) throws Exception;
    //查询特定主题下面是否存在层的名称相同
    Layer selectByNameAndActiveAndThemeId(Layer layer);
    void deleteInLogic(Layer layer);
}
