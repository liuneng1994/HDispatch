package hdispatch.core.dispatch.service.impl;

import com.jcraft.jsch.*;
import hdispatch.core.dispatch.dto.job.SvnConfig;
import hdispatch.core.dispatch.dto.job.TreeNode;
import hdispatch.core.dispatch.service.SvnFileSysService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;


/**
 * Created by yyz on 2016/9/14.
 * yazheng.yang@hand-china.com
 */
@Service
public class SvnFileSysServiceImpl implements SvnFileSysService {
    public static final String SVN_CONFIG_FILE = "svnFileSys.properties";
    public static String SVN_FILE_SYS_IP = "172.20.0.203";
    public static int SVN_FILE_SYS_PORT = 22;
    public static String SVN_FILE_SYS_USERNAME = "hive";
    public static String SVN_FILE_SYS_PASSWORD = "handoracle";
    public static String SVN_FILE_SYS_ROOTPATH = "/home/ETL";
    //    @Autowired
    public static SvnConfig svnConfig = new SvnConfig();
    private Logger logger = Logger.getLogger(SvnFileSysServiceImpl.class);

    static {
        svnConfig.setIp(SVN_FILE_SYS_IP).
                setPort(SVN_FILE_SYS_PORT).
                setUserName(SVN_FILE_SYS_USERNAME).
                setPassword(SVN_FILE_SYS_PASSWORD).
                setRootPath(SVN_FILE_SYS_ROOTPATH);
    }

    @Override
    public List<TreeNode> fetchSubNodes(TreeNode treeNode) throws Exception {
        List<TreeNode> list = new ArrayList<TreeNode>();
        ChannelSftp sftp = null;
        Channel channel = null;
        Session sshSession = null;
        try {
            JSch jsch = new JSch();
            sshSession = jsch.getSession(svnConfig.getUserName(), svnConfig.getIp(), svnConfig.getPort());
            sshSession.setPassword(svnConfig.getPassword());
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();

            logger.info("Session connected!");

            channel = sshSession.openChannel("sftp");
            channel.connect();
            logger.info("Channel connected!");
            sftp = (ChannelSftp) channel;
            String path = svnConfig.getRootPath();
            if (!treeNode.getNodeId().equals("")) {
                path = svnConfig.getRootPath() + "/" + treeNode.getNodeId();
            }

            Vector<?> vector = sftp.ls(path);
            for (Object item : vector) {
                ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) item;
                String fileName = entry.getFilename();
                if (fileName.equals(".") || fileName.equals("..")) {
                    continue;
                } else {
                    String filePath = path + "/" + fileName;
                    boolean hasChild = hasChildren(sftp, filePath);

                    TreeNode treeNode_temp = new TreeNode();
                    if(hasChild){
                        treeNode_temp.setSpriteCssClass(TreeNode.CSS_CLASS_FOLDER);
//                        treeNode_temp.setExpanded(true);//不能设置为true，否则会递归地将非叶子节点展开
                    }
                    treeNode_temp.setNodeId(treeNode.getNodeId()+ "/" + fileName).
                            setNodeName(fileName).
                            setHasChild(hasChild);
                    list.add(treeNode_temp);
                }
            }
        } catch (Exception e) {
            logger.error("查询SVN文件服务器访问出错", e);
            e.printStackTrace();
        } finally {
            closeChannel(sftp);
            closeChannel(channel);
            closeSession(sshSession);
        }
        return list;
    }

    private static void closeChannel(Channel channel) {
        if (channel != null) {
            if (channel.isConnected()) {
                channel.disconnect();
            }
        }
    }

    private static void closeSession(Session session) {
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
            }
        }
    }

    private static boolean hasChildren(ChannelSftp sftp, String filePath) throws SftpException {
        boolean flag = false;
        if(filePath.endsWith(".kjb")||filePath.endsWith(".KJB")){
            return false;
        }
        if(filePath.endsWith(".sql")||filePath.endsWith(".SQL")){
            return false;
        }
        Vector<?> vector = sftp.ls(filePath);
        for (Object item : vector) {
            ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) item;
            String fileName = entry.getFilename();
            if (fileName.equals(".") || fileName.equals("..")) {
                continue;
            } else {
                flag = true;
                break;
            }
        }
        return flag;
    }
}
