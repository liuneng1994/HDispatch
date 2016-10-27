package hdispatch.core.dispatch.dto.authority;

import hdispatch.core.dispatch.authorityValidate.AuthorityValidator;
import hdispatch.core.dispatch.authorityValidate.PermissionType;

import java.util.HashSet;
import java.util.Set;

/**
 * 权限验证中，需要拦截验证的方法的参数类，
 * 在Service方法参数中，需要传递该类的实例，
 * 其中permissionType是这个方法执行需要的权限类型
 * @see AuthorityValidator <p>权限类型字符串常量</p>，
 * </br>
 *
 * Created by yyz on 2016/10/24.
 * @author yazheng.yang@hand-china.com
 */
public class PermissionParameter {
    private Long userId;
    private Long themeId;
    private Set<PermissionType> permissionTypes;//权限类型，存放的字符串来自AuthorityValidator中的静态变量

    public PermissionParameter(){}

    /**
     * 构造方法，
     *
     * @param userId
     * @param themeId
     * @param permissionTypes
     */
    public PermissionParameter(Long userId, Long themeId, Set<PermissionType> permissionTypes) {
        this.userId = userId;
        this.themeId = themeId;
        this.permissionTypes = permissionTypes;
    }
    public PermissionParameter(Long userId, Long themeId, boolean needReadPermission, boolean needOperatePermission) {
        this.userId = userId;
        this.themeId = themeId;
        this.permissionTypes = new HashSet<PermissionType>();
        if(needReadPermission){
            permissionTypes.add(PermissionType.READ);
        }
        if(needOperatePermission){
            permissionTypes.add(PermissionType.OPERATE);
        }
    }



    public Long getUserId() {
        return userId;
    }

    public PermissionParameter setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getThemeId() {
        return themeId;
    }

    public PermissionParameter setThemeId(Long themeId) {
        this.themeId = themeId;
        return this;
    }

    public Set<PermissionType> getPermissionTypes() {
        return permissionTypes;
    }

    public PermissionParameter setPermissionTypes(Set<PermissionType> permissionTypes) {
        this.permissionTypes = permissionTypes;
        return this;
    }
}
