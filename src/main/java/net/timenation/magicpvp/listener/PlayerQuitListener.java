package net.timenation.magicpvp.listener;

import net.timenation.magicpvp.MagicPvP;
import net.timenation.timespigotapi.TimeSpigotAPI;
import net.timenation.timespigotapi.manager.game.gamestates.GameState;
import net.timenation.timespigotapi.manager.language.I18n;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void handlePlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        TimeSpigotAPI.getInstance().getTimeStatsPlayerManager().updateTimeStatsPlayer(TimeSpigotAPI.getInstance().getTimeStatsPlayerManager().getTimeStatsPlayer(player, MagicPvP.getInstance().getGameName()));

        if(MagicPvP.getInstance().getGameState().equals(GameState.LOBBY) || MagicPvP.getInstance().getGameState().equals(GameState.STARTING)) {
            Bukkit.getOnlinePlayers().forEach(current -> {
                current.sendMessage(I18n.format(player, MagicPvP.getInstance().getPrefix(), "api.game.messages.quit", TimeSpigotAPI.getInstance().getRankManager().getPlayersRank(player.getUniqueId()).getPlayersRankAndName(player.getUniqueId()), Bukkit.getOnlinePlayers().size(), Bukkit.getMaxPlayers()));
            });

            if(Bukkit.getOnlinePlayers().size() - 1 < MagicPvP.getInstance().getNeededPlayers()) {
                MagicPvP.getInstance().getCountdownManager().stopCountdown();
            }
        } else if(MagicPvP.getInstance().getGameState() == GameState.ENDING) {
            if(Bukkit.getOnlinePlayers().size() <= 1) {
                Bukkit.shutdown();
            }
        } else {
            MagicPvP.getInstance().getPlayers().remove(player);
            TimeSpigotAPI.getInstance().getTimeStatsPlayerManager().getTimeStatsPlayer(player, "SkyWars").setDeaths(TimeSpigotAPI.getInstance().getTimeStatsPlayerManager().getTimeStatsPlayer(player, "SkyWars").getDeaths() + 1);
            TimeSpigotAPI.getInstance().getTimeStatsPlayerManager().getTimeStatsPlayer(player, "SkyWars").setLooses(TimeSpigotAPI.getInstance().getTimeStatsPlayerManager().getTimeStatsPlayer(player, "SkyWars").getLooses() + 1);
            if(Bukkit.getOnlinePlayers().size() - 1 == 1 || MagicPvP.getInstance().getPlayers().size() == 1) {
                Bukkit.getOnlinePlayers().forEach(current -> {
                    current.teleport(new Location(Bukkit.getWorld("world"), 111.5, 114.00, -262.5, -45, 0));
                    current.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 0);
                    current.getInventory().clear();
                    current.setGameMode(GameMode.SURVIVAL);
                    current.sendTitle(I18n.format(current, "api.game.title.loose.top"), I18n.format(current, "api.game.title.loose.bottom"));
                    MagicPvP.getInstance().getDefaultGameQuitItem().setItem(current);
                    TimeSpigotAPI.getInstance().getTimePlayerManager().updateTimePlayer(TimeSpigotAPI.getInstance().getTimePlayerManager().getTimePlayer(current));
                });

                for(Player winner : MagicPvP.getInstance().getPlayers()) {
                    winner.sendTitle(I18n.format(player, "api.game.title.win.top"), I18n.format(player, "api.game.title.win.bottom"));
                    winner.sendMessage(I18n.format(player, MagicPvP.getInstance().getPrefix(), "api.game.messages.playerhaswongame", 300));
                    TimeSpigotAPI.getInstance().getTimePlayerManager().getTimePlayer(winner).setCoins(TimeSpigotAPI.getInstance().getTimePlayerManager().getTimePlayer(winner).getCoins() + 300);
                    TimeSpigotAPI.getInstance().getTimeStatsPlayerManager().getTimeStatsPlayer(player, "SkyWars").setWins(TimeSpigotAPI.getInstance().getTimeStatsPlayerManager().getTimeStatsPlayer(player, "SkyWars").getWins() + 1);
                }

                MagicPvP.getInstance().setGameState(GameState.ENDING);
                MagicPvP.getInstance().getCountdownManager().startEndCountdown();
            }
        }
    }
}
