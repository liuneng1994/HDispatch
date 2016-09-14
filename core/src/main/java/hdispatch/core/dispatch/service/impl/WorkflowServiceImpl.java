package hdispatch.core.dispatch.service.impl;

import hdispatch.core.dispatch.azkaban.service.ProjectService;
import hdispatch.core.dispatch.dto.job.Job;
import hdispatch.core.dispatch.dto.workflow.Workflow;
import hdispatch.core.dispatch.mapper.JobMapper;
import hdispatch.core.dispatch.mapper.WorkflowJobMapper;
import hdispatch.core.dispatch.mapper.WorkflowMapper;
import hdispatch.core.dispatch.mapper.WorkflowPropertyMapper;
import hdispatch.core.dispatch.service.WorkflowService;
import hdispatch.core.dispatch.utils.WorkflowUtils;
import hdispatch.core.dispatch.utils.ZipUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static hdispatch.core.dispatch.utils.Constants.RET_ERROR;
import static hdispatch.core.dispatch.utils.Constants.RET_SUCCESS;

/**
 * Created by 刘能 on 2016/9/12.
 */
@Service("workflowService")
public class WorkflowServiceImpl implements WorkflowService {
    private Logger logger = Logger.getLogger(WorkflowServiceImpl.class);
    @Autowired
    private WorkflowMapper workflowMapper;
    @Autowired
    private WorkflowPropertyMapper workflowPropertyMapper;
    @Autowired
    private WorkflowJobMapper workflowJobMapper;
    @Autowired
    private JobMapper jobMapper;
    @Autowired
    private ProjectService projectService;

    /**
     * 创建一个新的工作流，工作流中包含属性和Job.工作流的名称是唯一的。
     *
     * @param workflow 工作流对象，不能为null
     * @return 结果信息
     */
    @Override
    @Transactional
    public Map<String, Object> createWorkflow(Workflow workflow) {
        logger.info("Creates workflow " + workflow);
        Assert.notNull(workflow, "Workflow can not be null");
        Map<String, Object> ret = new HashMap<>();
        if (workflowMapper.getByName(workflow.getName()) != null) {
            ret.put(RET_ERROR, String.format("Workflow %s exists", workflow.getName()));
        } else {
            workflowMapper.create(workflow);
            Long id = workflow.getWorkflowId();
            workflow.getProperties().forEach(workflowProperty -> workflowProperty.setWorkflowId(id));
            workflow.getJobs().forEach(workflowJob -> workflowJob.setWorkflowId(id));
            if (workflow.getProperties() != null && !workflow.getProperties().isEmpty()) {
                workflowPropertyMapper.batchInsert(workflow.getProperties());
            }
            if (workflow.getJobs() != null && !workflow.getJobs().isEmpty()) {
                workflowJobMapper.batchInsert(workflow.getJobs());
            }
            ret.put(RET_SUCCESS, String.valueOf(id));
        }
        return ret;
    }

    /**
     * 更新工作流，工作流必须存在，通过id查找。
     *
     * @param workflow 工作流对象，不能为null
     * @return 结果信息
     */
    @Override
    @Transactional
    public Map<String, Object> updateWorkFlow(Workflow workflow) {
        logger.info("update workflow " + workflow);
        Assert.notNull(workflow, "Workflow can not be null");
        Map<String, Object> ret = new HashMap<>();
        if (workflowMapper.getById(workflow.getWorkflowId()) == null) {
            ret.put(RET_ERROR, String.format("Workflow %d not exists", workflow.getWorkflowId()));
        } else {
            workflowPropertyMapper.deleteByWorkflowId(workflow.getWorkflowId());
            workflowJobMapper.deleteByWorkflowId(workflow.getWorkflowId());
            if (workflow.getProperties() != null) {
                workflowPropertyMapper.batchInsert(workflow.getProperties());
            }
            if (workflow.getJobs() != null) {
                workflowJobMapper.batchInsert(workflow.getJobs());
            }
            ret.put(RET_SUCCESS, String.format("Workflow %d update sucess", workflow.getWorkflowId()));
        }
        return ret;
    }

    @Override
    @Transactional
    public boolean generateWorkflow(long workflowId) {
        Workflow workflow = workflowMapper.getById(workflowId);
        logger.info("generate workflow " + workflow);
        Set<Long> ids = new HashSet<>();
        workflow.getJobs().forEach(job -> ids.add(job.getJobSource()));
        List<Job> jobStore = jobMapper.getByIds(ids);
        logger.info("job source " + jobStore);
        File projectFile = generateWorkflowFile(workflow, jobStore);
        projectService.createProject(workflow.getName(), "");
        Map<String, String> result = projectService.uploadProjectFile(workflow.getName(), projectFile);
        workflowMapper.updateProjectNameAndFlowIdById(workflowId, workflow.getName(), workflow.getName());
        return !result.containsKey("error");
    }

    /**
     * 创建工作流的zip压缩文件
     *
     * @param workflow 工作流对象,不能为空
     * @param jobStore Job源列表
     * @return 压缩文件对象
     */
    private File generateWorkflowFile(Workflow workflow, Collection<Job> jobStore) {
        File tempDir = FileUtils.getTempDirectory();
        File projectDir = new File(tempDir, workflow.getName());
        File projectZipFile = new File(tempDir, workflow.getName() + ".zip");
        FileUtils.deleteQuietly(projectDir);
        projectDir.mkdir();
        workflow.getJobs().forEach(job -> WorkflowUtils.createJobFile(projectDir, job, jobStore));
        WorkflowUtils.createEndJobFile(projectDir, workflow);
        try {
            ZipUtils.zip(projectDir, projectZipFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return projectZipFile;
    }
}
