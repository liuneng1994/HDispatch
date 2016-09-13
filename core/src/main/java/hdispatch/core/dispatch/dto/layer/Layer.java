package hdispatch.core.dispatch.dto.layer;

import com.hand.hap.system.dto.BaseDTO;

/**
 * Created by yyz on 2016/9/7.
 * yazheng.yang@hand-china.com
 */
public class Layer extends BaseDTO{
    private long layerId;
    private String layerName;
    private String layerDescription;
    private int layerActive;
    private int layerSequence;
    private long themeId;

    public long getLayerId() {
        return layerId;
    }

    public void setLayerId(long layerId) {
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

    public int getLayerActive() {
        return layerActive;
    }

    public void setLayerActive(int layerActive) {
        this.layerActive = layerActive;
    }

    public int getLayerSequence() {
        return layerSequence;
    }

    public void setLayerSequence(int layerSequence) {
        this.layerSequence = layerSequence;
    }

    public long getThemeId() {
        return themeId;
    }

    public void setThemeId(long themeId) {
        this.themeId = themeId;
    }
}
