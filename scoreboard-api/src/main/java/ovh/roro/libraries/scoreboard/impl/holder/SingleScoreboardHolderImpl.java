package ovh.roro.libraries.scoreboard.impl.holder;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.scoreboard.api.holder.SingleScoreboardHolder;
import ovh.roro.libraries.scoreboard.api.instance.ScoreboardInstance;
import ovh.roro.libraries.scoreboard.api.player.SingleScoreboardPlayerHolder;

import java.util.function.Consumer;

@ApiStatus.Internal
public class SingleScoreboardHolderImpl<T extends ScoreboardInstance<?>> extends AbstractScoreboardHolder<SingleScoreboardPlayerHolder<T>> implements SingleScoreboardHolder<T> {

    private @Nullable T activeScoreboard;

    public SingleScoreboardHolderImpl(SingleScoreboardPlayerHolder<T> player) {
        super(player);
    }

    @Override
    public void setActiveScoreboard(T scoreboard) {
        this.clearScoreboardLines();

        this.activeScoreboard = scoreboard;
    }

    @Override
    public @Nullable T getActiveScoreboard() {
        return this.activeScoreboard;
    }

    @Override
    public void updateActiveScoreboard(Consumer<T> consumer) {
        if (this.activeScoreboard != null) {
            consumer.accept(this.activeScoreboard);
        }
    }
}
