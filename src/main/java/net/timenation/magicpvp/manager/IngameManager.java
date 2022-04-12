package net.timenation.magicpvp.manager;

import net.timenation.timespigotapi.manager.game.TimeGame;
import net.timenation.timespigotapi.manager.language.I18n;
import net.timenation.timespigotapi.manager.scoreboard.ScoreboardBuilder;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class IngameManager {

    public void sendIngameScoreboard(Player player, TimeGame timeGame) {
        ScoreboardBuilder scoreboardBuilder = new ScoreboardBuilder(player);

        scoreboardBuilder.setTitle(I18n.format(player, "api.game.scoreboard.title", (Object) timeGame.getColor(), timeGame.getGameName()));

        scoreboardBuilder.setLine(0, "§r§8§m                        ");
        scoreboardBuilder.setLine(1, "§1");
        scoreboardBuilder.setLine(2, timeGame.getSecoundColor() + " ❤ §8| " + I18n.format(player, "api.game.scoreboard.lives"));
        scoreboardBuilder.setLine(3, "   §8» §r§r" + timeGame.getColor() + player.getMetadata("lives").get(0).asInt());
        scoreboardBuilder.setLine(4, "§2");
        scoreboardBuilder.setLine(5, timeGame.getSecoundColor() + " ☁ §8| " + I18n.format(player, "api.game.scoreboard.map"));
        scoreboardBuilder.setLine(6, "   §8» §r" + timeGame.getColor() + timeGame.getGameMap());
        scoreboardBuilder.setLine(7, "§3");
        scoreboardBuilder.setLine(8, timeGame.getSecoundColor() + " ☄ §8| " + I18n.format(player, "api.game.scoreboard.kit"));
        scoreboardBuilder.setLine(9, "   §8» " + timeGame.getColor() + timeGame.getPlayerKit().get(player));
        scoreboardBuilder.setLine(10, "§4");
        scoreboardBuilder.setLine(11, timeGame.getSecoundColor() + " ☺ §8| " + I18n.format(player, "api.game.scoreboard.players"));
        scoreboardBuilder.setLine(12, "   §8» " + timeGame.getColor() + timeGame.getPlayers().size());
        scoreboardBuilder.setLine(13, "§5");
        scoreboardBuilder.setLine(14, "§8§m                        ");
    }

    public void teleportPlayerToLocation(Player player, int tpCount, TimeGame timeGame) {
        player.teleport(new Location(timeGame.getWorld(), timeGame.getConfigManager().getDouble("team" + tpCount + ".x"), timeGame.getConfigManager().getDouble("team" + tpCount + ".y"), timeGame.getConfigManager().getDouble("team" + tpCount + ".z"), timeGame.getConfigManager().getFloat("team" + tpCount + ".yaw"), 0));
    }
}
