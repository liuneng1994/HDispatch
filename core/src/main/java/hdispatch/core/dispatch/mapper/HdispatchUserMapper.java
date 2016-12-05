package hdispatch.core.dispatch.mapper;

import hdispatch.core.dispatch.dto.authority.HdispatchAuthority;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 任务调度系统中查询在主题组和不在主题组的用户<br>
 * Created by yyz on 2016/11/22.
 * @author yazheng.yang@hand-china.com
 */
public interface HdispatchUserMapper {
    /**
     * 模糊查询用户表，抽取出用户id用于再次从权限关系表中查询已经分配权限的用户对应的权限列表
     * @param userId 用户id，可以为空
     * @param userName 模糊查询的用户名，可以为空
     * @return
     */
    List<HdispatchAuthority> selectUser(@Param("userId") Long userId, @Param("userName") String userName);

    /**
     * 模糊查询用户表，根据已经在权限表中存在的用户id列表，查询待分配权限的用户列表
     * @param userIdCollection 已经在权限表中存在的用户id列表，可以是一个Set对象
     * @param userName 模糊查询的用户名
     * @return
     */
    List<HdispatchAuthority> selectNotInThemeGroup(@Param("userIdCollection") Collection<Long> userIdCollection, @Param("userName") String userName);
}
