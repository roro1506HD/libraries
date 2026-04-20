package ovh.roro.libraries.config.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.config.api.ConfigWritable;
import ovh.roro.libraries.config.api.ConfigWriter;

import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.function.BiConsumer;

@ApiStatus.Internal
public class ConfigWriterImpl implements ConfigWriter {

    private final Path path;
    private final Deque<JsonElement> parentElementQueue;
    private final JsonObject rootElement;

    private JsonElement currentElement;

    ConfigWriterImpl(Path path) {
        this.path = path;
        this.parentElementQueue = new ArrayDeque<>();
        this.rootElement = new JsonObject();

        this.currentElement = this.rootElement;
    }

    private JsonObject asObject() {
        if (this.currentElement.isJsonObject()) {
            return this.currentElement.getAsJsonObject();
        }

        throw new IllegalStateException("Current writing context is not within a JSON Object");
    }

    private JsonArray asArray() {
        if (this.currentElement.isJsonArray()) {
            return this.currentElement.getAsJsonArray();
        }

        throw new IllegalStateException("Current writing context is not within a JSON Array");
    }

    private void push(String key, JsonElement element) {
        this.asObject().add(key, element);
        this.parentElementQueue.add(this.currentElement);
        this.currentElement = element;
    }

    private void push(JsonElement element) {
        this.asArray().add(element);
        this.parentElementQueue.add(this.currentElement);
        this.currentElement = element;
    }

    private void pop() {
        this.currentElement = this.parentElementQueue.removeLast();
    }

    @Override
    public void writeBoolean(String key, boolean value) {
        this.asObject().addProperty(key, value);
    }

    @Override
    public void writeBoolean(boolean value) {
        this.asArray().add(value);
    }

    @Override
    public void writeByte(String key, byte value) {
        this.asObject().addProperty(key, value);
    }

    @Override
    public void writeByte(byte value) {
        this.asArray().add(value);
    }

    @Override
    public void writeShort(String key, short value) {
        this.asObject().addProperty(key, value);
    }

    @Override
    public void writeShort(short value) {
        this.asArray().add(value);
    }

    @Override
    public void writeInt(String key, int value) {
        this.asObject().addProperty(key, value);
    }

    @Override
    public void writeInt(int value) {
        this.asArray().add(value);
    }

    @Override
    public void writeLong(String key, long value) {
        this.asObject().addProperty(key, value);
    }

    @Override
    public void writeLong(long value) {
        this.asArray().add(value);
    }

    @Override
    public void writeFloat(String key, float value) {
        this.asObject().addProperty(key, value);
    }

    @Override
    public void writeFloat(float value) {
        this.asArray().add(value);
    }

    @Override
    public void writeDouble(String key, double value) {
        this.asObject().addProperty(key, value);
    }

    @Override
    public void writeDouble(double value) {
        this.asArray().add(value);
    }

    @Override
    public void writeString(String key, @Nullable String value) {
        this.asObject().addProperty(key, value);
    }

    @Override
    public void writeString(@Nullable String value) {
        this.asArray().add(value);
    }

    @Override
    public <T> void writeObject(String key, @Nullable T object, BiConsumer<T, ConfigWriter> writerConsumer) {
        if (object == null) {
            this.asObject().add(key, null);
            return;
        }

        this.push(key, new JsonObject());
        writerConsumer.accept(object, this);
        this.pop();
    }

    @Override
    public <T> void writeObject(@Nullable T object, BiConsumer<T, ConfigWriter> writerConsumer) {
        if (object == null) {
            this.asArray().add((JsonElement) null);
            return;
        }

        this.push(new JsonObject());
        writerConsumer.accept(object, this);
        this.pop();
    }

    @Override
    public <T extends ConfigWritable> void writeObject(String key, @Nullable T object) {
        this.writeObject(key, object, ConfigWritable::write);
    }

    @Override
    public <T extends ConfigWritable> void writeObject(@Nullable T object) {
        this.writeObject(object, ConfigWritable::write);
    }

    @Override
    public <T> void writeCollection(String key, @Nullable Collection<T> collection, BiConsumer<T, ConfigWriter> entryConsumer) {
        if (collection == null) {
            this.asObject().add(key, null);
            return;
        }

        this.push(key, new JsonArray());
        for (T entry : collection) {
            entryConsumer.accept(entry, this);
        }
        this.pop();
    }

    @Override
    public <T> void writeCollection(@Nullable Collection<T> collection, BiConsumer<T, ConfigWriter> entryConsumer) {
        if (collection == null) {
            this.asArray().add((JsonElement) null);
            return;
        }

        this.push(new JsonArray());
        for (T entry : collection) {
            entryConsumer.accept(entry, this);
        }
        this.pop();
    }

    @Override
    public void close() throws Exception {
        try (BufferedWriter writer = Files.newBufferedWriter(this.path, StandardCharsets.UTF_8)) {
            ConfigProviderImpl.GSON.toJson(this.rootElement, writer);
        }
    }
}
