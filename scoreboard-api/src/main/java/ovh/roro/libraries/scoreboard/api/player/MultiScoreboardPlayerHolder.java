package ovh.roro.libraries.scoreboard.api.player;

import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.scoreboard.api.holder.MultiScoreboardHolder;
import ovh.roro.libraries.scoreboard.api.instance.ScoreboardInstance;

import java.util.function.Consumer;

public interface MultiScoreboardPlayerHolder extends ScoreboardPlayerHolder<MultiScoreboardHolder> {

    default void setActiveScoreboard(ScoreboardInstance<?> scoreboard) {
        this.scoreboardHolder().setActiveScoreboard(scoreboard);
    }

    default <T extends ScoreboardInstance<?>> @Nullable T getActiveScoreboard(Class<T> clazz) {
        return this.scoreboardHolder().getActiveScoreboard(clazz);
    }

    default <T extends ScoreboardInstance<?>> void updateActiveScoreboard(Class<T> clazz, Consumer<T> consumer) {
        this.scoreboardHolder().updateActiveScoreboard(clazz, consumer);
    }
}
