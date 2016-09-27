package hdispatch.core.dispatch.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import hdispatch.core.dispatch.dto.svn.SvnParameter;
import hdispatch.core.dispatch.mapper.SvnParameterMapper;
import hdispatch.core.dispatch.service.SvnParameterService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyz on 2016/9/24.
 *
 * @author yazheng.yang@hand-china.com
 */
@Service
public class SvnParameterServiceImpl implements SvnParameterService {
    private Logger logger = Logger.getLogger(SvnParameterServiceImpl.class);
    @Autowired
    private SvnParameterMapper svnParameterMapper;

    /**
     * 根据SvnParameter进行查询
     * @param requestContext
     * @param svnParameter
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List<SvnParameter> selectBySvnParameter(IRequest requestContext, SvnParameter svnParameter, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<SvnParameter> list;
        if (null == svnParameterMapper) {
            list = new ArrayList<SvnParameter>();
            logger.error("svnParameterMapper没有注入");
        } else {
            list = svnParameterMapper.selectBySvnParameter(svnParameter);
        }
        return list;
    }

    /**
     * 检查是否已经在数据库中存在
     * @param svnParameterList
     * @return
     */
    @Override
    public boolean[] checkIsExist(List<SvnParameter> svnParameterList) {
        boolean[] isExist = new boolean[svnParameterList.size()];
        int i = 0;
        for (SvnParameter svnParameter : svnParameterList) {
            List<SvnParameter> svnParameterListReturn = svnParameterMapper.selectForCheck(svnParameter);
            if (null != svnParameterListReturn && svnParameterListReturn.size() > 0) {
                isExist[i] = true;
            }
            i++;
        }

        return isExist;
    }

    /**
     * 批量操作：插入、删除、更新
     * @param requestContext
     * @param svnParameterList
     * @return
     */
    @Override
    public List<SvnParameter> batchUpdate(IRequest requestContext, List<SvnParameter> svnParameterList) {
        for (SvnParameter svnParameter : svnParameterList) {
            if (svnParameter.get__status() != null) {
                switch (svnParameter.get__status()) {
                    case DTOStatus.ADD:
                        svnParameterMapper.create(svnParameter);
                        break;
                    case DTOStatus.UPDATE:
                        svnParameterMapper.updateById(svnParameter);
                        break;
                    case DTOStatus.DELETE:
                        svnParameterMapper.deleteById(svnParameter);
                        break;
                    default:
                        break;
                }
            }
        }
        return svnParameterList;
    }
}
