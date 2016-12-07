package hdispatch.core.dispatch.manager;

import hdispatch.core.dispatch.mapper_hdispatch.WorkflowDependencyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuneng on 2016/12/6.
 */
@Component
public class DepencencyManager {
    @Autowired
    private WorkflowDependencyMapper mapper;
    private Map<String,Map<String,List<String>>> deptMap = new HashMap<>();
    private Map<String,List<String>> deptListMap = new HashMap<>();

    public Map<String,List<String>> getDeptGraph(String flowName) {
        Map<String,List<String>> nodeDept = deptMap.get(flowName);
        if (nodeDept == null) {

        }
        return nodeDept;
    }

    public Map<String,List<String>> buildGraph(String flowName) {

        return null;
    }


    public void expire(String name) {

    }


}
