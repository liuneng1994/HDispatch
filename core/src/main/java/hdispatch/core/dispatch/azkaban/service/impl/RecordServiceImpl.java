package hdispatch.core.dispatch.azkaban.service.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import hdispatch.core.dispatch.azkaban.entity.record.RECExecutionHistory;
import hdispatch.core.dispatch.azkaban.entity.record.RECExecutionSince;
import hdispatch.core.dispatch.azkaban.service.RecordService;
import hdispatch.core.dispatch.azkaban.util.RequestUrl;
import hdispatch.core.dispatch.azkaban.util.RequestUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用于获取与执行流相关的记录信息
 */
public class RecordServiceImpl implements RecordService {
    private Logger logger = Logger.getLogger(RecordServiceImpl.class);

    /**
     * 获取执行流中的job执行记录，记录列表从offset开始，最多length个字符
     * length如果小于1，将采用默认值：int的最大值
     */
    public List<String> fetchExecutionJobLogs(String execId, String jobId, int offset, int length) {
        HttpResponse<String> response;
        if (length < 1) {
            length = Integer.MAX_VALUE;
        }
        try {
            response = RequestUtils.get(RequestUrl.EXECUTOR)
                    .queryString("jobId", jobId)
                    .queryString("execid", execId)
                    .queryString("ajax", "fetchExecJobLogs")
                    .queryString("offset", offset)
                    .queryString("length", length)
                    .asString();
        } catch (UnirestException e) {
            logger.error("查询job执行历史列表失败", e);
            throw new IllegalStateException("查询job执行历史列表失败", e);
        }
        String responseData = response.getBody();
        //        logger.info(projectsHistory);
        return splitStringToList(responseData);
    }

    /**
     * 获取执行流中所有的job信息
     * (job执行的依赖关系存放在json的in字段中)
     */
    @Override
    public RECExecutionHistory fetchFlowExecution(String execId) {
        HttpResponse<String> response;
        try {
            response = RequestUtils.get(RequestUrl.EXECUTOR)
                    .queryString("execid", execId)
                    .queryString("ajax", "fetchexecflow")
                    .asString();
        } catch (UnirestException e) {
            logger.error("查询执行流中的job列表失败", e);
            throw new IllegalStateException("查询执行流中的job列表失败", e);
        }
        String responseData = response.getBody();


        //返回复杂的json数据
        RECExecutionHistory recExecutionHistory = new RECExecutionHistory();
        recExecutionHistory.setJsonObject(new JSONObject(responseData));
//        logger.info(responseData);

        return recExecutionHistory;
    }

    /**
     * 获取执行流在某个时刻之后的执行信息（自某个时刻之后被更新的执行流的信息）
     */
    @Override
    public RECExecutionSince fetchFlowExecInfoSince(String execId, Date date) {
        if (null == date) {
            return null;
        }
        return this.fetchFlowExecInfoSince(execId, date.getTime());
    }

    /**
     * 获取执行流在某个时刻之后的执行信息（自某个时刻之后被更新的执行流的信息）
     *
     * @param lastUpdateTime 毫秒数
     */
    @Override
    public RECExecutionSince fetchFlowExecInfoSince(String execId, long lastUpdateTime) {
        HttpResponse<String> response;
        try {
            response = RequestUtils.get(RequestUrl.EXECUTOR)
                    .queryString("execid", execId)
                    .queryString("ajax", "fetchexecflowupdate")
                    .queryString("lastUpdateTime", lastUpdateTime)
                    .asString();
        } catch (UnirestException e) {
            logger.error("查询" + lastUpdateTime + "时刻，流执行结果失败", e);
            throw new IllegalStateException("查询" + lastUpdateTime + "时刻，流执行结果失败", e);
        }
        String responseData = response.getBody();

        RECExecutionSince recExecution = new RECExecutionSince();
        recExecution.setJsonObject(new JSONObject(responseData));
        return recExecution;
    }

    private List<String> splitStringToList(String logMsg) {
        String startStr = "\"data\" : \"";
        int startIndex = logMsg.indexOf(startStr);
        logMsg = logMsg.substring(startIndex + startStr.length());
        int endIndex = logMsg.indexOf("\"");
        logMsg = logMsg.substring(0, endIndex);
        List<String> list = new ArrayList<String>();
        int index = 0;
        while (-1 != (index = logMsg.indexOf("\\n"))) {
            String temp = logMsg.substring(0, index);
            list.add(temp);
            logMsg = logMsg.substring(index + 2);
        }

        return list;
    }
}
