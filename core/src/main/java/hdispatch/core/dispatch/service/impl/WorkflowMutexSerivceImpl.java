package hdispatch.core.dispatch.service.impl;

import hdispatch.core.dispatch.dto.workflow.AzkabanFlowMutex;
import hdispatch.core.dispatch.dto.workflow.WorkflowMutex;
import hdispatch.core.dispatch.mapper_hdispatch.AzkabanProjectMapper;
import hdispatch.core.dispatch.mapper_hdispatch.WorkflowMutexMapper;
import hdispatch.core.dispatch.service.WorkflowMutexService;
import hdispatch.core.dispatch.utils.StringUtils;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by liuneng on 2016/10/26.
 */
@Service
public class WorkflowMutexSerivceImpl implements WorkflowMutexService {
    @Autowired
    private AzkabanProjectMapper azkabanProjectMapper;
    @Autowired
    private WorkflowMutexMapper workflowMutexMapper;

    @Transactional("hdispatchTM")
    @Override
    public List<WorkflowMutex> query(String projectName) {
        List<WorkflowMutex> mutexList = new ArrayList<>();
        if (!StringUtils.isEmpty(projectName)) {
            mutexList = workflowMutexMapper.queryMutex(projectName);
        }
        return mutexList;
    }

    @Transactional("hdispatchTM")
    @Override
    public int batchInsert(List<AzkabanFlowMutex> mutexList) {
        if (mutexList.isEmpty()) return 0;
        mutexList.forEach(mutex -> {
            mutex.setMutexProjectId(azkabanProjectMapper.getIdByName(mutex.getMutexProjectName()));
            mutex.setProjectId(azkabanProjectMapper.getIdByName(mutex.getProjectName()));
        });
        List<WorkflowMutex> existingMutexes = query(mutexList.get(0).getProjectName());
        Set<Pair<String, String>> mutexSet = new HashSet<>();
        existingMutexes.forEach(item -> mutexSet.add(new Pair<>(item.getWorkflowName(), item.getMutexProjectName())));
        List<AzkabanFlowMutex> filteredMutexes = mutexList.stream().filter(item -> {
                    return !mutexSet.contains(new Pair<>(item.getProjectName(), item.getMutexProjectName()));
                }
        ).collect(Collectors.toList());
        workflowMutexMapper.batchInsert(filteredMutexes);
        List<AzkabanFlowMutex> reverseMutexList = filteredMutexes.parallelStream().map(item -> {
            AzkabanFlowMutex mutex = new AzkabanFlowMutex();
            mutex.setProjectId(item.getMutexProjectId()).setFlowId(item.getMutexFlowId()).setProjectName(item.getMutexProjectName())
                    .setMutexProjectId(item.getProjectId()).setMutexFlowId(item.getFlowId()).setMutexProjectName(item.getProjectName());
            return mutex;
        }).collect(Collectors.toList());
        reverseMutexList.forEach(item -> workflowMutexMapper.batchInsert(Arrays.asList(item)));
        return 0;
    }

    @Transactional("hdispatchTM")
    @Override
    public int batchDelete(List<AzkabanFlowMutex> mutexList) {
        mutexList.forEach(mutex -> {
            workflowMutexMapper.delete(mutex);
            workflowMutexMapper.delete(new AzkabanFlowMutex().setProjectName(mutex.getMutexProjectName())
                    .setMutexProjectName(mutex.getProjectName()));
        });
        return 0;
    }
}
