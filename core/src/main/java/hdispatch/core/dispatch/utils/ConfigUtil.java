package hdispatch.core.dispatch.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * 读取本项目中config.properties文件中的配置<br>
 *
 * Created by yyz on 2016/11/3.
 *
 * @author yazheng.yang@hand-china.com
 */
public class ConfigUtil {
    private static Properties properties;
    private static String jobRuntimeParameter_themeGroupName;

    static {
        properties = new Properties();
        try {
            properties.load(ConfigUtil.class.getClassLoader().getResourceAsStream("config.properties"));
            jobRuntimeParameter_themeGroupName = properties.getProperty("job_runtime_parameters.themeGroupName");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取配置值
     * @param propertyName 属性名称
     * @return
     */
    public static String getProperty(String propertyName){
        return properties.getProperty(propertyName);
    }

    /**
     * 获取配置值
     * @param propertyName 属性名称
     * @param defaultValue 默认值
     * @return
     */
    public static String getProperty(String propertyName, String defaultValue){
        return properties.getProperty(propertyName, defaultValue);
    }

    /**
     * 获取配置信息的Properties对象
     * @return
     * @see Properties Properties对象
     */
    public static Properties getConfigProperties(){
        return properties;
    }

    /**
     * 获取 任务运行时参数所在的主题组名称（用于确认任务参数的权限）
     * @return
     */
    public static String getJobRuntimeParameter_themeGroupName() {
        return jobRuntimeParameter_themeGroupName;
    }
}
