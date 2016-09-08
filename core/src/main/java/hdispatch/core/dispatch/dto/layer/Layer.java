package hdispatch.core.dispatch.dto.layer;

/**
 * Created by yyz on 2016/9/7.
 * yazheng.yang@hand-china.com
 */
public class Layer {
    private long layerId;
    private String name;
    private String description;
    private long themeId;
    private int seq;
    private String flowId;

    public long getLayerId() {
        return layerId;
    }

    public void setLayerId(long layerId) {
        this.layerId = layerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getThemeId() {
        return themeId;
    }

    public void setThemeId(long themeId) {
        this.themeId = themeId;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }
}
