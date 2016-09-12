package hdispatch.core.dispatch.dto.theme;

import com.hand.hap.system.dto.BaseDTO;

/**
 * Created by yyz on 2016/9/5.
 * yazheng.yang@hand-china.com
 */
public class Theme extends BaseDTO{
    private int themeId;
    private String themeName;
    private String themeDescription;
    private int themeActive;
    private int themeSequence;


    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
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

    public int getThemeActive() {
        return themeActive;
    }

    public void setThemeActive(int themeActive) {
        this.themeActive = themeActive;
    }

    public int getThemeSequence() {
        return themeSequence;
    }

    public void setThemeSequence(int themeSequence) {
        this.themeSequence = themeSequence;
    }
}
