package ovh.roro.libraries.common.optional;

import ovh.roro.libraries.common.consumer.BooleanConsumer;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public final class OptionalBoolean {

    private static final OptionalBoolean EMPTY = new OptionalBoolean();
    private static final OptionalBoolean TRUE = new OptionalBoolean(true);
    private static final OptionalBoolean FALSE = new OptionalBoolean(false);

    private final boolean present;
    private final boolean value;

    private OptionalBoolean() {
        this.present = false;
        this.value = false;
    }

    private OptionalBoolean(boolean value) {
        this.present = true;
        this.value = value;
    }

    public static OptionalBoolean empty() {
        return OptionalBoolean.EMPTY;
    }

    public static OptionalBoolean of(boolean value) {
        return value ? OptionalBoolean.TRUE : OptionalBoolean.FALSE;
    }

    public boolean getAsBoolean() {
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

    public void ifPresent(BooleanConsumer action) {
        if (this.present) {
            action.accept(this.value);
        }
    }

    public void ifPresentOrElse(BooleanConsumer action, Runnable emptyAction) {
        if (this.present) {
            action.accept(this.value);
        } else {
            emptyAction.run();
        }
    }

    public boolean orElse(boolean other) {
        return this.present ? this.value : other;
    }

    public boolean orElseGet(BooleanSupplier supplier) {
        return this.present ? this.value : supplier.getAsBoolean();
    }

    public boolean orElseThrow() {
        if (!this.present) {
            throw new NoSuchElementException("No value present");
        }

        return this.value;
    }

    public <E extends Throwable> boolean orElseThrow(Supplier<? extends E> exceptionSupplier) throws E {
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

        if (!(o instanceof OptionalBoolean that)) {
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
        return new StringJoiner(", ", OptionalBoolean.class.getSimpleName() + "[", "]")
                .add("present=" + this.present)
                .add("value=" + this.value)
                .toString();
    }
}
