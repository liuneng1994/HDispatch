package hdispatch.core.dispatch.service.impl;

import com.jcraft.jsch.*;
import hdispatch.core.dispatch.dto.job.SvnConfig;
import hdispatch.core.dispatch.dto.job.TreeNode;
import hdispatch.core.dispatch.service.SvnFileSysService;
import hdispatch.core.dispatch.utils.ConfigUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;


/**
 * 用于获取kettle文件所在服务器的文件目录service接口实现类<br>
 * Created by yyz on 2016/9/14.
 * @author yazheng.yang@hand-china.com
 */
@Service
public class SvnFileSysServiceImpl implements SvnFileSysService {
//    //    @Autowired
//    public static SvnConfig svnConfig = new SvnConfig();
    private Logger logger = Logger.getLogger(SvnFileSysServiceImpl.class);
//    @Resource(name = "svnConfig")
    private static SvnConfig svnConfig;
    static {
        svnConfig = new SvnConfig();
        svnConfig.setIp(ConfigUtil.getKettle_file_system_server_ip()).
                setPort(Integer.parseInt(ConfigUtil.getKettle_file_system_server_port())).
                setUserName(ConfigUtil.getKettle_file_system_server_login_userName()).
                setPassword(ConfigUtil.getKettle_file_system_server_login_password()).
                setRootPath(ConfigUtil.getKettle_file_system_server_relative_rootPath());
    }

    /**
     * 根据节点获取子节点
     * @param treeNode
     * @return
     * @throws Exception
     */
    @Override
    @Transactional("hdispatchTM")
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
                        treeNode_temp.setNodeId(treeNode.getNodeId()+ "/" + fileName).
                                setNodeName(fileName).
                                setHasChild(hasChild);
                        list.add(treeNode_temp);
                    }else {
                        if(isLegalFile(fileName)){
                            treeNode_temp.setNodeId(treeNode.getNodeId()+ "/" + fileName).
                                    setNodeName(fileName).
                                    setHasChild(hasChild);
                            list.add(treeNode_temp);
                        }
                    }

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

    /**
     * 关闭连接信道
     * @param channel
     */
    private static void closeChannel(Channel channel) {
        if (channel != null) {
            if (channel.isConnected()) {
                channel.disconnect();
            }
        }
    }

    /**
     * 关闭连接会话
     * @param session
     */
    private static void closeSession(Session session) {
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
            }
        }
    }

    /**
     * 判断是否存在子节点
     * @param sftp
     * @param filePath
     * @return
     * @throws SftpException
     */
    private static boolean hasChildren(ChannelSftp sftp, String filePath) throws SftpException {
        boolean flag = false;
        String surfix = filePath.substring(filePath.length()-4);
        if(".kjb".equalsIgnoreCase(surfix)||".ktl".equalsIgnoreCase(surfix)){
            return false;
        }
        if(".sql".equalsIgnoreCase(surfix)){
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

    /**
     * 判断是否是kettle文件
     * @param fileName
     * @return
     */
    private boolean isLegalFile(String fileName){
        boolean flag = false;
        String surfix = fileName.substring(fileName.length()-4);
        if(".kjb".equalsIgnoreCase(surfix) || ".ktl".equalsIgnoreCase(surfix)){
            flag = true;
        }
        return flag;
    }
}
