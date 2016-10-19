package hdispatch.core.dispatch.service;

import com.hand.hap.core.IRequest;
import hdispatch.core.dispatch.dto.authority.ThemeGroup;

import java.util.List;

/**
 * Created by yyz on 2016/10/17.
 *
 * @author yazheng.yang@hand-china.com
 */
public interface ThemeGroupService {
    List<ThemeGroup> selectByThemeGroup(IRequest requestContext, ThemeGroup themeGroup, int page, int pageSize);

    List<ThemeGroup> batchUpdate(IRequest requestContext, List<ThemeGroup> themeGroupList);
}
