package ovh.roro.libraries.scoreboard.api.holder;

import org.jetbrains.annotations.ApiStatus;
import ovh.roro.libraries.scoreboard.api.instance.ScoreboardInstance;

import java.util.function.Consumer;

@ApiStatus.NonExtendable
public interface SingleScoreboardHolder<T extends ScoreboardInstance<?>> extends ScoreboardHolder {

    void setActiveScoreboard(T scoreboard);

    T getActiveScoreboard(Class<T> clazz);

    void updateActiveScoreboard(Class<T> clazz, Consumer<T> consumer);

}
