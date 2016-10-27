package hdispatch.core.dispatch.authorityValidate;

import hdispatch.core.dispatch.dto.authority.PermissionParameter;

/**
 * Created by yyz on 2016/10/25.
 *
 * @author yazheng.yang@hand-china.com
 */
public class NoPermissionException extends RuntimeException {
    private PermissionParameter permissionParameter;
    public NoPermissionException(){
        super();
    }
    public NoPermissionException(String msg){
        super(msg);
    }

    public NoPermissionException(PermissionParameter permissionParameter){
        super();
        this.permissionParameter = permissionParameter;
    }

    public NoPermissionException(String msg,PermissionParameter permissionParameter){
        super(msg);
        this.permissionParameter = permissionParameter;
    }

    public PermissionParameter getPermissionParameter() {
        return permissionParameter;
    }

    public NoPermissionException setPermissionParameter(PermissionParameter permissionParameter) {
        this.permissionParameter = permissionParameter;
        return this;
    }
}
