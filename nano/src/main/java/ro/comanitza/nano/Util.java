package ro.comanitza.nano;

import java.lang.reflect.Method;

/**
 * Utility class
 *
 * @author scomanita
 */
public class Util {

    /**
     * private constructor to prevent instantiation
     */
    private Util() {
    }

    public Object parseParamValues(final Params parameter, final Method method) {
        return parseParamValues(parameter, method.getParameterTypes());
    }

    public static Object parseParamValues(final Params parameter, final Class<?>[] types) {
        if (parameter == null || parameter.getValues() == null || parameter.getValues().isEmpty()) {
            return null;
        }

        if(parameter.getValues().size() == 1) {
            return parameter.getValues().get(0);
        }

        return null;
    }
}
