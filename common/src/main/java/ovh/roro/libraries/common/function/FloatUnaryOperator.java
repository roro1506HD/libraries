package ovh.roro.libraries.common.function;

@FunctionalInterface
public interface FloatUnaryOperator {

    float applyAsFloat(float value);

    default FloatUnaryOperator compose(FloatUnaryOperator before) {
        return value -> this.applyAsFloat(before.applyAsFloat(this.applyAsFloat(value)));
    }

    default FloatUnaryOperator andThen(FloatUnaryOperator after) {
        return value -> after.applyAsFloat(this.applyAsFloat(value));
    }

    static FloatUnaryOperator identity() {
        return value -> value;
    }
}
