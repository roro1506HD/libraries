package ovh.roro.libraries.config.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ovh.roro.libraries.config.api.ConfigHolder;
import ovh.roro.libraries.config.api.ConfigReader;
import ovh.roro.libraries.config.api.ConfigWriter;
import ovh.roro.libraries.loader.LibraryLoader;
import ovh.roro.libraries.loader.plugin.PluginLibraryLoader;
import ovh.roro.libraries.loader.standalone.StandaloneLibraryLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiConsumer;
import java.util.function.Function;

@ApiStatus.Internal
public final class ConfigProviderImpl {

    public static final LibraryLoader<ConfigProviderImpl> LOADER;

    static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    static {
        if (LibraryLoader.isPaper()) {
            LOADER = new PluginLibraryLoader<>(plugin -> new ConfigProviderImpl(plugin.getSLF4JLogger()));
        } else {
            LOADER = new StandaloneLibraryLoader<>(() -> new ConfigProviderImpl(LoggerFactory.getLogger("Config API")));
        }
    }

    private final Logger logger;

    private ConfigProviderImpl(Logger logger) {
        this.logger = logger;
    }

    public static ConfigProviderImpl instance() {
        return ConfigProviderImpl.LOADER.getOrCreate();
    }

    public <T> ConfigHolder<T> createHolder(Path path, Function<ConfigReader, T> readMapper, BiConsumer<T, ConfigWriter> writeMapper) {
        return new ConfigHolderImpl<>(this.logger, path, readMapper, writeMapper);
    }

    public ConfigReader createReaderFromPath(Path path) {
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            return new ConfigReaderImpl(ConfigProviderImpl.GSON.fromJson(reader, JsonObject.class));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public ConfigWriter createWriterToPath(Path path) {
        return new ConfigWriterImpl(path);
    }
}
