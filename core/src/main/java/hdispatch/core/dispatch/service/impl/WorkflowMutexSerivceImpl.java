package hdispatch.core.dispatch.service.impl;

import hdispatch.core.dispatch.dto.workflow.AzkabanFlowMutex;
import hdispatch.core.dispatch.dto.workflow.WorkflowMutex;
import hdispatch.core.dispatch.mapper.AzkabanProjectMapper;
import hdispatch.core.dispatch.mapper.WorkflowMutexMapper;
import hdispatch.core.dispatch.service.WorkflowMutexService;
import hdispatch.core.dispatch.utils.StringUtils;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by hasee on 2016/10/26.
 */
@Service
public class WorkflowMutexSerivceImpl implements WorkflowMutexService {
    @Autowired
    private AzkabanProjectMapper azkabanProjectMapper;
    @Autowired
    private WorkflowMutexMapper workflowMutexMapper;

    @Transactional
    @Override
    public List<WorkflowMutex> query(String projectName) {
        List<WorkflowMutex> mutexList = new ArrayList<>();
        if (!StringUtils.isEmpty(projectName)) {
            mutexList = workflowMutexMapper.queryMutex(projectName);
        }
        return mutexList;
    }

    @Transactional
    @Override
    public int batchInsert(List<AzkabanFlowMutex> mutexList) {
        if (mutexList.isEmpty()) return 0;
        mutexList.forEach(mutex -> {
            mutex.setMutexProjectId(azkabanProjectMapper.getIdByName(mutex.getMutexProjectName()));
            mutex.setProjectId(azkabanProjectMapper.getIdByName(mutex.getProjectName()));
        });
        List<WorkflowMutex> existingDependencies = query(mutexList.get(0).getProjectName());
        Set<Pair<String, String>> mutexSet = new HashSet<>();
        existingDependencies.forEach(item -> mutexSet.add(new Pair<>(item.getWorkflowName(), item.getMutexProjectName())));
        List<AzkabanFlowMutex> filteredDependencies =mutexList.stream().filter(item -> {
            return !mutexSet.contains(new Pair<>(item.getProjectName(), item.getMutexProjectName()));}
        ).collect(Collectors.toList());
        return workflowMutexMapper.batchInsert(filteredDependencies);
    }

    @Transactional
    @Override
    public int batchDelete(List<AzkabanFlowMutex> mutexList) {
        mutexList.forEach(mutex -> workflowMutexMapper.delete(mutex));
        return 0;
    }
}
