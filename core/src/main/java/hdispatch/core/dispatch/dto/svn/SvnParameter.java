package hdispatch.core.dispatch.dto.svn;

import com.hand.hap.system.dto.BaseDTO;

import java.util.Date;

/**
 * 任务运行时参数dto<br>
 * Created by yyz on 2016/9/23.
 * @author yazheng.yang@hand-china.com
 */
public class SvnParameter extends BaseDTO {
    private Long scheduleParaId;
    private String parameterName;
    private String parameterValue;
    private Long parameterSort;
    private String subjectName;
    private String mappingName;
    private String sessionName;
    private String workFlowName;
    private String formatMask;
    private Long paraOffset;
    private String frequency;
    private String enableFlag;
    private Date startDateActive;
    private Date endDateActive;
    private String parameterDesc;
    private String parameterValueIni;
//    父类中已经存在
//private Date creationDate;
//private Long createdBy;
//private Long lastUpdatedBy;
//private Date lastUpdateDate;
//private Long lastUpdateLogin;

    public Long getScheduleParaId() {
        return scheduleParaId;
    }

    public SvnParameter setScheduleParaId(Long scheduleParaId) {
        this.scheduleParaId = scheduleParaId;
        return this;
    }

    public String getParameterName() {
        return parameterName;
    }

    public SvnParameter setParameterName(String parameterName) {
        this.parameterName = parameterName;
        return this;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public SvnParameter setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
        return this;
    }

    public Long getParameterSort() {
        return parameterSort;
    }

    public SvnParameter setParameterSort(Long parameterSort) {
        this.parameterSort = parameterSort;
        return this;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public SvnParameter setSubjectName(String subjectName) {
        this.subjectName = subjectName;
        return this;
    }

    public String getMappingName() {
        return mappingName;
    }

    public SvnParameter setMappingName(String mappingName) {
        this.mappingName = mappingName;
        return this;
    }

    public String getSessionName() {
        return sessionName;
    }

    public SvnParameter setSessionName(String sessionName) {
        this.sessionName = sessionName;
        return this;
    }

    public String getWorkFlowName() {
        return workFlowName;
    }

    public SvnParameter setWorkFlowName(String workFlowName) {
        this.workFlowName = workFlowName;
        return this;
    }

    public String getFormatMask() {
        return formatMask;
    }

    public SvnParameter setFormatMask(String formatMask) {
        this.formatMask = formatMask;
        return this;
    }

    public Long getParaOffset() {
        return paraOffset;
    }

    public SvnParameter setParaOffset(Long paraOffset) {
        this.paraOffset = paraOffset;
        return this;
    }

    public String getFrequency() {
        return frequency;
    }

    public SvnParameter setFrequency(String frequency) {
        this.frequency = frequency;
        return this;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public SvnParameter setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
        return this;
    }

    public Date getStartDateActive() {
        return startDateActive;
    }

    public SvnParameter setStartDateActive(Date startDateActive) {
        this.startDateActive = startDateActive;
        return this;
    }

    public Date getEndDateActive() {
        return endDateActive;
    }

    public SvnParameter setEndDateActive(Date endDateActive) {
        this.endDateActive = endDateActive;
        return this;
    }

    public String getParameterDesc() {
        return parameterDesc;
    }

    public SvnParameter setParameterDesc(String parameterDesc) {
        this.parameterDesc = parameterDesc;
        return this;
    }

    public String getParameterValueIni() {
        return parameterValueIni;
    }

    public SvnParameter setParameterValueIni(String parameterValueIni) {
        this.parameterValueIni = parameterValueIni;
        return this;
    }
}
