package hdispatch.core.dispatch.dto.layer;

import com.hand.hap.mybatis.annotation.Condition;
import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 层次类dto<br>
 * Created by yyz on 2016/9/7.
 * @author yazheng.yang@hand-china.com
 */
@Table(name = "HDISPATCH_LAYER")
public class Layer extends BaseDTO{
    @Id
    @GeneratedValue(generator = GENERATOR_TYPE)
    @Column(name = "layer_id")
    private Long layerId;

    @Condition(operator = LIKE)
    @Column(name = "name",nullable = false)
    private String layerName;

    @Condition(operator = LIKE)
    @Column(name = "description")
    private String layerDescription;

    @Condition(operator = LIKE)
    @Column(name = "active")
    private Long layerActive;

    @Column(name = "display_sequence")
    private Long layerSequence;

    @Condition(operator = LIKE)
    @Column(name = "theme_id",nullable = false)
    private Long themeId;
    //layerActive处于活跃状态的值
    public static final Long ACTIVE_STATUS = 1L;

    public Long getLayerId() {
        return layerId;
    }

    public void setLayerId(Long layerId) {
        this.layerId = layerId;
    }

    public String getLayerName() {
        return layerName;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }

    public String getLayerDescription() {
        return layerDescription;
    }

    public void setLayerDescription(String layerDescription) {
        this.layerDescription = layerDescription;
    }

    public Long getLayerActive() {
        return layerActive;
    }

    public void setLayerActive(Long layerActive) {
        this.layerActive = layerActive;
    }

    public Long getLayerSequence() {
        return layerSequence;
    }

    public void setLayerSequence(Long layerSequence) {
        this.layerSequence = layerSequence;
    }

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }
}
