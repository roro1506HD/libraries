package ovh.roro.libraries.language.impl;

import org.jetbrains.annotations.ApiStatus;
import ovh.roro.libraries.language.api.Placeholder;
import ovh.roro.libraries.language.api.Translation;

@ApiStatus.Internal
public record TranslationImpl(
        String translationKey,
        Placeholder... placeholders
) implements Translation {
}
