package ro.comanitza.nano;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that represents a container for {@link Model} instances
 *
 * @author scomanita
 */
public class ModelContainer {
    private Map<Class<?>, Model> models;

    public ModelContainer() {
        models = new HashMap<>();
    }

    public ModelContainer(final Map<Class<?>, Model> models) {
        if(models == null) {
            throw new IllegalStateException("Provided models should not be null.");
        }

        this.models = models;
    }

    /**
     *
     * Method for adding a {@link Model} instance to this model container
     *
     * @param model the {@link Model} instance to be used
     */
    public void addMode(final Model model) {
        if (model == null || model.getMethodToExecutionDescriptorsMap() == null || model.getMethodToExecutionDescriptorsMap().isEmpty()) {
            return;
        }

        Method foundMethod = Method.class.cast(model.getMethodToExecutionDescriptorsMap().entrySet().toArray()[0]);

        models.put(foundMethod.getDeclaringClass(), model);
    }

    /**
     *
     * Method that add a {@link Model} instance to this container based on the
     * provided mapping between {@link java.lang.reflect.Method} instances
     * and {@link ro.comanitza.nano.ExecutionDescriptor}
     *
     * @param methodToDescriptor
     */
    public void addModel(final Map<Method, ExecutionDescriptor> methodToDescriptor) {
        if(methodToDescriptor == null || methodToDescriptor.isEmpty()) {
            return;
        }

        Model model = new Model();

        Method foundMethod = null;

        for(Map.Entry<Method, ExecutionDescriptor> entry: methodToDescriptor.entrySet()) {
            model.addBundleToMethod(entry.getKey(), entry.getValue());

            foundMethod = entry.getKey();
            Class<?> declaringClass = foundMethod.getDeclaringClass();

            if(models.containsKey(declaringClass)) {
                Model foundModel = models.get(declaringClass);
                foundModel.addBundleToMethod(entry.getKey(), entry.getValue());

            } else {
                models.put(declaringClass, model);
            }
        }
    }

    public Model getModel(final Class<?> clazz) {
        return models.get(clazz);
    }

    public Map<Class<?>, Model> getModelsMap() {
        return models;
    }
}
