package hdispatch.core.dispatch.manager;

import hdispatch.core.dispatch.dto.workflow.WorkflowDependency;
import hdispatch.core.dispatch.mapper_hdispatch.WorkflowDependencyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.ObjectInputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static javafx.scene.input.KeyCode.M;

/**
 * Created by liuneng on 2016/12/6.
 */
@Component
public class DepencencyManager {
    private static final String DEPT_LIST_NAMESPACE = "hdispatch:deptlist:";
    private static final String DEPT_MAP_NAMESPACE = "hdispatch:deptmap:";

    @Autowired
    private WorkflowDependencyMapper dependencyMapper;
    private Map<String, Map<String, List<String>>> deptMap = new ConcurrentHashMap<>();
    private Map<String, List<String>> deptListMap = new ConcurrentHashMap<>();
    private ThreadLocal<Set<String>> trace = new ThreadLocal<>();
    private ThreadLocal<Set<String>> topTrace = new ThreadLocal<>();
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public Map<String, List<String>> getDeptGraph(String flowName) {
        Map<String, List<String>> nodeDept = deptMap.get(flowName);
        if (nodeDept == null) {
            nodeDept = buildGraph(flowName);
            deptMap.put(flowName, nodeDept);
//            addDeptMap(flowName,nodeDept);
        }
        return nodeDept;
    }

    private Map<String, List<String>> buildGraph(String flowName) {
        trace.set(new HashSet<>());
        topTrace.set(new HashSet<>());
        Map<String, List<String>> result = new HashMap<>();
        result.putAll(build1(flowName));
        result.putAll(build2(flowName));
        return result;
    }

    private Map<String, List<String>> build1(String flow) {
        Map<String, List<String>> result = new HashMap<>();
        if (topTrace.get().contains(flow)) return result;
        topTrace.get().add(flow);
        List<String> deptedList = dependencyMapper.queryDependencyed(flow).stream().map(WorkflowDependency::getWorkflowName).collect(Collectors.toList());
        deptedList.forEach(flowName -> {
            result.putAll(build1(flowName));
        });
        result.putAll(build2(flow));
        return result;
    }

    private Map<String, List<String>> build2(String flow) {
        Map<String, List<String>> result = new HashMap<>();
        if (trace.get().contains(flow)) return result;
        List<String> deptList = dependencyMapper.queryDependency(flow).stream().map(WorkflowDependency::getDeptWorkflowName).collect(Collectors.toList());
//        List<String> deptList = getDeptList(flow);
        trace.get().add(flow);
        result.put(flow, deptList);
        result.putAll(build1(flow));
        deptList.forEach(flowName -> {
            result.putAll(build2(flowName));
        });
        return result;
    }

    public void expire(String name) {
//        redisTemplate.delete(Arrays.asList(DEPT_LIST_NAMESPACE + name, DEPT_MAP_NAMESPACE + name));
        deptMap.remove(name);
        deptListMap.remove(name);
    }

    private Map<String, List<String>> getDeptMap(String name) {
        Map<String, List<String>> deptListMap = new HashMap<>();
        Map<Object, Object> deptMap = redisTemplate.boundHashOps(DEPT_MAP_NAMESPACE + name).entries();
        if (deptMap != null) {
            deptMap.forEach((key, value) -> {
                List<String> deptList = getDeptList((String) value);
                deptListMap.put((String) key, deptList);
            });
        } else {
            return null;
        }
        return deptListMap;
    }

    private void addDeptMap(String name, Map<String, List<String>> map) {
        redisTemplate.boundHashOps(DEPT_MAP_NAMESPACE + name).putAll(map);
    }

    private List<String> getDeptList(String name) {
        List<String> deptList = redisTemplate.boundListOps(DEPT_LIST_NAMESPACE + name).range(0, -1);
        if (deptList == null || deptList.isEmpty()) {
            deptList = dependencyMapper.queryDependency(name).stream().map(WorkflowDependency::getDeptWorkflowName).collect(Collectors.toList());
            deptList.forEach(value -> {
                redisTemplate.boundListOps(DEPT_LIST_NAMESPACE + name).leftPush(value);
            });
        }
        return deptList;
    }
}
