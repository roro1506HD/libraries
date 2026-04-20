package ovh.roro.libraries.config.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.config.api.ConfigReader;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.function.Function;
import java.util.function.Supplier;

@ApiStatus.Internal
public class ConfigReaderImpl implements ConfigReader {

    private static final ConfigReader EMPTY_READER = new ConfigReaderImpl(new JsonObject());

    private final Deque<JsonElement> parentElementQueue;

    private JsonElement currentElement;

    ConfigReaderImpl(JsonObject root) {
        this.parentElementQueue = new ArrayDeque<>();

        this.currentElement = root;
    }

    private JsonObject asObject() {
        if (this.currentElement.isJsonObject()) {
            return this.currentElement.getAsJsonObject();
        }

        throw new IllegalStateException("Current reading context is not within a JSON Object");
    }

    private JsonArray asArray() {
        if (this.currentElement.isJsonArray()) {
            return this.currentElement.getAsJsonArray();
        }

        throw new IllegalStateException("Current reading context is not within a JSON Array");
    }

    private JsonElement asArrayElement() {
        if (this.parentElementQueue.getLast().isJsonArray()) {
            return this.currentElement;
        }

        throw new IllegalStateException("Current reading context is not within a JSON Array");
    }

    private @Nullable JsonElement push(String key) {
        JsonElement element = this.asObject().get(key);
        if (element == null || !element.isJsonObject()) {
            return null;
        }

        this.parentElementQueue.add(this.currentElement);
        this.currentElement = element;

        return element;
    }

    private void push(int index) {
        JsonArray array = this.asArray();
        if (index < 0 || index >= array.size()) {
            return;
        }

        this.parentElementQueue.add(this.currentElement);
        this.currentElement = array.get(index);
    }

    private void pop() {
        this.currentElement = this.parentElementQueue.removeLast();
    }

    @Override
    public boolean readBoolean(String key, boolean fallback) {
        JsonElement element = this.asObject().get(key);
        return element == null || !element.isJsonPrimitive() ? fallback : element.getAsBoolean();
    }

    @Override
    public boolean readBoolean(boolean fallback) {
        JsonElement element = this.asArrayElement();
        return !element.isJsonPrimitive() ? fallback : element.getAsBoolean();
    }

    @Override
    public byte readByte(String key, byte fallback) {
        JsonElement element = this.asObject().get(key);
        return element == null || !element.isJsonPrimitive() ? fallback : element.getAsByte();
    }

    @Override
    public byte readByte(byte fallback) {
        JsonElement element = this.asArrayElement();
        return !element.isJsonPrimitive() ? fallback : element.getAsByte();
    }

    @Override
    public short readShort(String key, short fallback) {
        JsonElement element = this.asObject().get(key);
        return element == null || !element.isJsonPrimitive() ? fallback : element.getAsShort();
    }

    @Override
    public short readShort(short fallback) {
        JsonElement element = this.asArrayElement();
        return !element.isJsonPrimitive() ? fallback : element.getAsShort();
    }

    @Override
    public int readInt(String key, int fallback) {
        JsonElement element = this.asObject().get(key);
        return element == null || !element.isJsonPrimitive() ? fallback : element.getAsInt();
    }

    @Override
    public int readInt(int fallback) {
        JsonElement element = this.asArrayElement();
        return !element.isJsonPrimitive() ? fallback : element.getAsInt();
    }

    @Override
    public long readLong(String key, long fallback) {
        JsonElement element = this.asObject().get(key);
        return element == null || !element.isJsonPrimitive() ? fallback : element.getAsLong();
    }

    @Override
    public long readLong(long fallback) {
        JsonElement element = this.asArrayElement();
        return !element.isJsonPrimitive() ? fallback : element.getAsLong();
    }

    @Override
    public float readFloat(String key, float fallback) {
        JsonElement element = this.asObject().get(key);
        return element == null || !element.isJsonPrimitive() ? fallback : element.getAsFloat();
    }

    @Override
    public float readFloat(float fallback) {
        JsonElement element = this.asArrayElement();
        return !element.isJsonPrimitive() ? fallback : element.getAsFloat();
    }

    @Override
    public double readDouble(String key, double fallback) {
        JsonElement element = this.asObject().get(key);
        return element == null || !element.isJsonPrimitive() ? fallback : element.getAsDouble();
    }

    @Override
    public double readDouble(double fallback) {
        JsonElement element = this.asArrayElement();
        return !element.isJsonPrimitive() ? fallback : element.getAsDouble();
    }

    @Override
    public String readString(String key, String fallback) {
        JsonElement element = this.asObject().get(key);
        return element == null || !element.isJsonPrimitive() ? fallback : element.getAsString();
    }

    @Override
    public String readString(String fallback) {
        JsonElement element = this.asArrayElement();
        return !element.isJsonPrimitive() ? fallback : element.getAsString();
    }

    @Override
    public <T> T readObject(String key, Function<ConfigReader, T> factory) {
        JsonElement element = this.push(key);
        if (element == null || !element.isJsonObject()) {
            if (element != null) {
                // Don't forget to pop back to the original context
                // But keep in mind that the context is only shifted if the
                // return value of push(..) is not null
                this.pop();
            }

            return factory.apply(ConfigReaderImpl.EMPTY_READER);
        }

        T value = factory.apply(this);
        this.pop(); // Don't forget to pop back to the original context

        return value;
    }

    @Override
    public <T> T readObject(Function<ConfigReader, T> factory) {
        JsonElement element = this.asArrayElement();
        if (!element.isJsonObject()) {
            return factory.apply(ConfigReaderImpl.EMPTY_READER);
        }

        return factory.apply(this);
    }

    @Override
    public <C extends Collection<T>, T> C readCollection(String key, Function<ConfigReader, T> entryFactory, Supplier<C> collectionAggregator) {
        JsonElement element = this.push(key);
        C value = collectionAggregator.get();
        if (element == null || !element.isJsonArray()) {
            if (element != null) {
                // Don't forget to pop back to the original context
                // But keep in mind that the context is only shifted if the
                // return value of push(..) is not null
                this.pop();
            }

            return value;
        }

        JsonArray array = element.getAsJsonArray();

        for (int i = 0; i < array.size(); i++) {
            this.push(i);
            value.add(entryFactory.apply(this));
            this.pop(); // Don't forget to pop out of the array
        }

        this.pop(); // Don't forget to pop back to the original context

        return value;
    }

    @Override
    public <C extends Collection<T>, T> C readCollection(Function<ConfigReader, T> entryFactory, Supplier<C> collectionAggregator) {
        JsonElement element = this.asArrayElement();
        C value = collectionAggregator.get();
        if (!element.isJsonArray()) {
            return value;
        }

        JsonArray array = element.getAsJsonArray();

        for (int i = 0; i < array.size(); i++) {
            this.push(i);
            value.add(entryFactory.apply(this));
            this.pop(); // Don't forget to pop out of the array
        }

        return value;
    }
}
