package ovh.roro.libraries.scoreboard.api.holder;

import org.jetbrains.annotations.ApiStatus;
import ovh.roro.libraries.scoreboard.api.SidebarScoreboard;

@ApiStatus.NonExtendable
public interface ScoreboardHolder {

    SidebarScoreboard getSidebarScoreboard();

    void removeScoreboard();

}
