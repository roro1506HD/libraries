package ovh.roro.libraries.language.impl;

import org.jetbrains.annotations.ApiStatus;
import ovh.roro.libraries.language.api.ZonedTime;

import java.time.ZoneId;

@ApiStatus.Internal
public record ZonedTimeImpl(
        long millis,
        ZoneId zone
) implements ZonedTime {
}
