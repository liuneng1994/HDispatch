package hdispatch.core.dispatch.utils;

import hdispatch.core.dispatch.dto.job.Job;
import hdispatch.core.dispatch.dto.workflow.Workflow;
import hdispatch.core.dispatch.dto.workflow.WorkflowJob;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by 刘能 on 2016/9/12.
 */
public class WorkflowUtils {
    private static String COMMAND_TEMPLATE = "%s";
    private static final String JOB_SUFFIX = ".job";

    /**
     * 创建job文件
     *
     * @param parentFile 父目录
     * @param job
     * @param jobStore   job源
     */
    public static void createJobFile(File parentFile, WorkflowJob job, Collection<Job> jobStore) {
        File file = new File(parentFile, job.getWorkflowJobId() + JOB_SUFFIX);
        FileUtils.deleteQuietly(file);
        try {
            file.createNewFile();
            Job jobSource = jobStore.stream()
                    .filter(item -> item.getJobId() == job.getJobSource()).findFirst().get();
            FileUtils.writeLines(file, generateJobContent(jobSource, job.getParentsJobId()));
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

    public static void createEndJobFile(File parentFile, Workflow workflow) {
        File file = new File(parentFile, workflow.getName() + JOB_SUFFIX);
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
        if (!wholeJobs.isEmpty()) {
            content.add("dependencies=" + String.join(",", wholeJobs));
        }
        try {
            FileUtils.writeLines(file, content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}