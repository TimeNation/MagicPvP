package net.timenation.magicpvp.listener;

import net.timenation.magicpvp.MagicPvP;
import net.timenation.timespigotapi.TimeSpigotAPI;
import net.timenation.timespigotapi.manager.game.gamestates.GameState;
import net.timenation.timespigotapi.manager.language.I18n;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void handlePlayerQuit(PlayerQuitEvent event) {
        var player = event.getPlayer();
        TimeSpigotAPI.getInstance().getTimeStatsPlayerManager().updateTimeStatsPlayer(TimeSpigotAPI.getInstance().getTimeStatsPlayerManager().getTimeStatsPlayer(player, "SpecialPvP"));

        if (MagicPvP.getInstance().getGameState().equals(GameState.LOBBY) || MagicPvP.getInstance().getGameState().equals(GameState.STARTING)) {
            Bukkit.getOnlinePlayers().forEach(current -> {
                current.sendMessage(I18n.format(player, MagicPvP.getInstance().getPrefix(), "api.game.messages.quit", TimeSpigotAPI.getInstance().getRankManager().getPlayersRank(player.getUniqueId()).getPlayersRankAndName(player.getUniqueId()), Bukkit.getOnlinePlayers().size(), Bukkit.getMaxPlayers()));
            });

            if(Bukkit.getOnlinePlayers().size() - 1 < MagicPvP.getInstance().getNeededPlayers()) {
                MagicPvP.getInstance().getCountdownManager().stopCountdown();
            }
        } else if (MagicPvP.getInstance().getGameState() == GameState.ENDING) {
            if (Bukkit.getOnlinePlayers().size() <= 1) {
                Bukkit.shutdown();
            }
        }
    }

}
