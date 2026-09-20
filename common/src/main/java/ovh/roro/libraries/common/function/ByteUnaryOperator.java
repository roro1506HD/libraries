package ovh.roro.libraries.common.function;

@FunctionalInterface
public interface ByteUnaryOperator {

    byte applyAsByte(byte value);

    default ByteUnaryOperator compose(ByteUnaryOperator before) {
        return value -> this.applyAsByte(before.applyAsByte(this.applyAsByte(value)));
    }

    default ByteUnaryOperator andThen(ByteUnaryOperator after) {
        return value -> after.applyAsByte(this.applyAsByte(value));
    }

    static ByteUnaryOperator identity() {
        return value -> value;
    }
}
