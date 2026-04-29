package ovh.roro.libraries.scoreboard.impl.holder;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.scoreboard.api.SidebarScoreboard;
import ovh.roro.libraries.scoreboard.api.holder.ScoreboardHolder;
import ovh.roro.libraries.scoreboard.api.player.ScoreboardPlayerHolder;
import ovh.roro.libraries.scoreboard.impl.SidebarScoreboardImpl;

@ApiStatus.Internal
public abstract class AbstractScoreboardHolder<T extends ScoreboardPlayerHolder<?>> implements ScoreboardHolder {

    private final T player;

    private @Nullable SidebarScoreboard scoreboard;

    public AbstractScoreboardHolder(T player) {
        this.player = player;
    }

    @Override
    public SidebarScoreboard sidebarScoreboard() {
        if (this.scoreboard == null) {
            this.scoreboard = new SidebarScoreboardImpl(this.player);
        }

        this.scoreboard.create();

        return this.scoreboard;
    }

    @Override
    public void removeScoreboard() {
        if (this.scoreboard != null) {
            this.scoreboard.destroy();
            this.scoreboard = null;
        }
    }

    protected void clearScoreboardLines() {
        if (this.scoreboard != null) {
            this.scoreboard.clear();
        }
    }
}
