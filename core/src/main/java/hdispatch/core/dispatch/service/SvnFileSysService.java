package hdispatch.core.dispatch.service;

import hdispatch.core.dispatch.dto.job.TreeNode;

import java.util.List;

/**
 * 用于获取kettle文件所在服务器的文件目录service接口<br>
 * Created by yyz on 2016/9/14.
 * @author yazheng.yang@hand-china.com
 */
public interface SvnFileSysService {
    /**
     * 获取子节点
     */
    List<TreeNode> fetchSubNodes(TreeNode treeNode) throws Exception;

}
