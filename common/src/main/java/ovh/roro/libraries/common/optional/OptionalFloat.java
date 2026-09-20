package ovh.roro.libraries.common.optional;

import ovh.roro.libraries.common.consumer.FloatConsumer;
import ovh.roro.libraries.common.supplier.FloatSupplier;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Supplier;

public final class OptionalFloat {

    private static final OptionalFloat EMPTY = new OptionalFloat();

    private final boolean present;
    private final float value;

    private OptionalFloat() {
        this.present = false;
        this.value = 0;
    }

    private OptionalFloat(float value) {
        this.present = true;
        this.value = value;
    }

    public static OptionalFloat empty() {
        return OptionalFloat.EMPTY;
    }

    public static OptionalFloat of(float value) {
        return new OptionalFloat(value);
    }

    public float getAsFloat() {
        if (!this.present) {
            throw new NoSuchElementException("No value present");
        }

        return this.value;
    }

    public boolean isPresent() {
        return this.present;
    }

    public boolean isEmpty() {
        return !this.present;
    }

    public void ifPresent(FloatConsumer action) {
        if (this.present) {
            action.accept(this.value);
        }
    }

    public void ifPresentOrElse(FloatConsumer action, Runnable emptyAction) {
        if (this.present) {
            action.accept(this.value);
        } else {
            emptyAction.run();
        }
    }

    public float orElse(float other) {
        return this.present ? this.value : other;
    }

    public float orElseGet(FloatSupplier supplier) {
        return this.present ? this.value : supplier.getAsFloat();
    }

    public float orElseThrow() {
        if (!this.present) {
            throw new NoSuchElementException("No value present");
        }

        return this.value;
    }

    public <E extends Throwable> float orElseThrow(Supplier<? extends E> exceptionSupplier) throws E {
        if (!this.present) {
            throw exceptionSupplier.get();
        }

        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof OptionalFloat that)) {
            return false;
        }

        return this.present == that.present &&
                this.value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.present, this.value);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OptionalFloat.class.getSimpleName() + "[", "]")
                .add("present=" + this.present)
                .add("value=" + this.value)
                .toString();
    }
}
