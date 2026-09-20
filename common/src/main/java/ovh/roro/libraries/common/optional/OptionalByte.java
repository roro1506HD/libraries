package ovh.roro.libraries.common.optional;

import ovh.roro.libraries.common.consumer.ByteConsumer;
import ovh.roro.libraries.common.supplier.ByteSupplier;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Supplier;

public final class OptionalByte {

    private static final OptionalByte EMPTY = new OptionalByte();

    private final boolean present;
    private final byte value;

    private OptionalByte() {
        this.present = false;
        this.value = 0;
    }

    private OptionalByte(byte value) {
        this.present = true;
        this.value = value;
    }

    public static OptionalByte empty() {
        return OptionalByte.EMPTY;
    }

    public static OptionalByte of(byte value) {
        return new OptionalByte(value);
    }

    public byte getAsByte() {
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

    public void ifPresent(ByteConsumer action) {
        if (this.present) {
            action.accept(this.value);
        }
    }

    public void ifPresentOrElse(ByteConsumer action, Runnable emptyAction) {
        if (this.present) {
            action.accept(this.value);
        } else {
            emptyAction.run();
        }
    }

    public byte orElse(byte other) {
        return this.present ? this.value : other;
    }

    public byte orElseGet(ByteSupplier supplier) {
        return this.present ? this.value : supplier.getAsByte();
    }

    public byte orElseThrow() {
        if (!this.present) {
            throw new NoSuchElementException("No value present");
        }

        return this.value;
    }

    public <E extends Throwable> byte orElseThrow(Supplier<? extends E> exceptionSupplier) throws E {
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

        if (!(o instanceof OptionalByte that)) {
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
        return new StringJoiner(", ", OptionalByte.class.getSimpleName() + "[", "]")
                .add("present=" + this.present)
                .add("value=" + this.value)
                .toString();
    }
}
