package hdispatch.core.dispatch.dto.job;

/**
 * 存储SVN服务器信息的类<br>
 */
public class SvnConfig {
    private String ip;
    private int port;
    private String userName;
    private String password;
    //SVN文件根目录
    private String rootPath;

    public String getIp() {
        return ip;
    }

    public SvnConfig setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public int getPort() {
        return port;
    }

    public SvnConfig setPort(int port) {
        this.port = port;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public SvnConfig setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SvnConfig setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getRootPath() {
        return rootPath;
    }

    public SvnConfig setRootPath(String rootPath) {
        this.rootPath = rootPath;
        return this;
    }
}
