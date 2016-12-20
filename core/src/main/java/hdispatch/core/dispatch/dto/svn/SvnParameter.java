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
 * Created by yyz on 2016/9/23.
 * @author yazheng.yang@hand-china.com
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
