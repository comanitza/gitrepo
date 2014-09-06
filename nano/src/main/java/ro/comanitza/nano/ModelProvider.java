package ro.comanitza.nano;

import net.sf.cglib.proxy.Enhancer;

import java.util.Map;

/**
 * Implementation of the {@link ro.comanitza.nano.Provider} contract.
 *
 * It provided enhanced class instances based on a provided
 * {@link ro.comanitza.nano.ModelContainer} that describes
 * the enactments to be made.
 *
 * @author scomanita
 */
public class ModelProvider implements Provider {
    private Map<Class<?>, Model> models;

    /**
     *
     * Constructor that takes an {@link ro.comanitza.nano.ModelContainer} instance
     * to be used for describing the classes to be enhanced and their enhanced behaviour.
     *
     * @param modelContainer describes the behavior to be enhanced
     */
    public ModelProvider(final ModelContainer modelContainer) {
        this.models = modelContainer.getModelsMap();
    }

    @Override
    public <T> T get(final Class<T> type) {

        /**
         *
         */
        return createInstance(type, models.get(type), null);
    }

    @Override
    public <T> T get(Class<T> type, Params param) {

        /**
         * TODO: not yet implemented
         */
        return null;
    }

    /**
     *
     * Method for creating enhanced instances of the provided type based on the provided model
     *
     * @param type the type that will be enhanced
     * @param model the model containing the behaviour
     * @param param the parameters to be used for invoking a particular constructor
     * @param <T> the type to be enhanced
     * @return an enhance instance of the provided type
     */
    private <T> T createInstance(final Class<T> type, final Model model, final Params param) {
        if (type == null) {
            return null;
        }

        T instance;

        if (model == null) {
            instance = createVanillaInstance(type, param);
        } else {
            instance = createEnhancedInstance(type, model, param);
        }

        return instance;
    }

    /**
     *
     * Method for creating enhanced types
     *
     * @param type the type to enhance
     * @param model the model containing the behaviour
     * @param param the parameters to be used for invoking a particular constructor
     * @param <T> the type to be enhanced
     * @return an enhance instance of the provided type
     */
    private <T> T createEnhancedInstance(final Class<T> type, final Model model, final Params param) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(type);
        enhancer.setCallback(new NanoInterceptor(model));

        return type.cast(enhancer.create());
    }

    /**
     *
     * Method for creating a vanilla instance. Basically it will create an instance of the provided type
     *
     * @param type the type to instantiate
     * @param param he parameters to be used for invoking a particular constructor
     * @param <T> the type to be instantiated
     * @return an instance of the provided type
     */
    private <T> T createVanillaInstance(final Class<T> type, final Params param) {

        T instance;

        try {

            /**
             * TODO: make it so it can work with non void constructors
             */
            instance = type.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }

        return instance;
    }
}
