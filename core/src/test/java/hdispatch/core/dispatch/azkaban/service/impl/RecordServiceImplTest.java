package hdispatch.core.dispatch.azkaban.service.impl;

import hdispatch.core.dispatch.azkaban.service.RecordService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

/**
 * RecordServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>九月 1, 2016</pre>
 */
public class RecordServiceImplTest {
    private RecordService recordServiceImpl = new RecordServiceImpl();

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: fetchAllProjectHistory()
     */
    @Test
    public void testFetchAllProjectHistory() throws Exception {
    }


    /**
     * Method: fetchExecutionJobLogs(String sessionId, String execId, String jobId, int offset, int length)
     */
    @Test
    public void testFetchExecutionJobLogs() throws Exception {
        String sessionId = "636aa0f2-22a1-4770-a012-ba60d8aee492";
        String execId = "26";
        String jobId = "foo";
        int offset = 0;
        int length = 300000;
        recordServiceImpl.fetchExecutionJobLogs(execId, jobId, offset, length);
    }

    /**
     * Method: fetchFlowExecutionUpdates(String sessionId, String execId, Date date)
     */
    @Test
    public void testFetchFlowExecutionUpdatesForSessionIdExecIdDate() throws Exception {
        String sessionId = "8abc927b-e671-4e6e-bead-41b560d3b1bf";
        String execId = "27";
        recordServiceImpl.fetchFlowExecution(execId);
    }

    /**
     * Method: fetchFlowExecutionUpdates(String sessionId, String execId, long lastUpdateTime)
     */
    @Test
    public void testFetchFlowExecutionUpdatesForSessionIdExecIdLastUpdateTime() throws Exception {
        String sessionId = "8abc927b-e671-4e6e-bead-41b560d3b1bf";
        String execId = "26";
        recordServiceImpl.fetchFlowExecInfoSince(execId, new Date(10));
    }
}
