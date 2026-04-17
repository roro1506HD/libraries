package ovh.roro.libraries.language.impl.data;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import ovh.roro.libraries.language.api.data.LanguageNumberData;

@ApiStatus.Internal
public record LanguageNumberDataImpl(
        @NotNull String groupSeparator,
        @NotNull String decimalSeparator
) implements LanguageNumberData {
}
