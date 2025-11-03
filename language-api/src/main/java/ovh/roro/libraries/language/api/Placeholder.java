package ovh.roro.libraries.language.api;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import ovh.roro.libraries.language.impl.PlaceholderImpl;

import java.time.temporal.TemporalAccessor;

@ApiStatus.NonExtendable
public interface Placeholder {

    static @NotNull Placeholder string(@NotNull String code, @NotNull Object value) {
        return PlaceholderImpl.string(code, value);
    }

    static @NotNull Placeholder translation(@NotNull String code, @NotNull Translation translation) {
        return PlaceholderImpl.translation(code, translation);
    }

    static @NotNull Placeholder date(@NotNull String code, @NotNull ZonedTime time) {
        return PlaceholderImpl.date(code, time);
    }

    static @NotNull Placeholder decimal(@NotNull String code, @NotNull double d) {
        return PlaceholderImpl.decimal(code, d);
    }

    static @NotNull Placeholder number(@NotNull String code, @NotNull long l) {
        return PlaceholderImpl.number(code, l);
    }

    static @NotNull Placeholder translated(@NotNull String code, @NotNull Component component) {
        return PlaceholderImpl.translated(code, component);
    }

    @NotNull String code();

    @NotNull Object value();

}
