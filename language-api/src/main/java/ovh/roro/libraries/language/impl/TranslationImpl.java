package ovh.roro.libraries.language.impl;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import ovh.roro.libraries.language.api.Placeholder;
import ovh.roro.libraries.language.api.Translation;

@ApiStatus.Internal
public record TranslationImpl(
        @NotNull String translationKey,
        @NotNull Placeholder... placeholders
) implements Translation {
}
