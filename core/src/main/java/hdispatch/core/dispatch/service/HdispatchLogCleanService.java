package hdispatch.core.dispatch.service;

import java.util.Date;

/**
 * 清除Azkaban日志service
 */
public interface HdispatchLogCleanService {
    void cleanLogs();
    void cleanLogsBefore(Date beforeDate);
}
