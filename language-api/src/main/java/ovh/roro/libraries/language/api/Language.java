package ovh.roro.libraries.language.api;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ovh.roro.libraries.language.api.data.LanguageNumberData;

@ApiStatus.NonExtendable
public interface Language {

    int id();

    @NotNull String name();

    @NotNull String alpha();

    @Nullable Language fallbackLanguage();

    @NotNull String headTexture();

    @NotNull LanguageNumberData numberData();

}
