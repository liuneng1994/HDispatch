package hdispatch.core.dispatch.dto.job;

import com.hand.hap.system.dto.BaseDTO;

/**
 * Created by yyz on 2016/9/11.
 * yazheng.yang@hand-china.com
 */
public class Job extends BaseDTO {
    private Long jobId;
    private String jobName;
    private String jobSvn;
    private Long jobActive;
    private Long themeId;
    private Long layerId;

    //????????
    private String themeName;
    private String layerName;


    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobSvn() {
        return jobSvn;
    }

    public void setJobSvn(String jobSvn) {
        this.jobSvn = jobSvn;
    }

    public Long getJobActive() {
        return jobActive;
    }

    public void setJobActive(Long jobActive) {
        this.jobActive = jobActive;
    }

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    public Long getLayerId() {
        return layerId;
    }

    public void setLayerId(Long layerId) {
        this.layerId = layerId;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getLayerName() {
        return layerName;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }
}
