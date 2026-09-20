package ovh.roro.libraries.common.function;

@FunctionalInterface
public interface BooleanBinaryOperator {

    boolean applyAsBoolean(boolean left, boolean right);

}
