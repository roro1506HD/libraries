package ovh.roro.libraries.language.impl.data;

import org.jetbrains.annotations.ApiStatus;
import ovh.roro.libraries.language.api.data.LanguageNumberData;

@ApiStatus.Internal
public record LanguageNumberDataImpl(
        String groupSeparator,
        String decimalSeparator
) implements LanguageNumberData {
}
