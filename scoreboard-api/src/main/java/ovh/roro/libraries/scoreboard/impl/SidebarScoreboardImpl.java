package ovh.roro.libraries.scoreboard.impl;

import io.papermc.paper.adventure.PaperAdventure;
import net.kyori.adventure.text.Component;
import net.minecraft.network.chat.numbers.BlankFormat;
import net.minecraft.network.protocol.game.ClientboundResetScorePacket;
import net.minecraft.network.protocol.game.ClientboundSetDisplayObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.scores.DisplaySlot;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.language.api.LanguageManager;
import ovh.roro.libraries.language.api.LanguagePlayerHolder;
import ovh.roro.libraries.language.api.Placeholder;
import ovh.roro.libraries.language.api.Translatable;
import ovh.roro.libraries.language.api.Translation;
import ovh.roro.libraries.scoreboard.api.player.ScoreboardPlayerHolder;
import ovh.roro.libraries.scoreboard.api.SidebarScoreboard;

import java.util.Optional;

@ApiStatus.Internal
public class SidebarScoreboardImpl implements SidebarScoreboard {

    private final ServerPlayer player;
    private final Objective objective;
    private final LanguagePlayerHolder languagePlayer;

    private final @Nullable Component[] lines;
    private final @Nullable Translation[] linesTranslations;

    private Component title;
    private @Nullable Translation titleTranslation;

    private boolean created;

    public SidebarScoreboardImpl(ScoreboardPlayerHolder player) {
        this.player = ((CraftPlayer) player.bukkitPlayer()).getHandle();
        this.objective = this.createObjective();
        this.languagePlayer = player;

        this.lines = new Component[15];
        this.linesTranslations = new Translation[15];

        this.title = Component.empty();
        this.titleTranslation = null;
    }

    private Objective createObjective() {
        return new Objective(
                new Scoreboard(),
                this.player.getGameProfile().name(),
                ObjectiveCriteria.DUMMY,
                net.minecraft.network.chat.Component.empty(),
                ObjectiveCriteria.RenderType.INTEGER,
                false,
                BlankFormat.INSTANCE
        );
    }

    @Override
    public boolean create() {
        if (this.created) {
            return false;
        }

        this.player.connection.send(this.createAddObjectivePacket());
        this.player.connection.send(this.createSetObjectiveSlotPacket());

        int i = 0;
        while (i < this.lines.length) {
            this.sendLine(i++);
        }

        this.created = true;

        return true;
    }

    @Override
    public boolean destroy() {
        if (!this.created) {
            return false;
        }

        this.player.connection.send(this.createRemoveObjectivePacket());

        this.created = false;

        return true;
    }

    @Override
    public void title(Component title) {
        this.title = title;
        this.titleTranslation = null;

        this.objective.setDisplayName(PaperAdventure.asVanilla(title));

        if (this.created) {
            this.player.connection.send(this.createChangeObjectivePacket());
        }
    }

    @Override
    public void title(String translation, Placeholder... placeholders) {
        this.title(Translation.translation(translation, placeholders));
    }

    @Override
    public void title(Translatable translatable, Placeholder... placeholders) {
        this.title(Translation.translation(translatable, placeholders));
    }

    @Override
    public void title(Translation translation) {
        this.title = LanguageManager.languageManager().translate(this.languagePlayer.language(), translation);
        this.titleTranslation = translation;

        this.objective.setDisplayName(PaperAdventure.asVanilla(this.title));

        if (this.created) {
            this.player.connection.send(this.createChangeObjectivePacket());
        }
    }

    @Override
    public Component title() {
        return this.title;
    }

    @Override
    public @Nullable Translation titleTranslation() {
        return this.titleTranslation;
    }

    @Override
    public void line(int index, Component line) {
        if (index > 14 || index < 0) {
            return;
        }

        if (!line.equals(this.lines[index])) {
            this.lines[index] = line;
            this.sendLine(index);
        }

        this.linesTranslations[index] = null;
    }

    @Override
    public void line(int index, String translation, Placeholder... placeholders) {
        this.line(index, Translation.translation(translation, placeholders));
    }

    @Override
    public void line(int index, Translatable translatable, Placeholder... placeholders) {
        this.line(index, Translation.translation(translatable, placeholders));
    }

    @Override
    public void line(int index, Translation translation) {
        if (index > 14 || index < 0) {
            return;
        }

        Component line = LanguageManager.languageManager().translate(this.languagePlayer.language(), translation);
        ;
        if (!line.equals(this.lines[index])) {
            this.lines[index] = line;
            this.sendLine(index);
        }

        this.linesTranslations[index] = translation;
    }

    @Override
    public void remove(int index) {
        if (index > 14 || index < 0 || this.lines[index] == null) {
            return;
        }

        if (this.created) {
            this.player.connection.send(this.createResetScorePacket(this.getScoreInternalName(index)));
        }

        this.lines[index] = null;
        this.linesTranslations[index] = null;
    }

    @Override
    public void clear() {
        for (int i = 0; i < this.lines.length; i++) {
            this.remove(i);
        }
    }

    @Override
    public @Nullable Component line(int index) {
        if (index > 14 || index < 0) {
            return null;
        }

        return this.lines[index];
    }

    @Override
    public @Nullable Translation lineTranslation(int index) {
        if (index > 14 || index < 0) {
            return null;
        }

        return this.linesTranslations[index];
    }

    @Override
    public @Nullable Component[] lines() {
        Component[] lines = new Component[this.lines.length];
        // Create a copy so that modifying this array doesn't mess with the real array
        System.arraycopy(this.lines, 0, lines, 0, this.lines.length);
        return lines;
    }

    @Override
    public @Nullable Translation[] linesTranslations() {
        Translation[] linesTranslations = new Translation[this.lines.length];
        // Create a copy so that modifying this array doesn't mess with the real array
        System.arraycopy(this.linesTranslations, 0, linesTranslations, 0, this.linesTranslations.length);
        return linesTranslations;
    }

    private void sendLine(int index) {
        if (index > 14 || index < 0 || !this.created) {
            return;
        }

        Component line = this.lines[index];

        if (line == null) {
            return;
        }

        this.player.connection.send(this.createModifyScorePacket(
                this.getScoreInternalName(index),
                line,
                14 - index
        ));
    }

    private ClientboundSetObjectivePacket createAddObjectivePacket() {
        return new ClientboundSetObjectivePacket(this.objective, ClientboundSetObjectivePacket.METHOD_ADD);
    }

    private ClientboundSetObjectivePacket createChangeObjectivePacket() {
        return new ClientboundSetObjectivePacket(this.objective, ClientboundSetObjectivePacket.METHOD_CHANGE);
    }

    private ClientboundSetObjectivePacket createRemoveObjectivePacket() {
        return new ClientboundSetObjectivePacket(this.objective, ClientboundSetObjectivePacket.METHOD_REMOVE);
    }

    private ClientboundSetDisplayObjectivePacket createSetObjectiveSlotPacket() {
        return new ClientboundSetDisplayObjectivePacket(DisplaySlot.SIDEBAR, this.objective);
    }

    private ClientboundSetScorePacket createModifyScorePacket(String line, Component component, int score) {
        return new ClientboundSetScorePacket(
                line,
                this.player.getGameProfile().name(),
                score,
                Optional.of(PaperAdventure.asVanilla(component)),
                Optional.empty()
        );
    }

    private ClientboundResetScorePacket createResetScorePacket(String line) {
        return new ClientboundResetScorePacket(line, this.player.getGameProfile().name());
    }

    private String getScoreInternalName(int index) {
        return String.valueOf('a' + index);
    }
}
