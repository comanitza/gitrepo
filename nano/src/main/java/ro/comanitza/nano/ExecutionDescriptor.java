package ro.comanitza.nano;

import java.util.Optional;
import java.util.function.Function;

/**
 *  Contract that describes the execution of an aspect.
 *  It defines the {@link java.util.function.Function}
 *  to be executed, the {@link Params} to be passed and
 *  the {@link ro.comanitza.nano.JoinPoint}.
 *
 *  @author scomanita
 */
public interface ExecutionDescriptor {

    public Function getFunction();

    public Optional<Params> getParam();

    public JoinPoint getJoinPoint();
}
