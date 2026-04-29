package ovh.roro.libraries.scoreboard.api.holder;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.scoreboard.api.instance.ScoreboardInstance;

import java.util.function.Consumer;

@ApiStatus.NonExtendable
public interface SingleScoreboardHolder<T extends ScoreboardInstance<?>> extends ScoreboardHolder {

    void setActiveScoreboard(T scoreboard);

    @Nullable T getActiveScoreboard();

    void updateActiveScoreboard(Consumer<T> consumer);

}
