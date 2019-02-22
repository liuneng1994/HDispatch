package hdispatch.core.dispatch.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.core.exception.UpdateFailedException;
import com.hand.hap.mybatis.common.Mapper;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.IBaseService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ETL调度系统使用的基本服务，已经为数据分源进行适配<br>
 *
 */
@Service
public abstract class HdispatchBaseServiceImpl<T> implements IBaseService<T> {
    @Autowired
    protected Mapper<T> mapper;

    @Override
    @Transactional(transactionManager = "hdispatchTM",propagation = Propagation.SUPPORTS)
    public List<T> select(IRequest request, T condition, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return mapper.select(condition);
    }

    @Override
    @Transactional(transactionManager = "hdispatchTM",rollbackFor = Exception.class)
    public T insert(IRequest request, T record) {
        BaseDTO baseDTO = (BaseDTO)record;
        baseDTO.setObjectVersionNumber(1L);
        mapper.insert(record);
        return record;
    }

    @Override
    @Transactional(transactionManager = "hdispatchTM",rollbackFor = Exception.class)
    public T insertSelective(IRequest request, T record) {
        BaseDTO baseDTO = (BaseDTO)record;
        baseDTO.setObjectVersionNumber(1L);
        mapper.insertSelective(record);
        return record;
    }

    @Override
    @Transactional(transactionManager = "hdispatchTM",rollbackFor = Exception.class)
    public T updateByPrimaryKey(IRequest request, @StdWho T record) {
        int ret = mapper.updateByPrimaryKey(record);
        checkOvn(ret, record);
        return record;
    }

    @Override
    @Transactional(transactionManager = "hdispatchTM",rollbackFor = Exception.class)
    public T updateByPrimaryKeySelective(IRequest request, @StdWho T record) {
        int ret = mapper.updateByPrimaryKeySelective(record);
        checkOvn(ret, record);
        return record;
    }

    @Override
    @Transactional(transactionManager = "hdispatchTM",propagation = Propagation.SUPPORTS)
    public T selectByPrimaryKey(IRequest request, T record) {
        return mapper.selectByPrimaryKey(record);
    }

    @Override
    @Transactional(transactionManager = "hdispatchTM",rollbackFor = Exception.class)
    public int deleteByPrimaryKey(T record) {
        int ret = mapper.deleteByPrimaryKey(record);
        checkOvn(ret, record);
        return ret;
    }

    /**
     * 检查乐观锁<br>
     * 检测到更新，删除失败时，抛出UpdateFailedException 异常
     *
     * @param updateCount
     *            update,delete 操作返回的值
     * @param record
     *            操作参数
     */
    protected void checkOvn(int updateCount, Object record) {
        if (updateCount == 0 && record instanceof BaseDTO) {
            BaseDTO baseDTO = (BaseDTO) record;
            if (baseDTO.getObjectVersionNumber() != null) {
                throw new RuntimeException(new UpdateFailedException(baseDTO));
            }
        }
    }

    @Override
    @Deprecated
    @Transactional(transactionManager = "hdispatchTM",propagation = Propagation.SUPPORTS)
    public List<T> selectAll() {
        return mapper.selectAll();
    }

    @Override
    @Transactional(transactionManager = "hdispatchTM",propagation = Propagation.SUPPORTS)
    public List<T> selectAll(IRequest request) {
        return mapper.selectAll();
    }

    /**
     * this method assume the object in list is BaseDTO.
     *
     * @param request
     *            requestContext
     * @param list
     *            dto list
     * @return the list
     */
    @Override
    @Transactional(transactionManager = "hdispatchTM",rollbackFor = Exception.class)
    public List<T> batchUpdate(IRequest request, List<T> list) {
        IBaseService<T> self = ((IBaseService<T>) AopContext.currentProxy());
        for (T t : list) {
            switch (((BaseDTO) t).get__status()) {
                case DTOStatus.ADD:
                    self.insertSelective(request, t);
                    break;
                case DTOStatus.UPDATE:
                    if (useSelectiveUpdate()) {
                        self.updateByPrimaryKeySelective(request, t);
                    } else {
                        self.updateByPrimaryKey(request, t);
                    }
                    break;
                case DTOStatus.DELETE:
                    self.deleteByPrimaryKey(t);
                    break;
                default:
                    break;
            }
        }
        return list;
    }

    /**
     * 默认 true,表示在 batchUpdate 中,更新操作,使用updateByPrimaryKeySelective(只更新不为 null
     * 的字段)。<br>
     * 若返回 false,则使用 updateByPrimaryKey(更新所有字段)
     *
     * @return
     */
    protected boolean useSelectiveUpdate() {
        return true;
    }

    @Override
    @Transactional(transactionManager = "hdispatchTM",rollbackFor = Exception.class)
    public int batchDelete(List<T> list) {
        IBaseService<T> self = ((IBaseService<T>) AopContext.currentProxy());
        int c = 0;
        for (T t : list) {
            c += self.deleteByPrimaryKey(t);
        }
        return c;
    }
}
