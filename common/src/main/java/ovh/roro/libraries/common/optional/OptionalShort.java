package ovh.roro.libraries.common.optional;

import ovh.roro.libraries.common.consumer.ByteConsumer;
import ovh.roro.libraries.common.consumer.ShortConsumer;
import ovh.roro.libraries.common.supplier.ByteSupplier;
import ovh.roro.libraries.common.supplier.ShortSupplier;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Supplier;

public final class OptionalShort {

    private static final OptionalShort EMPTY = new OptionalShort();

    private final boolean present;
    private final short value;

    private OptionalShort() {
        this.present = false;
        this.value = 0;
    }

    private OptionalShort(short value) {
        this.present = true;
        this.value = value;
    }

    public static OptionalShort empty() {
        return OptionalShort.EMPTY;
    }

    public static OptionalShort of(short value) {
        return new OptionalShort(value);
    }

    public short getAsShort() {
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

    public void ifPresent(ShortConsumer action) {
        if (this.present) {
            action.accept(this.value);
        }
    }

    public void ifPresentOrElse(ShortConsumer action, Runnable emptyAction) {
        if (this.present) {
            action.accept(this.value);
        } else {
            emptyAction.run();
        }
    }

    public short orElse(short other) {
        return this.present ? this.value : other;
    }

    public short orElseGet(ShortSupplier supplier) {
        return this.present ? this.value : supplier.getAsShort();
    }

    public short orElseThrow() {
        if (!this.present) {
            throw new NoSuchElementException("No value present");
        }

        return this.value;
    }

    public <E extends Throwable> short orElseThrow(Supplier<? extends E> exceptionSupplier) throws E {
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

        if (!(o instanceof OptionalShort that)) {
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
        return new StringJoiner(", ", OptionalShort.class.getSimpleName() + "[", "]")
                .add("present=" + this.present)
                .add("value=" + this.value)
                .toString();
    }
}
