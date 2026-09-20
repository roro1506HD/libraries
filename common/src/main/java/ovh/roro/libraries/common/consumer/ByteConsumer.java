package ovh.roro.libraries.common.consumer;

@FunctionalInterface
public interface ByteConsumer {

    void accept(byte value);

    default ByteConsumer andThen(ByteConsumer after) {
        return value -> {
            this.accept(value);
            after.accept(value);
        };
    }
}
