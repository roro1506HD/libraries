package ovh.roro.libraries.language.api;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import ovh.roro.libraries.language.impl.TranslationImpl;

@ApiStatus.NonExtendable
public interface Translation extends Translatable {

    static @NotNull Translation translation(@NotNull String translationKey, @NotNull Placeholder... placeholders) {
        return new TranslationImpl(translationKey, placeholders);
    }

    @NotNull String translationKey();

    @NotNull Placeholder @NotNull [] placeholders();

}
