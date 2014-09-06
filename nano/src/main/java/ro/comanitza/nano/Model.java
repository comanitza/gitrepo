package ro.comanitza.nano;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that describe the behavior of a class that will be enhanced.
 * Each {@link java.lang.reflect.Method} can be associated with a list
 * of {@link ro.comanitza.nano.ExecutionDescriptor} in order to describe
 * the enhanced behavior if that particular method.
 *
 * All the provided {@link java.lang.reflect.Method} instances should belong
 * to the same class.
 *
 * @author scomanita
 */
public class Model {
    private Map<Method, List<ExecutionDescriptor>> methodToExecutionDescriptorsMap = new HashMap<Method, List<ExecutionDescriptor>>();

    /**
     *
     * Method to add behaviour via an {@link ro.comanitza.nano.ExecutionDescriptor} instance to an {@link java.lang.reflect.Method}
     *
     * @param method the method to add the behaviour to
     * @param executionDescriptor the behaviour to add to the provided method
     */
    public void addBundleToMethod(final Method method, final ExecutionDescriptor executionDescriptor) {
        if (methodToExecutionDescriptorsMap.containsKey(method)) {
            methodToExecutionDescriptorsMap.get(method).add(executionDescriptor);
        } else {
            methodToExecutionDescriptorsMap.put(method, new ArrayList<ExecutionDescriptor>() {{
                add(executionDescriptor);
            }});
        }
    }

    public Map<Method, List<ExecutionDescriptor>> getMethodToExecutionDescriptorsMap() {
        return methodToExecutionDescriptorsMap;
    }
}
