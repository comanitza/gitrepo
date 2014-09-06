package ro.comanitza.nano;

import java.util.Comparator;

/**
 * Comparator implementation that compares two provided {@link ExecutionDescriptor} instances.
 *
 * @author scomanita
 */
public class ExecutionDescriptorComparator implements Comparator<ExecutionDescriptor> {

    @Override
    public int compare(final ExecutionDescriptor first, final ExecutionDescriptor second) {
        if (first == null || second == null || first.getJoinPoint() == null || second.getJoinPoint() == null) {
            throw new RuntimeException("Both provided instances should be non null have join points");
        }

        return (first.getJoinPoint().getValue() - second.getJoinPoint().getValue());
    }
}
