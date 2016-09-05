package hdispatch.core.dispatch.azkaban.service;

/**
 * Created by yyz on 2016/9/5.
 * yazheng.yang@hand-china.com
 */
public interface ThemeService {
    boolean create(String themeName, String description, String projectName, String projectDescription);
}
