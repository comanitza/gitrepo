package ro.comanitza.nano;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * Class that intercept the method calls in the enhanced instances.
 *
 * @author scomanita
 */
public class NanoInterceptor implements MethodInterceptor {

    private final Model model;

    public NanoInterceptor(final Model model) {
        this.model = model;
    }

    /**
     *
     * Method that actually does the heavy lifting in the enhancement process.
     *
     * It intercept the methods and applies the injected behaviour if needed.
     *
     * @param obj the object that is intercepted
     * @param method the intercepted method
     * @param args the provided arguments
     * @param methodProxy a proxy over the intercepted method
     * @return the result of the operation
     * @throws Throwable generic exception/error
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object result = null;

        List<ExecutionDescriptor> sortedDescriptors = new ArrayList<ExecutionDescriptor>(model.getMethodToExecutionDescriptorsMap().get(method));

        Collections.sort(sortedDescriptors, new ExecutionDescriptorComparator());

        for(ExecutionDescriptor descriptor: sortedDescriptors) {

            JoinPoint joinPoint = descriptor.getJoinPoint();

            Object value = null;

            if (descriptor.getParam().isPresent()) {
                Params parameter = descriptor.getParam().get();
                value = Util.parseParamValues(parameter, (Class<?>[])null);
            }

            if (JoinPoint.INSTEAD.equals(joinPoint)) {
                result = descriptor.getFunction().apply(value);
            } else {
                descriptor.getFunction().apply(value);
            }
        }

        if (result == null) {
            result = methodProxy.invokeSuper(obj, args);
        }

        return method.getReturnType().cast(result);
    }
}
