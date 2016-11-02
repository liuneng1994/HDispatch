package hdispatch.core.dispatch.dto.authority;

import com.hand.hap.system.dto.BaseDTO;

/**
 * 存储权限信息dto<br>
 * Created by yyz on 2016/10/14.
 * @author yazheng.yang@hand-china.com
 */
public class HdispatchAuthority extends BaseDTO{
    private Long authorityId;
    private Long themeGroupId;
    private Long userId;
    private String authRead;
    private String authOperate;

    //冗余字段
    private String userName;

    public Long getAuthorityId() {
        return authorityId;
    }

    public HdispatchAuthority setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
        return this;
    }

    public Long getThemeGroupId() {
        return themeGroupId;
    }

    public HdispatchAuthority setThemeGroupId(Long themeGroupId) {
        this.themeGroupId = themeGroupId;
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

    public String getUserName() {
        return userName;
    }

    public HdispatchAuthority setUserName(String userName) {
        this.userName = userName;
        return this;
    }
}