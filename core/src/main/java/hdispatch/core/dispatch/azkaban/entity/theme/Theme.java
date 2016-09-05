package hdispatch.core.dispatch.azkaban.entity.theme;

/**
 * Created by yyz on 2016/9/5.
 * yazheng.yang@hand-china.com
 */
public class Theme {
    private int themeId;
    private String themeName;
    private String description;
    private String projectName;
    private int projectVersion;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getProjectVersion() {
        return projectVersion;
    }

    public void setProjectVersion(int projectVersion) {
        this.projectVersion = projectVersion;
    }
}
