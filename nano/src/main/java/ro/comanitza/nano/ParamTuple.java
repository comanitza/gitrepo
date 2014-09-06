package ro.comanitza.nano;

/**
 *
 * Class that represent a param value and it's type.
 *
 * @author scomanita
 */
public class ParamTuple<T> {

    private T value;
    private Class<T> type;

    public ParamTuple(final T value, final Class<T> type) {
        this.value = value;
        this.type = type;
    }

    public T getValue() {
        return value;
    }

    public Class<T> getType() {
        return type;
    }

    public T getValueAsType() {
        return type.cast(value);
    }
}
