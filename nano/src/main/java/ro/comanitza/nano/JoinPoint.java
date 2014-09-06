package ro.comanitza.nano;

/**
 * Enum that describes the invocation timing of the
 * described {@link ro.comanitza.nano.ExecutionDescriptor}.
 *
 * @scomanita
 */
public enum JoinPoint {

    /**
     * This marks an {@link ro.comanitza.nano.ExecutionDescriptor} that should be exevuted before the actual method that is enhanced. Multiple
     */
    BEFORE(0),
    INSTEAD(1),
    AFTER(2);

    private int value;

    private JoinPoint(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
