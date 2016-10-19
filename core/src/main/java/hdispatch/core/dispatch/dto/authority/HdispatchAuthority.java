package hdispatch.core.dispatch.dto.authority;

import com.hand.hap.system.dto.BaseDTO;

/**
 * Created by yyz on 2016/10/14.
 *
 * @author yazheng.yang@hand-china.com
 *
 * 存储权限信息
 */
public class HdispatchAuthority extends BaseDTO{
    private Long autorityId;
    private Long themeGroupThemeId;
    private Long userId;
    private String authRead;
    private String authOperate;

    public Long getAutorityId() {
        return autorityId;
    }

    public HdispatchAuthority setAutorityId(Long autorityId) {
        this.autorityId = autorityId;
        return this;
    }

    public Long getThemeGroupThemeId() {
        return themeGroupThemeId;
    }

    public HdispatchAuthority setThemeGroupThemeId(Long themeGroupThemeId) {
        this.themeGroupThemeId = themeGroupThemeId;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public HdispatchAuthority setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getAuthRead() {
        return authRead;
    }

    public HdispatchAuthority setAuthRead(String authRead) {
        this.authRead = authRead;
        return this;
    }

    public String getAuthOperate() {
        return authOperate;
    }

    public HdispatchAuthority setAuthOperate(String authOperate) {
        this.authOperate = authOperate;
        return this;
    }
}