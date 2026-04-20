package ovh.roro.libraries.config.api;

import org.jetbrains.annotations.ApiStatus;
import ovh.roro.libraries.config.impl.ConfigProviderImpl;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.function.BiConsumer;

@ApiStatus.NonExtendable
public interface ConfigWriter extends AutoCloseable {

    static ConfigWriter toFile(File file) {
        return ConfigWriter.toPath(file.toPath());
    }

    static ConfigWriter toPath(Path path) {
        return ConfigProviderImpl.instance().createWriterToPath(path);
    }

    void writeBoolean(String key, boolean value);

    void writeBoolean(boolean value);

    void writeByte(String key, byte value);

    void writeByte(byte value);

    void writeShort(String key, short value);

    void writeShort(short value);

    void writeInt(String key, int value);

    void writeInt(int value);

    void writeLong(String key, long value);

    void writeLong(long value);

    void writeFloat(String key, float value);

    void writeFloat(float value);

    void writeDouble(String key, double value);

    void writeDouble(double value);

    void writeString(String key, String value);

    void writeString(String value);

    <T> void writeObject(String key, T object, BiConsumer<T, ConfigWriter> writerConsumer);

    <T> void writeObject(T object, BiConsumer<T, ConfigWriter> writerConsumer);

    <T extends ConfigWritable> void writeObject(String key, T object);

    <T extends ConfigWritable> void writeObject(T object);

    <T> void writeCollection(String key, Collection<T> collection, BiConsumer<T, ConfigWriter> entryConsumer);

    <T> void writeCollection(Collection<T> collection, BiConsumer<T, ConfigWriter> entryConsumer);

    @Override
    void close() throws Exception;

}
