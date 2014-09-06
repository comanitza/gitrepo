package ro.comanitza.nano;

/**
 *
 * Contract for defining a provider instance.
 * A provided is the entity that fetches an enhanced type.
 *
 * @author scomanita
 */
public interface Provider {

    /**
     *
     * Method that retrieves a an enhanced type that is an instance of the provided
     * type T represented by the input {@link java.lang.Class} instance.
     *
     * @param type a {@link java.lang.Class} instance representing the type to be enhanced
     * @param <T> the type to be enhanced
     * @return a enhanced T instance
     */
    public <T> T get(Class<T> type);

    /**
     *
     * Method that retrieves a an enhanced type that is an instance of the provided
     * type T represented by the input {@link java.lang.Class} instance.
     *
     * @param type a {@link java.lang.Class} instance representing the type to be enhanced
     * @param param the parameters of the constructor to be invoked
     * @param <T> the type to be enhanced
     * @return a enhanced T instance
     */
    public <T> T get(Class<T> type, Params param);
}
