package hdispatch.core.dispatch.dto.job;

import com.hand.hap.mybatis.annotation.Condition;
import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 任务类<br>
 * Created by yyz on 2016/9/11.
 * @author yazheng.yang@hand-china.com
 */
@Table(name = "HDISPATCH_JOB")
public class Job extends BaseDTO {
    @Id
    @Column(name = "job_id")
    @GeneratedValue(generator = GENERATOR_TYPE)
    private Long jobId;

    @Column(name = "name")
    @Condition(operator = LIKE)
    private String jobName;

    @Column(name = "svn")
    @Condition(operator = LIKE)
    private String jobSvn;

    @Column(name = "active")
    @Condition(operator = LIKE)
    private Long jobActive;

    @Column(name = "theme_id")
    @Condition(operator = LIKE)
    private Long themeId;

    @Column(name = "layer_id")
    @Condition(operator = LIKE)
    private Long layerId;

    @Column(name = "job_type")
    @Condition(operator = LIKE)
    private String jobType;

    //冗余信息
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

    public String getJobType() {
        return jobType;
    }

    public Job setJobType(String jobType) {
        this.jobType = jobType;
        return this;
    }
}
