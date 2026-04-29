package ovh.roro.libraries.scoreboard.api.instance;

import net.kyori.adventure.text.Component;
import ovh.roro.libraries.language.api.Placeholder;
import ovh.roro.libraries.language.api.Translatable;
import ovh.roro.libraries.language.api.Translation;
import ovh.roro.libraries.scoreboard.api.ScoreboardPlayerHolder;
import ovh.roro.libraries.scoreboard.api.SidebarScoreboard;

public abstract class ScoreboardInstance<T extends ScoreboardPlayerHolder> {

    protected final T player;

    private final SidebarScoreboard scoreboard;

    private int index;

    protected ScoreboardInstance(T player) {
        this.player = player;
        this.scoreboard = player.scoreboardHolder().getSidebarScoreboard();
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

    protected void appendLine(Component component) {
        this.scoreboard.line(this.index++, component);
    }

    protected void appendLine(String translation, Placeholder... placeholders) {
        this.scoreboard.line(this.index++, translation, placeholders);
    }

    protected void appendLine(Translatable translatable, Placeholder... placeholders) {
        this.scoreboard.line(this.index++, translatable, placeholders);
    }

    protected void appendLine(Translation translation) {
        this.scoreboard.line(this.index++, translation);
    }

    protected void appendEmptyLine() {
        this.appendLine(Component.empty());
    }
}
