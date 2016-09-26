package hdispatch.core.dispatch.service;

import com.hand.hap.core.IRequest;
import hdispatch.core.dispatch.dto.svn.SvnParameter;

import java.util.List;

/**
 * Created by yyz on 2016/9/24.
 *
 * @author yazheng.yang@hand-china.com
 *
 * SVN参数-服务接口
 */
public interface SvnParameterService {
    /**
     * 根据SvnParameter进行查询
     */
    List<SvnParameter> selectBySvnParameter(IRequest requestContext, SvnParameter svnParameter, int page, int pageSize);
    /**
     * 检查是否已经在数据库中存在
     */
    boolean[] checkIsExist(List<SvnParameter> svnParameterList);

    /**
     * 批量操作：插入、删除、更新
     */
    List<SvnParameter> batchUpdate(IRequest requestContext, List<SvnParameter> svnParameterList);
}
