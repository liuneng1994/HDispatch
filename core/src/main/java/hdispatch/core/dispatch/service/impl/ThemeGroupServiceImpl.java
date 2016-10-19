package hdispatch.core.dispatch.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import hdispatch.core.dispatch.dto.authority.ThemeGroup;
import hdispatch.core.dispatch.mapper.ThemeGroupMapper;
import hdispatch.core.dispatch.service.ThemeGroupService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyz on 2016/10/17.
 *
 * @author yazheng.yang@hand-china.com
 */
@Service
public class ThemeGroupServiceImpl implements ThemeGroupService {
    private Logger logger = Logger.getLogger(ThemeGroupServiceImpl.class);
    @Autowired
    private ThemeGroupMapper themeGroupMapper;


    @Override
    public List<ThemeGroup> selectByThemeGroup(IRequest requestContext, ThemeGroup themeGroup, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<ThemeGroup> result = themeGroupMapper.selectByThemeGroup(themeGroup);
        return result;
    }

    @Override
    public List<ThemeGroup> batchUpdate(IRequest requestContext, List<ThemeGroup> themeGroupList) {
        for (ThemeGroup themeGroup : themeGroupList) {
            if (themeGroup.get__status() != null) {
                switch (themeGroup.get__status()) {
                    case DTOStatus.ADD:
                        themeGroupMapper.save(themeGroup);
                        break;
                    case DTOStatus.UPDATE:
                        themeGroupMapper.updateById(themeGroup);
                        break;
                    case DTOStatus.DELETE:

                        break;
                    default:
                        break;
                }
            }
        }
        return themeGroupList;
    }
}
