package ovh.roro.libraries.language.api;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import ovh.roro.libraries.language.impl.ZonedTimeImpl;

import java.time.ZoneId;
import java.util.TimeZone;

@ApiStatus.NonExtendable
public interface ZonedTime {

    static @NotNull ZonedTime of(long millis) {
        return ZonedTime.of(millis, TimeZone.getTimeZone("GMT").toZoneId());
    }

    static @NotNull ZonedTime of(long millis, @NotNull ZoneId zone) {
        return new ZonedTimeImpl(millis, zone);
    }

    long millis();

    @NotNull ZoneId zone();

}
