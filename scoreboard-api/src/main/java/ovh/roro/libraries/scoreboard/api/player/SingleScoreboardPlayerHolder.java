package ovh.roro.libraries.scoreboard.api.player;

import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.scoreboard.api.holder.SingleScoreboardHolder;
import ovh.roro.libraries.scoreboard.api.instance.ScoreboardInstance;

import java.util.function.Consumer;

public interface SingleScoreboardPlayerHolder<T extends ScoreboardInstance<?>> extends ScoreboardPlayerHolder<SingleScoreboardHolder<T>> {

    default void setActiveScoreboard(T scoreboard) {
        this.scoreboardHolder().setActiveScoreboard(scoreboard);
    }

    default @Nullable T getActiveScoreboard() {
        return this.scoreboardHolder().getActiveScoreboard();
    }

    default void updateActiveScoreboard(Consumer<T> consumer) {
        this.scoreboardHolder().updateActiveScoreboard(consumer);
    }
}
