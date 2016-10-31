package hdispatch.core.dispatch.service.impl;

import hdispatch.core.dispatch.dto.workflow.AzkabanFlowDependency;
import hdispatch.core.dispatch.dto.workflow.WorkflowDependency;
import hdispatch.core.dispatch.mapper.AzkabanProjectMapper;
import hdispatch.core.dispatch.mapper.WorkflowDependencyMapper;
import hdispatch.core.dispatch.service.WorkflowDependencyService;
import hdispatch.core.dispatch.utils.StringUtils;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by hasee on 2016/10/26.
 */
@Service
public class WorkflowDependencySerivceImpl implements WorkflowDependencyService {
    @Autowired
    private AzkabanProjectMapper azkabanProjectMapper;
    @Autowired
    private WorkflowDependencyMapper workflowDependencyMapper;

    @Override
    public List<WorkflowDependency> query(String projectName) {
        List<WorkflowDependency> dependencies = new ArrayList<>();
        if (!StringUtils.isEmpty(projectName)) {
            dependencies = workflowDependencyMapper.queryDependency(projectName);
        }
        return dependencies;
    }

    @Override
    public int batchInsert(List<AzkabanFlowDependency> dependencies) {
        if (dependencies.isEmpty()) return 0;
        dependencies.forEach(dependency -> {
            dependency.setDeptProjectId(azkabanProjectMapper.getIdByName(dependency.getDeptProjectName()));
            dependency.setProjectId(azkabanProjectMapper.getIdByName(dependency.getProjectName()));
        });
        List<WorkflowDependency> existingDependencies = query(dependencies.get(0).getProjectName());
        Set<Pair<String, String>> deptSet = new HashSet<>();
        existingDependencies.forEach(item -> deptSet.add(new Pair<>(item.getWorkflowName(), item.getDeptProjectName())));
        List<AzkabanFlowDependency> filteredDependencies =dependencies.stream().filter(item -> {
            return !deptSet.contains(new Pair<>(item.getProjectName(), item.getDeptProjectName()));}
        ).collect(Collectors.toList());
        return workflowDependencyMapper.batchInsert(filteredDependencies);
    }

    @Override
    public int batchDelete(List<AzkabanFlowDependency> dependencies) {
        dependencies.forEach(dependency -> workflowDependencyMapper.delete(dependency));
        return 0;
    }
}