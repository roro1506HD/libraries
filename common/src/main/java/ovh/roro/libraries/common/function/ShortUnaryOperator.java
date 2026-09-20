package ovh.roro.libraries.common.function;

@FunctionalInterface
public interface ShortUnaryOperator {

    short applyAsShort(short value);

    default ShortUnaryOperator compose(ShortUnaryOperator before) {
        return value -> this.applyAsShort(before.applyAsShort(this.applyAsShort(value)));
    }

    default ShortUnaryOperator andThen(ShortUnaryOperator after) {
        return value -> after.applyAsShort(this.applyAsShort(value));
    }

    static ShortUnaryOperator identity() {
        return value -> value;
    }
}
