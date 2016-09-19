package hdispatch.core.dispatch.service;

import hdispatch.core.dispatch.dto.job.TreeNode;

import java.util.List;

/**
 * Created by yyz on 2016/9/14.
 * yazheng.yang@hand-china.com
 *
 * 用于获取SVN所在服务器的文件目录
 */
public interface SvnFileSysService {
    /**
     * 获取子节点
     */
    List<TreeNode> fetchSubNodes(TreeNode treeNode) throws Exception;

}
