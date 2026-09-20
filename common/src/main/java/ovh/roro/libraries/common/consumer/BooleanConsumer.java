package ovh.roro.libraries.common.consumer;

@FunctionalInterface
public interface BooleanConsumer {

    void accept(boolean value);

    default BooleanConsumer andThen(BooleanConsumer after) {
        return value -> {
            this.accept(value);
            after.accept(value);
        };
    }
}
