package ovh.roro.libraries.config.api;

import org.jetbrains.annotations.ApiStatus;
import ovh.roro.libraries.config.impl.ConfigProviderImpl;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.function.Function;
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

    boolean readBoolean(boolean fallback);

    byte readByte(String key, byte fallback);

    byte readByte(byte fallback);

    short readShort(String key, short fallback);

    short readShort(short fallback);

    int readInt(String key, int fallback);

    int readInt(int fallback);

    long readLong(String key, long fallback);

    long readLong(long fallback);

    float readFloat(String key, float fallback);

    float readFloat(float fallback);

    double readDouble(String key, double fallback);

    double readDouble(double fallback);

    String readString(String key, String fallback);

    String readString(String fallback);

    <T> T readObject(String key, Function<ConfigReader, T> factory);

    <T> T readObject(Function<ConfigReader, T> factory);

    <C extends Collection<T>, T> C readCollection(String key, Function<ConfigReader, T> entryFactory, Supplier<C> collectionAggregator);

    <C extends Collection<T>, T> C readCollection(Function<ConfigReader, T> entryFactory, Supplier<C> collectionAggregator);

}
