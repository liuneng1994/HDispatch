package hdispatch.core.dispatch.dto.theme;

import com.hand.hap.system.dto.BaseDTO;

/**
 * Created by yyz on 2016/9/5.
 * @author yazheng.yang@hand-china.com
 *
 * 主题类
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

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getThemeDescription() {
        return themeDescription;
    }

    public void setThemeDescription(String themeDescription) {
        this.themeDescription = themeDescription;
    }

    public Long getThemeActive() {
        return themeActive;
    }

    public void setThemeActive(Long themeActive) {
        this.themeActive = themeActive;
    }

    public Long getThemeSequence() {
        return themeSequence;
    }

    public void setThemeSequence(Long themeSequence) {
        this.themeSequence = themeSequence;
    }
}
