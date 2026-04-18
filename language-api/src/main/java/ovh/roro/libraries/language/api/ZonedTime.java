package ovh.roro.libraries.language.api;

import org.jetbrains.annotations.ApiStatus;
import ovh.roro.libraries.language.impl.ZonedTimeImpl;

import java.time.ZoneId;
import java.util.TimeZone;

/**
 * Represents a pair of a time in milliseconds and a timezone.
 * Used for zone-specific dates and times for placeholders
 *
 * @see Placeholder#date(String, ZonedTime)
 */
@ApiStatus.NonExtendable
public interface ZonedTime {

    /**
     * Creates a ZonedTime object of the provided milliseconds, with GMT as default timezone.
     *
     * @param millis the time
     * @return a new ZonedTime
     */
    static ZonedTime of(long millis) {
        return ZonedTime.of(millis, TimeZone.getTimeZone("GMT").toZoneId());
    }

    /**
     * Creates a ZonedTime object of the provided milliseconds and the provided timezone
     *
     * @param millis the time
     * @param zone   the timezone
     * @return a new ZonedTime
     */
    static ZonedTime of(long millis, ZoneId zone) {
        return new ZonedTimeImpl(millis, zone);
    }

    /**
     * Returns the time of this object
     *
     * @return the time in milliseconds
     */
    long millis();

    /**
     * Returns the timezone of this object
     *
     * @return the timezone
     */
    ZoneId zone();

}
