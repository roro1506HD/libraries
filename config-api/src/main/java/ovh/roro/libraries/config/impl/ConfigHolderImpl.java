package ovh.roro.libraries.config.impl;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import ovh.roro.libraries.config.api.ConfigHolder;
import ovh.roro.libraries.config.api.ConfigReader;
import ovh.roro.libraries.config.api.ConfigWriter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

@ApiStatus.Internal
public class ConfigHolderImpl<T> implements ConfigHolder<T> {

    private final Logger logger;
    private final Path path;
    private final Function<ConfigReader, T> readMapper;
    private final BiConsumer<T, ConfigWriter> writeMapper;

    private @Nullable T instance;

    ConfigHolderImpl(Logger logger, Path path, Function<ConfigReader, T> readMapper, BiConsumer<T, ConfigWriter> writeMapper) {
        this.logger = logger;
        this.path = path;
        this.readMapper = readMapper;
        this.writeMapper = writeMapper;
    }

    @Override
    public T get() {
        return Objects.requireNonNull(this.instance, "Config isn't loaded");
    }

    private T loadDefaults() {
        ConfigReader reader = new ConfigReaderImpl(new JsonObject());
        return this.readMapper.apply(reader);
    }

    @Override
    public boolean load() {
        if (Files.notExists(this.path)) {
            this.logger.info("Config file {} doesn't exists, saving defaults", this.path);
            this.instance = this.loadDefaults();
            this.save();
            return true;
        }

        try {
            ConfigReader reader = ConfigReader.fromPath(this.path);
            this.instance = this.readMapper.apply(reader);
            return true;
        } catch (Exception ex) {
            this.logger.error("Failed to load config {}, using defaults", this.path, ex);
            this.instance = this.loadDefaults();
            return false;
        }
    }

    @Override
    public boolean save() {
        T config = this.get();
        try (ConfigWriter writer = ConfigWriter.toPath(this.path)) {
            this.writeMapper.accept(config, writer);
            return true;
        } catch (Exception ex) {
            this.logger.error("Failed to save config {}", this.path, ex);
            return false;
        }
    }
}
