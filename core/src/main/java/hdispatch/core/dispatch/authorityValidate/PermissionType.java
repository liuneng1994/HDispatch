package hdispatch.core.dispatch.authorityValidate;

/**
 * 权限类别枚举类<br>
 * 权限类别：
 * READ——读、
 * OPERATE——操作、
 * DELAY_CHECK——延迟判断（对于那些批量操作或者读取数据的方法可以使用DELAY_CHECK暂时绕过权限拦截自行过滤数据）<br>
 * Created by yyz on 2016/10/25.
 * @author yazheng.yang@hand-china.com
 */
public enum PermissionType {
    READ,OPERATE,DELAY_CHECK
}
