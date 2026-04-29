package ovh.roro.libraries.scoreboard.api.player;

import org.bukkit.entity.Player;
import ovh.roro.libraries.language.api.LanguagePlayerHolder;
import ovh.roro.libraries.scoreboard.api.holder.ScoreboardHolder;

public interface ScoreboardPlayerHolder<T extends ScoreboardHolder> extends LanguagePlayerHolder {

    T scoreboardHolder();

    Player bukkitPlayer();

}
