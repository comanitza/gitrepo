package ro.comanitza.nano;

import java.util.Optional;
import java.util.function.Function;

/**
 *
 * Default implementation of the {@link ro.comanitza.nano.ExecutionDescriptor} contract.
 *
 * @author scomanita
 */
public class ExecutionDescriptorImpl<T, R> implements ExecutionDescriptor  {
    private Function <T, R> function;
    private Optional<Params> param;
    private JoinPoint joinPoint;

    public ExecutionDescriptorImpl(final Function<T, R> function, final Optional<Params> param, final JoinPoint joinPoint) {
        this.function = function;
        this.param = param;
        this.joinPoint = joinPoint;
    }

    public Function<T, R> getFunction() {
        return function;
    }

    public Optional<Params> getParam() {
        return param;
    }

    public JoinPoint getJoinPoint() {
        return joinPoint;
    }
}
