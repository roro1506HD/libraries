package ovh.roro.libraries.common.function;

@FunctionalInterface
public interface BooleanUnaryOperator {

    boolean applyAsBoolean(boolean value);

    default BooleanUnaryOperator compose(BooleanUnaryOperator before) {
        return value -> this.applyAsBoolean(before.applyAsBoolean(this.applyAsBoolean(value)));
    }

    default BooleanUnaryOperator andThen(BooleanUnaryOperator after) {
        return value -> after.applyAsBoolean(this.applyAsBoolean(value));
    }

    static BooleanUnaryOperator identity() {
        return value -> value;
    }
}
