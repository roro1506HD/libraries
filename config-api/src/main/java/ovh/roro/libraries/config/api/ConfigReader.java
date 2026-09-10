package ovh.roro.libraries.config.api;

import org.jetbrains.annotations.ApiStatus;
import ovh.roro.libraries.common.function.ByteSupplier;
import ovh.roro.libraries.common.function.FloatSupplier;
import ovh.roro.libraries.common.function.ShortSupplier;
import ovh.roro.libraries.config.impl.ConfigProviderImpl;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

@ApiStatus.NonExtendable
public interface ConfigReader {

    static ConfigReader fromFile(File file) {
        return ConfigReader.fromPath(file.toPath());
    }

    static ConfigReader fromPath(Path path) {
        return ConfigProviderImpl.instance().createReaderFromPath(path);
    }

    boolean readBoolean(String key, boolean fallback);

    boolean readBoolean(String key, BooleanSupplier fallback);

    boolean readBoolean(boolean fallback);

    boolean readBoolean(BooleanSupplier fallback);

    byte readByte(String key, byte fallback);

    byte readByte(String key, ByteSupplier fallback);

    byte readByte(byte fallback);

    byte readByte(ByteSupplier fallback);

    short readShort(String key, short fallback);

    short readShort(String key, ShortSupplier fallback);

    short readShort(short fallback);

    short readShort(ShortSupplier fallback);

    int readInt(String key, int fallback);

    int readInt(String key, IntSupplier fallback);

    int readInt(int fallback);

    int readInt(IntSupplier fallback);

    long readLong(String key, long fallback);

    long readLong(String key, LongSupplier fallback);

    long readLong(long fallback);

    long readLong(LongSupplier fallback);

    float readFloat(String key, float fallback);

    float readFloat(String key, FloatSupplier fallback);

    float readFloat(float fallback);

    float readFloat(FloatSupplier fallback);

    double readDouble(String key, double fallback);

    double readDouble(String key, DoubleSupplier fallback);

    double readDouble(double fallback);

    double readDouble(DoubleSupplier fallback);

    String readString(String key, String fallback);

    String readString(String key, Supplier<String> fallback);

    String readString(String fallback);

    String readString(Supplier<String> fallback);

    <T> T readObject(String key, Function<ConfigReader, T> factory);

    <T> T readObject(Function<ConfigReader, T> factory);

    <C extends Collection<T>, T> C readCollection(String key, Function<ConfigReader, T> entryFactory, Supplier<C> collectionAggregator);

    <C extends Collection<T>, T> C readCollection(Function<ConfigReader, T> entryFactory, Supplier<C> collectionAggregator);

}
