package hdispatch.core.dispatch.dto.svn;

import com.hand.hap.mybatis.annotation.Condition;
import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 任务运行时参数dto<br>
 */
@Table(name = "HDISPATCH_FND_SCHEDULE_PARAMETER")
public class SvnParameter extends BaseDTO {
    @Id
    @Column(name = "SCHEDULE_PARA_ID")
    @GeneratedValue(generator = GENERATOR_TYPE)
    private Long scheduleParaId;

    @Column(name = "PARAMETER_NAME")
    @Condition(operator = LIKE)
    private String parameterName;

    @Column(name = "PARAMETER_VALUE")
    @Condition(operator = LIKE)
    private String parameterValue;

    @Column(name = "PARAMETER_SORT")
    @Condition(operator = LIKE)
    private Long parameterSort;

    @Column(name = "SUBJECT_NAME")
    @Condition(operator = LIKE)
    private String subjectName;

    @Column(name = "MAPPING_NAME")
    @Condition(operator = LIKE)
    private String mappingName;

    @Column(name = "SESSION_NAME")
    @Condition(operator = LIKE)
    private String sessionName;

    @Column(name = "WORKFLOW_NAME")
    @Condition(operator = LIKE)
    private String workFlowName;

    @Column(name = "FORMAT_MASK")
    private String formatMask;

    @Column(name = "PARA_OFFSET")
    private Long paraOffset;

    @Column(name = "FREQUENCY")
    @Condition(operator = LIKE)
    private String frequency;

    @Column(name = "ENABLE_FLAG")
    @Condition(operator = LIKE)
    private String enableFlag;

    @Column(name = "START_DATE_ACTIVE")
    @Condition(operator = LIKE)
    private Date startDateActive;

    @Column(name = "END_DATE_ACTIVE")
    @Condition(operator = LIKE)
    private Date endDateActive;

    @Column(name = "PARAMETER_DESC")
    @Condition(operator = LIKE)
    private String parameterDesc;

    @Column(name = "PARAMETER_VALUE_INI")
    @Condition(operator = LIKE)
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

    public void setScheduleParaId(Long scheduleParaId) {
        this.scheduleParaId = scheduleParaId;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public Long getParameterSort() {
        return parameterSort;
    }

    public void setParameterSort(Long parameterSort) {
        this.parameterSort = parameterSort;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getMappingName() {
        return mappingName;
    }

    public void setMappingName(String mappingName) {
        this.mappingName = mappingName;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getWorkFlowName() {
        return workFlowName;
    }

    public void setWorkFlowName(String workFlowName) {
        this.workFlowName = workFlowName;
    }

    public String getFormatMask() {
        return formatMask;
    }

    public void setFormatMask(String formatMask) {
        this.formatMask = formatMask;
    }

    public Long getParaOffset() {
        return paraOffset;
    }

    public void setParaOffset(Long paraOffset) {
        this.paraOffset = paraOffset;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    public Date getStartDateActive() {
        return startDateActive;
    }

    public void setStartDateActive(Date startDateActive) {
        this.startDateActive = startDateActive;
    }

    public Date getEndDateActive() {
        return endDateActive;
    }

    public void setEndDateActive(Date endDateActive) {
        this.endDateActive = endDateActive;
    }

    public String getParameterDesc() {
        return parameterDesc;
    }

    public void setParameterDesc(String parameterDesc) {
        this.parameterDesc = parameterDesc;
    }

    public String getParameterValueIni() {
        return parameterValueIni;
    }

    public void setParameterValueIni(String parameterValueIni) {
        this.parameterValueIni = parameterValueIni;
    }
}
