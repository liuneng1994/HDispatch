package hdispatch.core.dispatch.utils;

import hdispatch.core.dispatch.azkaban.util.RequestUtils;
import hdispatch.core.dispatch.dto.job.Job;
import hdispatch.core.dispatch.dto.workflow.Workflow;
import hdispatch.core.dispatch.dto.workflow.WorkflowJob;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by 刘能 on 2016/9/12.
 */
public class WorkflowUtils {
    private static String COMMAND_TEMPLATE;
    private static final String JOB_SUFFIX = ".job";
    private static final String FLOW_PREFIX = "_";

    static {
        Properties properties = new Properties();
        try {
            properties.load(RequestUtils.class.getClassLoader().getResourceAsStream("config.properties"));
            COMMAND_TEMPLATE = properties.getProperty("workflow.command.template", "%s");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建job文件
     *
     * @param parentFile 父目录
     * @param job
     * @param jobStore   job源
     */
    public static void createJobFile(File parentFile, List<String> parentWorkflow, WorkflowJob job, Collection<Job> jobStore) {

        File file = new File(parentFile, parentWorkflow.isEmpty() ? job.getWorkflowJobId() + JOB_SUFFIX : String.join(".", parentWorkflow) + "." + job.getWorkflowJobId() + JOB_SUFFIX);
        try {
            file.createNewFile();
            Job jobSource = jobStore.stream()
                    .filter(item -> item.getJobId() == job.getJobSource()).findFirst().get();
            List<String> depts = Arrays.asList(job.getParentsJobId().split(","));
            List<String> newDepts = new ArrayList<>();
            depts.forEach(dept -> {
                if (Pattern.matches("\\s*",dept)) return;
                String deptName = parentWorkflow.isEmpty() ? dept : String.join(".", parentWorkflow) + "." + dept;
                newDepts.add(deptName);
            });
            FileUtils.writeLines(file, generateJobContent(jobSource, String.join(",", newDepts)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建flow文件.
     *
     * @param parentFile
     * @param job
     * @param jobStore
     */
    public static void createFlowFile(File parentFile, List<String> parentWorkflow, String flowName, WorkflowJob job, Collection<Job> jobStore) {
        File file = new File(parentFile, parentWorkflow.isEmpty() ? job.getWorkflowJobId() + JOB_SUFFIX : String.join(".", parentWorkflow) + "." + job.getWorkflowJobId() + JOB_SUFFIX);
        try {
            file.createNewFile();
            List<String> depts = Arrays.asList(job.getParentsJobId().split(","));
            List<String> newDepts = new ArrayList<>();
            depts.forEach(dept -> {
                if (Pattern.matches("\\s*",dept)) return;
                String namespace = parentWorkflow.isEmpty() ? "" : String.join(".", parentWorkflow) + ".";
                newDepts.add(namespace + dept);
            });
            flowName = parentWorkflow.isEmpty() ? FLOW_PREFIX + flowName : FLOW_PREFIX + String.join(".", parentWorkflow) + "." + flowName;
            FileUtils.writeLines(file, generateFlowContent(flowName, String.join(",", newDepts)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createEndJobFile(File parentFile, List<String> parentWorkflow, Workflow workflow) {
        String fileName = parentWorkflow.isEmpty() ? workflow.getName() : String.join(".", parentWorkflow) + "." + workflow.getName();
        File file = new File(parentFile, FLOW_PREFIX + fileName + JOB_SUFFIX);
        Set<String> wholeJobs = new HashSet<>();
        Set<String> jobsWithDept = new HashSet<>();
        workflow.getJobs().forEach(job -> wholeJobs.add(job.getWorkflowJobId()));
        workflow.getJobs().forEach(job -> {
            if (job.getParentsJobId() != null) {
                Arrays.asList(job.getParentsJobId().split(",")).forEach(JobName -> {
                    jobsWithDept.add(JobName);
                });
            }
        });
        wholeJobs.removeAll(jobsWithDept);
        List<String> content = new ArrayList<>();
        content.add("type=command");
        content.add("command=echo \"flow run success\"");
        List<String> newJobs = new ArrayList<>();
        wholeJobs.forEach(job -> {
            newJobs.add((parentWorkflow.isEmpty() ? "" : String.join(".", parentWorkflow) + ".") + job);
        });
        if (!newJobs.isEmpty()) {
            content.add("dependencies=" + String.join(",", newJobs));
        }
        try {
            FileUtils.writeLines(file, content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 产生job文件内容
     *
     * @param job
     * @param dependencies
     * @return
     */
    private static List<String> generateJobContent(Job job, String dependencies) {
        List<String> contents = new ArrayList<>();
        contents.add("type=command");
        contents.add("command=" + String.format(COMMAND_TEMPLATE, job.getJobSvn()));
        if (!StringUtils.isEmpty(dependencies)) {
            contents.add("dependencies=" + dependencies);
        }
        return contents;
    }

    private static List<String> generateFlowContent(String flowName, String dependencies) {
        List<String> contents = new ArrayList<>();
        contents.add("type=flow");
        contents.add("flow.name=" + flowName);
        if (!StringUtils.isEmpty(dependencies)) {
            contents.add("dependencies=" + dependencies);
        }
        return contents;
    }
}
