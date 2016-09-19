package hdispatch.core.dispatch.dto.job;

/**
 * Created by yyz on 2016/9/14.
 * yazheng.yang@hand-china.com
 *
 * 用于存放页面树状结构的节点数据
 */
public class TreeNode {
    /**
     * nodeId存放当前节点的相对路径，相对于设置的SVN文件服务器的目录
     */
    private String nodeId;
    /**
     * 文件名称
     */
    private String nodeName;
    /**
     * isLeaf:true,是一个普通叶子节点
     * isLeaf:false,是一个可能包含子节点
     *
     * 对于文件夹，hasChild为true
     * isLeaf暂时为冗余变量
     */
    private boolean hasChild;
    private boolean isLeaf;
    /**
     * 页面上的样式，暂时设置为两种值：html、folder
     * 默认值为：html
     */
    public static final String CSS_CLASS_HTML="html";
    public static final String CSS_CLASS_FOLDER="folder";
    private String spriteCssClass = CSS_CLASS_HTML;
    /**
     * 这个变量决定在加载树的过程中是否自动扩展，如果为true那么将会将被标志为true的节点展开，自动发送请求多次获取
     */
    private boolean expanded;

    public boolean isExpanded() {
        return expanded;
    }

    public TreeNode setExpanded(boolean expanded) {
        this.expanded = expanded;
        return this;
    }

    public String getSpriteCssClass() {
        return spriteCssClass;
    }

    public TreeNode setSpriteCssClass(String spriteCssClass) {
        this.spriteCssClass = spriteCssClass;
        return this;
    }

    public String getNodeId() {
        return nodeId;
    }

    public TreeNode setNodeId(String nodeId) {
        this.nodeId = nodeId;
        return this;
    }

    public String getNodeName() {
        return nodeName;
    }

    public TreeNode setNodeName(String nodeName) {
        this.nodeName = nodeName;
        return this;
    }

    public boolean isHasChild() {
        return hasChild;
    }

    public TreeNode setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
        return this;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public TreeNode setLeaf(boolean leaf) {
        isLeaf = leaf;
        return this;
    }
}
