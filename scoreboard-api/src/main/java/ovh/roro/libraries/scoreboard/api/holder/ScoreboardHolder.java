package ovh.roro.libraries.scoreboard.api.holder;

import org.jetbrains.annotations.ApiStatus;
import ovh.roro.libraries.scoreboard.api.SidebarScoreboard;
import ovh.roro.libraries.scoreboard.api.instance.ScoreboardInstance;
import ovh.roro.libraries.scoreboard.api.player.MultiScoreboardPlayerHolder;
import ovh.roro.libraries.scoreboard.api.player.SingleScoreboardPlayerHolder;
import ovh.roro.libraries.scoreboard.impl.holder.MultiScoreboardHolderImpl;
import ovh.roro.libraries.scoreboard.impl.holder.SingleScoreboardHolderImpl;

@ApiStatus.NonExtendable
public interface ScoreboardHolder {

    static <T extends ScoreboardInstance<?>> SingleScoreboardHolder<T> single(SingleScoreboardPlayerHolder<T> player) {
        return new SingleScoreboardHolderImpl<>(player);
    }

    static MultiScoreboardHolder multi(MultiScoreboardPlayerHolder player) {
        return new MultiScoreboardHolderImpl(player);
    }

    SidebarScoreboard sidebarScoreboard();

    void removeScoreboard();

}
