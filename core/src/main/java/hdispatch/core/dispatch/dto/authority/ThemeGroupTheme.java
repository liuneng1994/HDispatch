package hdispatch.core.dispatch.dto.authority;

import com.hand.hap.system.dto.BaseDTO;

/**
 * 存储主题组和主题之间的映射关系的dto类<br>
 */
public class ThemeGroupTheme extends BaseDTO {
    private Long themeGroupThemeId;
    private Long themeGroupId;
    private Long themeId;
    //冗余字段
    private String themeName;
    private String themeDescription;
    private String themeGroupName;

    public String getThemeName() {
        return themeName;
    }

    public ThemeGroupTheme setThemeName(String themeName) {
        this.themeName = themeName;
        return this;
    }

    public String getThemeDescription() {
        return themeDescription;
    }

    public ThemeGroupTheme setThemeDescription(String themeDescription) {
        this.themeDescription = themeDescription;
        return this;
    }

    public String getThemeGroupName() {
        return themeGroupName;
    }

    public ThemeGroupTheme setThemeGroupName(String themeGroupName) {
        this.themeGroupName = themeGroupName;
        return this;
    }

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
