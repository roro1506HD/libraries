package ovh.roro.libraries.language.impl;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ovh.roro.libraries.language.api.Language;
import ovh.roro.libraries.language.api.data.LanguageNumberData;

import java.util.Map;

@ApiStatus.Internal
public class LanguageImpl implements Language {

    private final @NotNull LanguageManagerImpl languageManager;

    private final int id;
    private final @NotNull String name;
    private final @NotNull String alpha;
    private final @Nullable String fallbackLanguage;
    private final @NotNull String headTexture;
    private final @NotNull LanguageNumberData numberData;

    private final @NotNull Map<String, String> translations;

    public LanguageImpl(
            @NotNull LanguageManagerImpl languageManager,
            int id,
            @NotNull String name,
            @NotNull String alpha,
            @Nullable String fallbackLanguage,
            @NotNull String headTexture,
            @NotNull LanguageNumberData numberData
    ) {
        this.languageManager = languageManager;

        this.id = id;
        this.name = name;
        this.alpha = alpha;
        this.fallbackLanguage = fallbackLanguage;
        this.headTexture = headTexture;
        this.numberData = numberData;

        this.translations = new Object2ObjectOpenHashMap<>();
    }

    public @NotNull LanguageManagerImpl languageManager() {
        return this.languageManager;
    }

    @Override
    public int id() {
        return this.id;
    }

    @Override
    public @NotNull String name() {
        return this.name;
    }

    @Override
    public @NotNull String alpha() {
        return this.alpha;
    }

    @Override
    public @Nullable Language fallbackLanguage() {
        if (this.fallbackLanguage == null) {
            return null;
        }

        return this.languageManager.language(this.fallbackLanguage);
    }

    @Override
    public @NotNull String headTexture() {
        return this.headTexture;
    }

    @Override
    public @NotNull LanguageNumberData numberData() {
        return this.numberData;
    }

    public @NotNull Map<String, String> translations() {
        return this.translations;
    }
}
