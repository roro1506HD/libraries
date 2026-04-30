package ovh.roro.libraries.scoreboard.impl.holder;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.scoreboard.api.holder.MultiScoreboardHolder;
import ovh.roro.libraries.scoreboard.api.instance.ScoreboardInstance;
import ovh.roro.libraries.scoreboard.api.player.MultiScoreboardPlayerHolder;

import java.util.function.Consumer;

@ApiStatus.Internal
public class MultiScoreboardHolderImpl extends AbstractScoreboardHolder<MultiScoreboardPlayerHolder> implements MultiScoreboardHolder {

    private @Nullable ScoreboardInstance<?> activeScoreboard;

    public MultiScoreboardHolderImpl(MultiScoreboardPlayerHolder player) {
        super(player);
    }

    @Override
    public void setActiveScoreboard(ScoreboardInstance<?> scoreboard) {
        this.clearScoreboardLines();

        this.activeScoreboard = scoreboard;
        this.activeScoreboard.init();
    }

    @Override
    public @Nullable <T extends ScoreboardInstance<?>> T getActiveScoreboard(Class<T> clazz) {
        if (!clazz.isInstance(this.activeScoreboard)) {
            return null;
        }

        return clazz.cast(this.activeScoreboard);
    }

    @Override
    public <T extends ScoreboardInstance<?>> void updateActiveScoreboard(Class<T> clazz, Consumer<T> consumer) {
        T scoreboard = this.getActiveScoreboard(clazz);

        if (scoreboard != null) {
            consumer.accept(scoreboard);
        }
    }
}
