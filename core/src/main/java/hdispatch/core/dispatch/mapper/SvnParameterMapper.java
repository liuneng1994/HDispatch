package hdispatch.core.dispatch.mapper;

import hdispatch.core.dispatch.dto.svn.SvnParameter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 任务运行时参数mapper接口<br>
 * Created by yyz on 2016/9/24.
 * @author yazheng.yang@hand-china.com
 */
public interface SvnParameterMapper {
    /**
     * 根据svnParameter对象进行查询
     * @param svnParameter
     * @return
     */
    List<SvnParameter> selectBySvnParameter(SvnParameter svnParameter);

//    /**
//     * 根据ID列表查询svnParameter
//     * @param ids
//     * @return
//     */
//    List<SvnParameter> getByIds(Collection<Long> ids);
    /**
     * 新建svnParameter
     * @param svnParameter
     */
    void create(SvnParameter svnParameter);

    /**
     * 删除svnParameter
     * @param svnParameter
     */
    void deleteById(SvnParameter svnParameter);
//    /**
//     * 删除svnParameter
//     * @param ids
//     */
//    void deleteByIds(Collection<Long> ids);

    /**
     * 根据id对svnParameter进行更新
     * @param svnParameter
     */
    void updateById(SvnParameter svnParameter);

    /**
     * 用于检测是否参数已经存在
     * 判定条件：subjectName,mappingName,sessionName,workFlowName,parameterName
     * @param svnParameter
     * @return
     */
    List<SvnParameter> selectForCheck(SvnParameter svnParameter);
    /**
     * 用于检测是否参数已经存在(2016.10.24需求变更，只需要检查SUBJECT_NAME、MAPPING_NAME、PARAMETER_NAME)
     * 判定条件：subjectName,mappingName,parameterName
     * @param svnParameter
     * @return
     */
    List<SvnParameter> selectForCheck_2(SvnParameter svnParameter);

    /**
     * 检查当前用户是否有操作任务运行时参数的权限<br>
     *     返回值非空且大于0表示有权限
     * @param themeGroupName
     * @return 返回值非空且大于0表示有权限
     */
    Long hasOperatePermission(@Param("themeGroupName") String themeGroupName);

    /**
     * 检查当前用户是否有读取任务运行时参数的权限<br>
     *     返回值非空且大于0表示有权限
     * @param themeGroupName
     * @return 返回值非空且大于0表示有权限
     */
    Long hasReadPermission(@Param("themeGroupName") String themeGroupName);
}
