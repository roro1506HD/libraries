package ovh.roro.libraries.scoreboard.api.holder;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.scoreboard.api.instance.ScoreboardInstance;

import java.util.function.Consumer;

@ApiStatus.NonExtendable
public interface MultiScoreboardHolder extends ScoreboardHolder {

    void setActiveScoreboard(ScoreboardInstance<?> scoreboard);

    <T extends ScoreboardInstance<?>> @Nullable T getActiveScoreboard(Class<T> clazz);

    <T extends ScoreboardInstance<?>> void updateActiveScoreboard(Class<T> clazz, Consumer<T> consumer);

}
