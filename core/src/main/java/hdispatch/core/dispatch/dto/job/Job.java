package hdispatch.core.dispatch.dto.job;

import com.hand.hap.system.dto.BaseDTO;

/**
 * Created by yyz on 2016/9/11.
 * yazheng.yang@hand-china.com
 */
public class Job extends BaseDTO {
    private int jobId;
    private String jobName;
    private String jobSvn;
    private int jobActive;
    private int themeId;
    private int layerId;

    //冗余属性
    private String themeName;
    private String layerName;


    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
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

    public int getJobActive() {
        return jobActive;
    }

    public void setJobActive(int jobActive) {
        this.jobActive = jobActive;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    public int getLayerId() {
        return layerId;
    }

    public void setLayerId(int layerId) {
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
