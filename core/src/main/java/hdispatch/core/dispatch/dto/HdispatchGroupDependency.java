package hdispatch.core.dispatch.dto;

import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by 邓志龙 on 2016/9/7.
 */
@Table(name = "HDISPATCH_GROUP_DEPENDENCY")
public class HdispatchGroupDependency extends BaseDTO {


    /**
     * 表ID，主键，供其他表做外键.
     *
     */
    @Id
    @Column
    @GeneratedValue(generator = GENERATOR_TYPE)
    private Long id;

    @Column
    private Long groupId;

    @Column
    private Long depentGroupId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getDepentGroupId() {
        return depentGroupId;
    }

    public void setDepentGroupId(Long depentGroupId) {
        this.depentGroupId = depentGroupId;
    }
}

