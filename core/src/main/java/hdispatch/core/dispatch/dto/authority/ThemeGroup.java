package hdispatch.core.dispatch.dto.authority;

import com.hand.hap.system.dto.BaseDTO;

/**
 * 主题组dto<br>
 * Created by yyz on 2016/10/14.
 * @author yazheng.yang@hand-china.com
 */
public class ThemeGroup extends BaseDTO {
    private Long themeGroupId;
    private String themeGroupName;
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
