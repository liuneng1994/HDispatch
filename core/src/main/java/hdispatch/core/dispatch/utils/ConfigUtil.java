package hdispatch.core.dispatch.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * 读取本项目中config.properties文件中的配置<br>
 *
 */
public class ConfigUtil {
    private static Properties properties;
    private static String jobRuntimeParameter_themeGroupName;
    private static String themeLayer_themeGroupName;
    //读取kettle文件目录结构的服务器ip
    private static String kettle_file_system_server_ip;
    //读取kettle文件目录结构的服务器端口
    private static String kettle_file_system_server_port;
    //读取kettle文件目录结构的服务器,kettle文件的根目录
    private static String kettle_file_system_server_relative_rootPath;
    //读取kettle文件目录结构的服务器-登录账户
    private static String kettle_file_system_server_login_userName;
    //读取kettle文件目录结构的服务器-登录密码
    private static String kettle_file_system_server_login_password;

    //日志保留时间（单位：天）
    private static long logs_remain_days;

    static {
        properties = new Properties();
        try {
            properties.load(ConfigUtil.class.getClassLoader().getResourceAsStream("config.properties"));
            jobRuntimeParameter_themeGroupName = properties.getProperty("job_runtime_parameters.themeGroupName");
            themeLayer_themeGroupName = properties.getProperty("theme_layer_management.themeGroupName");

            kettle_file_system_server_ip = properties.getProperty("kettle_file_system_server_ip");
            kettle_file_system_server_port = properties.getProperty("kettle_file_system_server_port");
            kettle_file_system_server_relative_rootPath = properties.getProperty("kettle_file_system_server_relative_rootPath");
            kettle_file_system_server_login_userName = properties.getProperty("kettle_file_system_server_login_userName");
            kettle_file_system_server_login_password = properties.getProperty("kettle_file_system_server_login_password");

            logs_remain_days = Long.parseLong(properties.getProperty("hdispatch.logs_remain_days","30"));
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

    /**
     * 获取 管理主题创建的权限所在的主题组名称（用于确认操作主题（创建）的权限）
     * @return
     */
    public static String getThemeLayer_themeGroupName() {
        return themeLayer_themeGroupName;
    }


    public static String getKettle_file_system_server_ip() {
        return kettle_file_system_server_ip;
    }

    public static String getKettle_file_system_server_port() {
        return kettle_file_system_server_port;
    }

    public static String getKettle_file_system_server_relative_rootPath() {
        return kettle_file_system_server_relative_rootPath;
    }

    public static String getKettle_file_system_server_login_userName() {
        return kettle_file_system_server_login_userName;
    }

    public static String getKettle_file_system_server_login_password() {
        return kettle_file_system_server_login_password;
    }

    public static long getLogs_remain_days(){
        return logs_remain_days;
    }
}
