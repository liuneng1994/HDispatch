package hdispatch.core.dispatch.mapper;

import hdispatch.core.dispatch.dto.svn.SvnParameter;

import java.util.Collection;
import java.util.List;

/**
 * Created by yyz on 2016/9/24.
 *
 * @author yazheng.yang@hand-china.com
 */
public interface SvnParameterMapper {
    /**
     * 根据svnParameter对象进行查询
     * @param svnParameter
     * @return
     */
    List<SvnParameter> selectBySvnParameter(SvnParameter svnParameter);

    /**
     * 根据ID列表查询svnParameter
     * @param ids
     * @return
     */
    List<SvnParameter> getByIds(Collection<Long> ids);
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
    /**
     * 删除svnParameter
     * @param ids
     */
    void deleteByIds(Collection<Long> ids);

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
}
