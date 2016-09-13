package hdispatch.core.dispatch.dto.layer;

import com.hand.hap.system.dto.BaseDTO;

/**
 * Created by yyz on 2016/9/7.
 * yazheng.yang@hand-china.com
 */
public class Layer extends BaseDTO{
    private Long layerId;
    private String layerName;
    private String layerDescription;
    private Long layerActive;
    private Long layerSequence;
    private Long themeId;

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
