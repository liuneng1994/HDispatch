package hdispatch.core.dispatch.dto.authority;

import com.hand.hap.mybatis.annotation.Condition;
import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 主题组dto<br>
 * Created by yyz on 2016/10/14.
 * @author yazheng.yang@hand-china.com
 */
@Table(name = "HDISPATCH_THEMEGROUP")
public class ThemeGroup extends BaseDTO {

    @Id
    @GeneratedValue(generator = GENERATOR_TYPE)
    @Column(name = "theme_group_id")
    private Long themeGroupId;

    @Column(name = "theme_group_name")
    @Condition(operator = LIKE)
    private String themeGroupName;

    @Column(name = "theme_group_desc")
    @Condition(operator = LIKE)
    private String themeGroupDesc;

    public Long getThemeGroupId() {
        return themeGroupId;
    }

    public ThemeGroup setThemeGroupId(Long themeGroupId) {
        this.themeGroupId = themeGroupId;
        return this;
    }

    public String getThemeGroupName() {
        return themeGroupName;
    }

    public ThemeGroup setThemeGroupName(String themeGroupName) {
        this.themeGroupName = themeGroupName;
        return this;
    }

    public String getThemeGroupDesc() {
        return themeGroupDesc;
    }

    public ThemeGroup setThemeGroupDesc(String themeGroupDesc) {
        this.themeGroupDesc = themeGroupDesc;
        return this;
    }
}
