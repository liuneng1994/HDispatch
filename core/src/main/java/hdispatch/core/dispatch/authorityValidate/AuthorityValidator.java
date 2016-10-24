package hdispatch.core.dispatch.authorityValidate;

import com.hand.hap.account.dto.User;
import hdispatch.core.dispatch.dto.authority.ThemeGroup;
import hdispatch.core.dispatch.dto.theme.Theme;
import hdispatch.core.dispatch.service.HdispatchAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by yyz on 2016/10/21.
 *
 * @author yazheng.yang@hand-china.com
 *
 * 权限验证器
 */
public class AuthorityValidator {
    //权限类型——读权限
    public static final String AUTHORITY_READ = "read";
    //权限类型——操作权限
    public static final String AUTHORITY_OPERATE = "operate";
    @Autowired
    private HdispatchAuthorityService hdispatchAuthorityService;

    public boolean validate(User user, Theme theme, String authorityType){
        return validate(user.getUserId(),theme.getThemeId(),authorityType);
    }
    public boolean validate(User user, ThemeGroup themeGroup, String authorityType){
        return false;
    }
    public boolean validate(Long userId, Long themeId, String authorityType){
        if(!checkNotNull(userId,themeId))
            return false;
        boolean result = false;
        switch (authorityType){
            case AUTHORITY_OPERATE:
                result = hdispatchAuthorityService.hasReadPermission(themeId, userId);
                break;
            case AUTHORITY_READ:
                result = hdispatchAuthorityService.hasOperatePermission(themeId,userId);
                break;
        }
        return result;
    }

    public boolean hasAllPermission(User user, Theme theme){
        return hasAllPermission(user.getUserId(),theme.getThemeId());
    }
    public boolean hasAllPermission(User user, ThemeGroup themeGroup){
        return false;
    }
    public boolean hasAllPermission(Long userId, Long themeId){
        if(!checkNotNull(userId,themeId))
            return false;

        return hdispatchAuthorityService.hasReadAndOperatePermission(themeId,userId);
    }

    private boolean checkNotNull(Long userId, Long themeId){
        if(null == userId || null == themeId){
            return false;
        }
        return true;
    }

    /**
     * 是否对主题有：操作权限
     * @param theme
     * @param user
     * @return
     */
    public boolean hasOperatePermission(Theme theme, User user) {
        return validate(user.getUserId(),theme.getThemeId(),AUTHORITY_OPERATE);
    }

    /**
     * 是否对主题有：读权限
     * @param theme
     * @param user
     * @return
     */
    public boolean hasReadPermission(Theme theme, User user) {
        return validate(user.getUserId(),theme.getThemeId(),AUTHORITY_READ);
    }
}
