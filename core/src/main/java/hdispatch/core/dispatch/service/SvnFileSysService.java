package hdispatch.core.dispatch.service;

import com.hand.hap.core.ProxySelf;
import hdispatch.core.dispatch.dto.job.TreeNode;

import java.util.List;

/**
 * 用于获取kettle文件所在服务器的文件目录service接口<br>
 */
public interface SvnFileSysService extends ProxySelf<SvnFileSysService> {
    /**
     * 获取子节点
     */
    List<TreeNode> fetchSubNodes(TreeNode treeNode) throws Exception;

}
