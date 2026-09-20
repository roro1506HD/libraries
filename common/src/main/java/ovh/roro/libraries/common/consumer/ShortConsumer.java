package ovh.roro.libraries.common.consumer;

@FunctionalInterface
public interface ShortConsumer {

    void accept(short value);

    default ShortConsumer andThen(ShortConsumer after) {
        return value -> {
            this.accept(value);
            after.accept(value);
        };
    }
}
