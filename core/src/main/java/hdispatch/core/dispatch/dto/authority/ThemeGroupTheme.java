package hdispatch.core.dispatch.dto.authority;

import com.hand.hap.system.dto.BaseDTO;

/**
 * Created by yyz on 2016/10/14.
 *
 * @author yazheng.yang@hand-china.com
 *
 * 存储主题组和主题之间的映射关系
 */
public class ThemeGroupTheme extends BaseDTO {
    private Long themeGroupThemeId;
    private Long themeGroupId;
    private Long themeId;


    public Long getThemeGroupThemeId() {
        return themeGroupThemeId;
    }

    public ThemeGroupTheme setThemeGroupThemeId(Long themeGroupThemeId) {
        this.themeGroupThemeId = themeGroupThemeId;
        return this;
    }

    public Long getThemeGroupId() {
        return themeGroupId;
    }

    public ThemeGroupTheme setThemeGroupId(Long themeGroupId) {
        this.themeGroupId = themeGroupId;
        return this;
    }

    public Long getThemeId() {
        return themeId;
    }

    public ThemeGroupTheme setThemeId(Long themeId) {
        this.themeId = themeId;
        return this;
    }
}
