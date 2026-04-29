package ovh.roro.libraries.scoreboard.api.instance;

import net.kyori.adventure.text.Component;
import ovh.roro.libraries.language.api.Placeholder;
import ovh.roro.libraries.language.api.Translatable;
import ovh.roro.libraries.language.api.Translation;
import ovh.roro.libraries.scoreboard.api.player.ScoreboardPlayerHolder;
import ovh.roro.libraries.scoreboard.api.holder.ScoreboardHolder;

public abstract class ScoreboardInstance<T extends ScoreboardPlayerHolder<?>> {

    private final T player;
    private final ScoreboardHolder scoreboardHolder;

    private int index;

    protected ScoreboardInstance(T player) {
        this.player = player;
        this.scoreboardHolder = player.scoreboardHolder();
    }

    public abstract void init();

    protected T player() {
        return this.player;
    }

    protected int index() {
        return this.index;
    }

    protected void index(int index) {
        this.index = index;
    }

    protected void title(Component title) {
        this.scoreboardHolder.sidebarScoreboard().title(title);
    }

    protected void title(String translation, Placeholder... placeholders) {
        this.scoreboardHolder.sidebarScoreboard().title(translation, placeholders);
    }

    protected void title(Translatable translatable, Placeholder... placeholders) {
        this.scoreboardHolder.sidebarScoreboard().title(translatable, placeholders);
    }

    protected void title(Translation translation) {
        this.scoreboardHolder.sidebarScoreboard().title(translation);
    }

    protected void appendLine(Component line) {
        this.scoreboardHolder.sidebarScoreboard().line(this.index++, line);
    }

    protected void appendLine(String translation, Placeholder... placeholders) {
        this.scoreboardHolder.sidebarScoreboard().line(this.index++, translation, placeholders);
    }

    protected void appendLine(Translatable translatable, Placeholder... placeholders) {
        this.scoreboardHolder.sidebarScoreboard().line(this.index++, translatable, placeholders);
    }

    protected void appendLine(Translation translation) {
        this.scoreboardHolder.sidebarScoreboard().line(this.index++, translation);
    }

    protected void appendEmptyLine() {
        this.appendLine(Component.empty());
    }
}
