package hdispatch.core.dispatch.service;

import java.util.Date;

/**
 * 清除Azkaban日志service
 * Created by yyz on 2017/1/11.
 *
 * @author yazheng.yang@hand-china.com
 */
public interface HdispatchLogCleanService {
    void cleanLogs();
    void cleanLogsBefore(Date beforeDate);
}
