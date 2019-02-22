package hdispatch.core.dispatch.dto.theme;

import com.hand.hap.mybatis.annotation.Condition;
import com.hand.hap.system.dto.BaseDTO;
import sun.font.TrueTypeFont;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 主题类dto<br>
 */
@Table(name = "HDISPATCH_THEME")
public class Theme extends BaseDTO{
    @Id
    @GeneratedValue(generator = GENERATOR_TYPE)
    @Column(name = "theme_id")
    private Long themeId;

    @Condition(operator = LIKE)
    @Column(name = "name",nullable = false)
    private String themeName;

    @Condition(operator = LIKE)
    @Column(name = "description")
    private String themeDescription;

    @Condition(operator = LIKE)
    @Column(name = "active")
    private Long themeActive;

    @Column(name = "display_sequence")
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
