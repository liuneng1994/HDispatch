package hdispatch.core.dispatch.dto.theme;

import com.hand.hap.system.dto.BaseDTO;

/**
 * Created by yyz on 2016/9/5.
 * yazheng.yang@hand-china.com
 */
public class Theme extends BaseDTO{
    private int themeId;
    private String themeName;
    private String description;
    private String projectName;
    private int projectVersion;
    private int projectId;
    private String projectDescription;


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

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }
}
