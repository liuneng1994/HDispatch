package hdispatch.core.dispatch.dto.theme;

import com.hand.hap.system.dto.BaseDTO;

/**
 * 主题类dto<br>
 * Created by yyz on 2016/9/5.
 * @author yazheng.yang@hand-china.com
 */
public class Theme extends BaseDTO{
    private Long themeId;
    private String themeName;
    private String themeDescription;
    private Long themeActive;
    private Long themeSequence;

    public Long getThemeId() {
        return themeId;
    }

    public Theme setThemeId(Long themeId) {
        this.themeId = themeId;
        return this;
    }

    public String getThemeName() {
        return themeName;
    }

    public Theme setThemeName(String themeName) {
        this.themeName = themeName;
        return this;
    }

    public String getThemeDescription() {
        return themeDescription;
    }

    public Theme setThemeDescription(String themeDescription) {
        this.themeDescription = themeDescription;
        return this;
    }

    public Long getThemeActive() {
        return themeActive;
    }

    public Theme setThemeActive(Long themeActive) {
        this.themeActive = themeActive;
        return this;
    }

    public Long getThemeSequence() {
        return themeSequence;
    }

    public Theme setThemeSequence(Long themeSequence) {
        this.themeSequence = themeSequence;
        return this;
    }
}
