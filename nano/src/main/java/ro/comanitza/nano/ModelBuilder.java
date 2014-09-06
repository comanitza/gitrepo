package ro.comanitza.nano;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Class that helps to build {@link ro.comanitza.nano.Model} instances.
 *
 *
 * @author scomanita
 */
public class ModelBuilder {

    private Method targetedMethod;

    /**
     * private constructor to prevent initialisation
     */
    private ModelBuilder() {
    }

    /**
     *
     * Method for starting the build process
     *
     * @return a new {@link ro.comanitza.nano.ModelBuilder} instance
     */
    public static ModelBuilder build() {
        return new ModelBuilder();
    }

    /**
     *
     * Method that will add a {@link Method} instance to the created {@link ro.comanitza.nano.Model}
     *
     * @param targetedMethod the {@link Method} to be added
     * @return an {@link ro.comanitza.nano.ModelBuilder.MethodModelBuilder} to be used internally
     */
    public MethodModelBuilder addMethod(final Method targetedMethod) {
        this.targetedMethod = targetedMethod;

        MethodModelBuilder methodModelBuilder = new MethodModelBuilder(targetedMethod);

        return methodModelBuilder;
    }

    /**
     *
     * Utility method for declaring a method to be enhanced
     *
     * @param targetClazz the {@link Class} that has the {@link java.lang.reflect.Method} to be enhanced
     * @param methodName the name of the method to be enhanced
     * @param types the type of the method to be enhanced
     * @return an {@link ro.comanitza.nano.ModelBuilder.MethodModelBuilder} to be used internally
     */
    public MethodModelBuilder addMethod(final Class<?> targetClazz, final String methodName, final Class<?> ... types) {
        Method foundMethod = null;

        try {
            foundMethod = targetClazz.getMethod(methodName, types);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        return addMethod(foundMethod);
    }

    /**
     *  Class to be used internally by {@link ro.comanitza.nano.ModelBuilder}
     *
     *  All setter declared fallow the fluent interface pattern,
     *  meaning they return a reference to this.
     */
    public static class MethodModelBuilder {
        private Method targetedMethod;
        private Function<?, ?> function;
        private JoinPoint joinPoint;
        private Params params;

        public MethodModelBuilder(final Method targetedMethod) {
            this.targetedMethod = targetedMethod;
        }

        public MethodModelBuilder addFunction(final Function<?, ?> function) {
            this.function = function;

            return this;
        }

        public MethodModelBuilder addJoinPoint(final JoinPoint joinPoint) {
            this.joinPoint = joinPoint;

            return this;
        }

        public MethodModelBuilder addParams(final Params params) {
            this.params = params;

            return this;
        }

        /**
         * Method that creates a {@link java.util.Map} instance representing
         * the mapping between the {@link java.lang.reflect.Method} to be
         * enhanced and a list of {@link ro.comanitza.nano.ExecutionDescriptor} to
         * be used for the enhancing
         *
         * @return the mapping between the methods and the execution descriptors
         */
        public Map<Method, ExecutionDescriptor> create() {
            if(function == null) {
                throw new IllegalStateException("Function can't be null.");
            }

            ExecutionDescriptor executionDescriptor = new ExecutionDescriptorImpl<>(
                    function, (params == null)? Optional.<Params>empty() : Optional.of(params), (joinPoint == null) ? JoinPoint.INSTEAD : joinPoint
                );

            Map<Method, ExecutionDescriptor> methodToDescriptor = new HashMap<>();
            methodToDescriptor.put(targetedMethod, executionDescriptor);

            return methodToDescriptor;
        }
    }
}
