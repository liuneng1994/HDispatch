package hdispatch.core.dispatch.manager;

import hdispatch.core.dispatch.cache.String2ObjectCache;
import hdispatch.core.dispatch.dto.workflow.WorkflowDependency;
import hdispatch.core.dispatch.mapper_hdispatch.WorkflowDependencyMapper;
import hdispatch.core.dispatch.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
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
    @Resource(name = "dependencyListCache")
    private String2ObjectCache deptListCache;
    @Resource(name = "dependencyMapCache")
    private String2ObjectCache deptMapCache;

    @Autowired
    private WorkflowDependencyMapper dependencyMapper;
    private Map<String, Map<String, List<String>>> deptMap = new ConcurrentHashMap<>();
    private Map<String, List<String>> deptListMap = new ConcurrentHashMap<>();
    private ThreadLocal<Set<String>> trace = new ThreadLocal<>();
    private ThreadLocal<Set<String>> topTrace = new ThreadLocal<>();

    public Map<String, List<String>> getDeptGraph(String flowName) {
        Map<String, List<String>> nodeDept = (Map<String, List<String>>) deptMapCache.get(flowName).get();
        if (nodeDept == null||!validateMapCache(nodeDept)) {
            nodeDept = buildGraph(flowName);
//            deptMap.put(flowName, nodeDept);
            deptMapCache.put(flowName, nodeDept);
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
//        List<String> deptList = dependencyMapper.queryDependency(flow).stream().map(WorkflowDependency::getDeptWorkflowName).collect(Collectors.toList());
        List<String> deptList = getDeptList(flow);
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
//        deptMap.remove(name);
//        deptListMap.remove(name);
        deptListCache.evict(name);
    }

    private boolean validateMapCache(Map<String, List<String>> map) {
        RedisTemplate redisTemplate = (RedisTemplate) deptListCache.getNativeCache();
        for (String key : map.keySet()) {
            boolean has = redisTemplate.boundHashOps(Constants.CACHE_NAMESPACE + deptListCache.getName()).hasKey(key);
            if (!has) return has;
        }
        return true;
    }

    private List<String> getDeptList(String name) {
        List<String> deptList = (List<String>) deptListCache.get(name).get();
        if (deptList == null) {
            deptList = dependencyMapper.queryDependency(name).stream().map(WorkflowDependency::getDeptWorkflowName).collect(Collectors.toList());
            deptListCache.put(name, deptList);
        }
        return deptList;
    }
}
