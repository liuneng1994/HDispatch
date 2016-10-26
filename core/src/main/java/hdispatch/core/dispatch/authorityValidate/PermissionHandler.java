package hdispatch.core.dispatch.authorityValidate;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.impl.RequestHelper;
import hdispatch.core.dispatch.dto.authority.PermissionParameter;
import hdispatch.core.dispatch.service.HdispatchAuthorityService;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * Created by yyz on 2016/10/24.
 *
 * @author yazheng.yang@hand-china.com
 */
@Aspect
public class PermissionHandler {
    @Autowired
    private HdispatchAuthorityService hdispatchAuthorityService;
    private Logger logger = Logger.getLogger(PermissionHandler.class);

//    @Before("execution(* hdispatch.core.dispatch.service..*.*(..))")
    public void checkPermission(JoinPoint joinPoint){
        Object[] targetMehodParameters = joinPoint.getArgs();
        PermissionParameter permissionParameter = null;
        for(Object parameter : targetMehodParameters){
            if (null != parameter && parameter.getClass() == PermissionParameter.class){
                permissionParameter = (PermissionParameter) parameter;
                break;
            }
        }
        if(null == permissionParameter){
            logger.error("missing PermissionParameter");
            throw new RuntimeException("missing PermissionParameter");
        }
        checkLegalPermissionParameter(permissionParameter);
        Set<PermissionType> permissionTypes = permissionParameter.getPermissionTypes();
        boolean isNeedRead = permissionTypes.contains(PermissionType.READ);
        boolean isNeedOperate = permissionTypes.contains(PermissionType.OPERATE);

        if(isNeedOperate && isNeedRead){
            boolean hasPermission = hdispatchAuthorityService.hasReadAndOperatePermission(permissionParameter.getThemeId(), permissionParameter.getUserId());
            if(!hasPermission){
                logger.error(new NoPermissionException());
                throw new NoPermissionException();
            }
            return;
        }

        if(isNeedRead){
            if(!hdispatchAuthorityService.hasReadPermission(permissionParameter.getThemeId(),permissionParameter.getUserId())){
                logger.error(new NoPermissionException());
                throw new NoPermissionException();
            }
            return;
        }

        if(isNeedOperate){
            if(!hdispatchAuthorityService.hasOperatePermission(permissionParameter.getThemeId(),permissionParameter.getUserId())){
                logger.error(new NoPermissionException());
                throw new NoPermissionException();
            }
            return;
        }

        if(permissionTypes.contains(PermissionType.DELAY_CHECK)){
            return;
        }

        logger.error(new NoPermissionException());
        throw new NoPermissionException();
    }

    /**
     * 检查PermissionParameter是否合法,
     * 检查：userId、themeId、permissionTypes 非空;
     * @param parameter PermissionParameter
     * @see PermissionParameter
     */
    private void checkLegalPermissionParameter(PermissionParameter parameter){
        if(null == parameter ||
                null == parameter.getUserId() ||
                null == parameter.getThemeId() ||
                null == parameter.getPermissionTypes()){
            throw new RuntimeException("Illegal PermissionParameter");
        }
    }
}
