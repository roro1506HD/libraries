package ovh.roro.libraries.language.impl;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.Formatter;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import ovh.roro.libraries.language.api.Placeholder;
import ovh.roro.libraries.language.api.Translation;
import ovh.roro.libraries.language.api.ZonedTime;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Function;

@ApiStatus.Internal
public final class PlaceholderImpl implements Placeholder {

    private final @NotNull String code;
    private final @NotNull Object value;
    private final @NotNull Function<LanguageImpl, TagResolver> toTagResolver;

    private PlaceholderImpl(
            @NotNull String code,
            @NotNull Object value,
            @NotNull Function<LanguageImpl, TagResolver> toTagResolver
    ) {
        this.code = code;
        this.value = value;
        this.toTagResolver = toTagResolver;
    }

    public static @NotNull PlaceholderImpl string(@NotNull String code, @NotNull Object value) {
        return new PlaceholderImpl(
                code,
                value,
                language -> TagResolver.resolver(code, Tag.inserting(Component.text(Objects.toString(value))))
        );
    }

    public static @NotNull PlaceholderImpl translation(@NotNull String code, @NotNull Translation translation) {
        return new PlaceholderImpl(
                code,
                translation,
                language -> TagResolver.resolver(code, Tag.inserting(language.languageManager().translate(language, translation)))
        );
    }

    public static @NotNull PlaceholderImpl date(@NotNull String code, @NotNull ZonedTime time) {
        return new PlaceholderImpl(
                code,
                time,
                language -> Formatter.date(code, LocalDateTime.ofInstant(Instant.ofEpochMilli(time.millis()), time.zone()))
        );
    }

    public static @NotNull PlaceholderImpl decimal(@NotNull String code, @NotNull double value) {
        return new PlaceholderImpl(
                code,
                value,
                language -> language.languageManager().resolveNumber(language, code, value)
        );
    }

    public static @NotNull PlaceholderImpl number(@NotNull String code, @NotNull long value) {
        return new PlaceholderImpl(
                code,
                value,
                language -> language.languageManager().resolveNumber(language, code, value)
        );
    }

    public static @NotNull PlaceholderImpl translated(@NotNull String code, @NotNull Component component) {
        return new PlaceholderImpl(
                code,
                component,
                language -> TagResolver.resolver(code, Tag.inserting(component))
        );
    }

    @Override
    public @NotNull String code() {
        return this.code;
    }

    @Override
    public @NotNull Object value() {
        return this.value;
    }

    public @NotNull Function<LanguageImpl, TagResolver> toTagResolver() {
        return this.toTagResolver;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (PlaceholderImpl) obj;
        return Objects.equals(this.code, that.code) &&
                Objects.equals(this.value, that.value) &&
                Objects.equals(this.toTagResolver, that.toTagResolver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.code, this.value, this.toTagResolver);
    }

    @Override
    public String toString() {
        return "PlaceholderImpl[" +
                "code=" + this.code + ", " +
                "value=" + this.value + ", " +
                "toTagResolver=" + this.toTagResolver + ']';
    }
}