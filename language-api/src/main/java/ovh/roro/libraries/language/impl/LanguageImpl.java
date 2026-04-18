package ovh.roro.libraries.language.impl;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.language.api.Language;
import ovh.roro.libraries.language.api.data.LanguageNumberData;

import java.util.Map;

@ApiStatus.Internal
public class LanguageImpl implements Language {

    private final LanguageManagerImpl languageManager;

    private final int id;
    private final String name;
    private final String alpha;
    private final @Nullable String fallbackLanguage;
    private final LanguageNumberData numberData;

    private final Map<String, String> translations;

    public LanguageImpl(
            LanguageManagerImpl languageManager,
            int id,
            String name,
            String alpha,
            @Nullable String fallbackLanguage,
            LanguageNumberData numberData
    ) {
        this.languageManager = languageManager;

        this.id = id;
        this.name = name;
        this.alpha = alpha;
        this.fallbackLanguage = fallbackLanguage;
        this.numberData = numberData;

        this.translations = new Object2ObjectOpenHashMap<>();
    }

    public LanguageManagerImpl languageManager() {
        return this.languageManager;
    }

    @Override
    public int id() {
        return this.id;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String alpha() {
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
    public LanguageNumberData numberData() {
        return this.numberData;
    }

    public Map<String, String> translations() {
        return this.translations;
    }
}
