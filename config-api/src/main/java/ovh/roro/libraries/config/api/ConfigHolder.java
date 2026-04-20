package ovh.roro.libraries.config.api;

import org.jetbrains.annotations.ApiStatus;
import ovh.roro.libraries.config.impl.ConfigProviderImpl;

import java.nio.file.Path;
import java.util.function.BiConsumer;
import java.util.function.Function;

@ApiStatus.NonExtendable
public interface ConfigHolder<T> {

    static <T> ConfigHolder<T> of(Path path, Function<ConfigReader, T> readMapper, BiConsumer<T, ConfigWriter> writeMapper) {
        return ConfigProviderImpl.instance().createHolder(path, readMapper, writeMapper);
    }

    static <T extends ConfigWritable> ConfigHolder<T> ofWritable(Path path, Function<ConfigReader, T> readMapper) {
        return ConfigHolder.of(path, readMapper, ConfigWritable::write);
    }

    T get();

    boolean load();

    boolean save();

}
