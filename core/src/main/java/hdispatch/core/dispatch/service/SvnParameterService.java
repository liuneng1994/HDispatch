package hdispatch.core.dispatch.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;
import hdispatch.core.dispatch.dto.svn.SvnParameter;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 任务运行时参数service接口<br>
 */
public interface SvnParameterService extends IBaseService<SvnParameter>, ProxySelf<SvnParameterService> {
    /**
     * 根据SvnParameter进行模糊查询
     */
    List<SvnParameter> selectBySvnParameter(IRequest requestContext, SvnParameter svnParameter, int page, int pageSize);
    /**
     * 检查是否已经在数据库中存在
     */
    boolean[] checkIsExist(List<SvnParameter> svnParameterList);

    /**
     * 批量操作：插入、删除、更新
     */
    List<SvnParameter> batchUpdate(IRequest requestContext, @StdWho List<SvnParameter> svnParameterList, Map<String,String> feedbackMsg);

    /**
     * 从excel文件中导入数据，批量创建 SVN参数
     * @param excelFiles
     * @return
     */
    List<SvnParameter> batchCreateFromExcel(IRequest requestContext,CommonsMultipartFile[] excelFiles, Map<String,String> feedbackMsg) throws Exception;

    /**
     * 根据subjectName、mappingName、parameterName判断是否已经存在,
     * 如果已经存在，那个直接将add状态变为update状态
     * @param svnParameterList
     * @return
     */
    void preAddHandle(List<SvnParameter> svnParameterList);

    /**
     * 判断当前用户是否有操作任务运行时参数的权限<br>
     * @param requestContext
     * @return
     */
    boolean hasOperatePermission(IRequest requestContext);

    /**
     * 判断当前用户是否有读取任务运行时参数的权限<br>
     * @param requestContext
     * @return
     */
    boolean hasReadPermission(IRequest requestContext);

    /**
     * 根据subjectName、mappingName、parameterName判断是否已经存在,
     * @param svnParameter
     * @return
     */
    boolean isExistParameter(SvnParameter svnParameter);
}
