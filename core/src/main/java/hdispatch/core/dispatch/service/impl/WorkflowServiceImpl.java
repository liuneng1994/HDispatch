package hdispatch.core.dispatch.service.impl;

import com.github.pagehelper.PageHelper;
import hdispatch.core.dispatch.azkaban.service.ProjectService;
import hdispatch.core.dispatch.dto.job.Job;
import hdispatch.core.dispatch.dto.workflow.SimpleWorkflow;
import hdispatch.core.dispatch.dto.workflow.Workflow;
import hdispatch.core.dispatch.dto.workflow.WorkflowJob;
import hdispatch.core.dispatch.exception.CircularReferenceException;
import hdispatch.core.dispatch.mapper.JobMapper;
import hdispatch.core.dispatch.mapper.WorkflowJobMapper;
import hdispatch.core.dispatch.mapper.WorkflowMapper;
import hdispatch.core.dispatch.mapper.WorkflowPropertyMapper;
import hdispatch.core.dispatch.service.WorkflowService;
import hdispatch.core.dispatch.utils.WorkflowUtils;
import hdispatch.core.dispatch.utils.ZipUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static hdispatch.core.dispatch.utils.Constants.RET_ERROR;
import static hdispatch.core.dispatch.utils.Constants.RET_SUCCESS;

/**
 * Created by 刘能 on 2016/9/12.
 */

/**
 * 工作流服务类
 *
 * @author neng.liu@hand-china.com
 */
@Service("workflowService")
public class WorkflowServiceImpl implements WorkflowService {
    private Logger logger = LoggerFactory.getLogger(WorkflowServiceImpl.class);
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
        logger.info("Creates workflow {}", workflow.toString());
        Assert.notNull(workflow, "Workflow can not be null");
        Map<String, Object> ret = new HashMap<>();
        if (workflowMapper.getByName(workflow.getName()) != null) {
            ret.put(RET_ERROR, String.format("Workflow %s exists", workflow.getName()));
        } else {
            workflowMapper.create(workflow);
            Long id = workflow.getWorkflowId();
            if (workflow.getProperties() != null) {
                workflow.getProperties().forEach(workflowProperty -> workflowProperty.setWorkflowId(id));
            }
            if (workflow.getJobs() != null) {
                workflow.getJobs().forEach(workflowJob -> workflowJob.setWorkflowId(id));
            }
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
            workflowMapper.update(workflow);
            ret.put(RET_SUCCESS, String.format("Workflow %d update sucess", workflow.getWorkflowId()));
        }
        return ret;
    }

    @Override
    @Transactional
    public boolean generateWorkflow(long workflowId) {
        Workflow workflow = workflowMapper.getById(workflowId);
        if (workflow == null || workflow.getJobs() == null) return false;
        logger.info("generate workflow " + workflow);
        Set<Long> ids = new HashSet<>();
        workflow.getJobs().forEach(job -> ids.add(job.getJobSource()));
        WorkflowResolver resolver = new WorkflowResolver();
        List<Job> jobStore = jobMapper.getByIds(resolver.resolveWorkflowJobs(workflow));
        logger.info("job source " + jobStore);
        File projectFile = generateWorkflowFile(workflow, jobStore);
        projectService.createProject(workflow.getName(), workflow.getDescription());
        Map<String, String> result = projectService.uploadProjectFile(workflow.getName(), projectFile);
        if (!result.containsKey("error")) {
            workflowMapper.updateProjectNameAndFlowIdById(workflowId, workflow.getName(), workflow.getName());
        }
        return !result.containsKey("error");
    }

    private class WorkflowResolver {
        private Set<String> parsedFlow = new HashSet<>();

        private Set<Long> resolveWorkflowJobs(Workflow workflow) {
            Set<Long> jobs = new HashSet<>();
            if (workflow.getJobs() != null)
                workflow.getJobs().forEach(workflowJob -> {
                    switch (workflowJob.getJobType()) {
                        case "job":
                            jobs.add(workflowJob.getJobSource());
                            break;
                        case "flow":
//                            if (parsedFlow.contains(workflowJob.getWorkflowJobId())) {
//                                throw new CircularReferenceException(String.format("Flow %s has circular reference", workflowJob.getWorkflowJobId()));
//                            }
                            parsedFlow.add(workflowJob.getWorkflowJobId());
                            jobs.addAll(resolveWorkflowJobs(workflowMapper.getById(workflowJob.getJobSource())));
                            break;
                    }
                });
            return jobs;
        }
    }

    @Override
    @Transactional
    public Workflow getWorkflowById(long workflowId) {
        return workflowMapper.getById(workflowId);
    }

    @Override
    @Transactional
    public Workflow getWorkflowByName(String name) {
        return workflowMapper.getByName(name);
    }

    @Override
    @Transactional
    public boolean saveGraph(long workflowId, String graph) {
        if (StringUtils.isEmpty(graph)) {
            return false;
        }
        int result = workflowMapper.saveGraph(workflowId, graph);
        return result > 0;
    }

    @Override
    @Transactional
    public String getGraph(long workflowId) {
        return workflowMapper.getGraph(workflowId);
    }

    @Override
    @Transactional
    public List<SimpleWorkflow> queryWorkflow(Long themeId, Long layerId, String workflowName, String decription, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return workflowMapper.query(themeId, layerId, workflowName, decription);
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
        FileUtils.deleteQuietly(projectZipFile);
        projectDir.mkdir();
        workflow.getJobs().forEach(job -> {
            switch (job.getJobType()) {
                case "job":
                    WorkflowUtils.createJobFile(projectDir, new ArrayList<>(), job, jobStore);
                    break;
                case "flow":
                    generateEmbededFlowFile(projectDir, new ArrayList<>(), job, jobStore);
                    break;
            }
        });
        WorkflowUtils.createEndJobFile(projectDir, new ArrayList<>(), workflow);
        try {
            ZipUtils.zip(projectDir, projectZipFile);
        } catch (IOException e) {
            logger.error("generate workflow file failed");
            throw new RuntimeException(e);
        }
        FileUtils.deleteQuietly(projectDir);
        return projectZipFile;
    }

    private void generateEmbededFlowFile(File parentFile, List<String> parentWorkflow, WorkflowJob job, Collection<Job> jobStore) {
        Workflow workflow = workflowMapper.getById(job.getJobSource());
        WorkflowUtils.createFlowFile(parentFile, parentWorkflow, job.getWorkflowJobId() +"."+workflow.getName(), job, jobStore);
        List<String> parentWorkflowCloning = new ArrayList<>();
        parentWorkflowCloning.addAll(parentWorkflow);
        parentWorkflowCloning.add(job.getWorkflowJobId());
        workflow.getJobs().forEach(workflowJob -> {
            switch (workflowJob.getJobType()) {
                case "job":
                    WorkflowUtils.createJobFile(parentFile, parentWorkflowCloning, workflowJob, jobStore);
                    break;
                case "flow":
                    generateEmbededFlowFile(parentFile, parentWorkflowCloning, workflowJob, jobStore);
                    break;
            }
        });
        WorkflowUtils.createEndJobFile(parentFile, parentWorkflowCloning, workflow);
    }
}
