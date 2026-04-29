package ovh.roro.libraries.scoreboard.api;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.language.api.Placeholder;
import ovh.roro.libraries.language.api.Translatable;
import ovh.roro.libraries.language.api.Translation;

@ApiStatus.NonExtendable
public interface SidebarScoreboard {

    void title(Component objectiveName);

    void title(String translation, Placeholder... placeholders);

    void title(Translatable translatable, Placeholder... placeholders);

    void title(Translation translation);

    void line(int index, Component line);

    void line(int index, String translation, Placeholder... placeholders);

    void line(int index, Translatable translatable, Placeholder... placeholders);

    void line(int index, Translation translation);

    void remove(int index);

    void clear();

    @Nullable Component line(int index);

    @Nullable Translation translation(int index);

    @Nullable Component[] lines();

    @Nullable Translation[] translations();

}
