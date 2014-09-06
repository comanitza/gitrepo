package ro.comanitza.nano;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class that represents a parameter set to be passes to a method execution
 *
 * @author scomanita
 */
public class Params {
    private List<Object> values;

    public Params(final List<Object> values) {
        this.values = values;
    }

    public Params(final Object firstValue, final Object... values) {
        /**
         * TODO: filter out nulls
         */
        int capacity = (values != null) ? values.length + 1 : 1;
        this.values = new ArrayList<Object>(capacity);
        this.values.add(firstValue);
        this.values.addAll(Arrays.asList(values));
    }

    public List<Object> getValues() {
        return values;
    }
}
