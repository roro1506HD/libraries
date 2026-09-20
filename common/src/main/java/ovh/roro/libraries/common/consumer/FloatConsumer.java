package ovh.roro.libraries.common.consumer;

@FunctionalInterface
public interface FloatConsumer {

    void accept(float value);

    default FloatConsumer andThen(FloatConsumer after) {
        return value -> {
            this.accept(value);
            after.accept(value);
        };
    }
}
