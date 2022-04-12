package net.timenation.specialpvp.listener;

import net.timenation.specialpvp.SpecialPvP;
import net.timenation.timespigotapi.TimeSpigotAPI;
import net.timenation.timespigotapi.manager.ItemManager;
import net.timenation.timespigotapi.manager.game.gamestates.GameState;
import net.timenation.timespigotapi.manager.language.I18n;
import net.timenation.timespigotapi.timeplayer.TimePlayer;
import net.timenation.timespigotapi.timeplayer.TimeStatsPlayer;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void handlePlayerDeath(PlayerDeathEvent event) {
        var player = event.getPlayer();
        TimePlayer timePlayer = TimeSpigotAPI.getInstance().getTimePlayerManager().getTimePlayer(player);
        TimeStatsPlayer timeStatsPlayer = TimeSpigotAPI.getInstance().getTimeStatsPlayerManager().getTimeStatsPlayer(player, "SpecialPvP");

        timeStatsPlayer.setDeaths(timeStatsPlayer.getDeaths() + 1);

        event.setDeathMessage(null);
        event.getDrops().clear();

        SpecialPvP.getInstance().getIngameManager().sendIngameScoreboard(player, SpecialPvP.getInstance());
        player.setMetadata("lives", new FixedMetadataValue(SpecialPvP.getInstance(), player.getMetadata("lives").get(0).asInt() - 1));
        player.getKiller().getInventory().addItem(new ItemManager(Material.ENCHANTED_GOLDEN_APPLE).build());

        if (player.getMetadata("lives").get(0).asInt() != 0) {
            Player killer = player.getKiller();
            TimePlayer timeKiller = TimeSpigotAPI.getInstance().getTimePlayerManager().getTimePlayer(killer);
            TimeStatsPlayer timeStatsKiller = TimeSpigotAPI.getInstance().getTimeStatsPlayerManager().getTimeStatsPlayer(killer, "SpecialPvP");

            Bukkit.getScheduler().runTaskLater(SpecialPvP.getInstance(), () -> {
                player.spigot().respawn();
                SpecialPvP.getInstance().getIngameManager().teleportPlayerToLocation(player, ThreadLocalRandom.current().nextInt(1, 4), SpecialPvP.getInstance());
                player.sendMessage(I18n.format(player, SpecialPvP.getInstance().getPrefix(), "api.game.messages.specialpvp.remaininglives", player.getMetadata("lives").get(0).asString()));
                player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_INFECT, 10F, 1F);
                player.getInventory().setContents(SpecialPvP.getInstance().getKitManager().getKitFromPlayer(player).getKitObject().getContent());
                player.getInventory().setArmorContents(SpecialPvP.getInstance().getKitManager().getKitFromPlayer(player).getKitObject().getArmor());
                player.getInventory().setItem(7, new ItemManager(Material.END_ROD).setDisplayName(I18n.format(player, "api.game.specialpvp.item.lightning")).build());
                player.getInventory().setItem(8, new ItemManager(Material.IRON_AXE).setDisplayName(I18n.format(player, "api.game.specialpvp.item.thor_hammer")).build());
            }, 5);

            Bukkit.getOnlinePlayers().forEach(current -> {
                current.sendMessage(I18n.format(player, SpecialPvP.getInstance().getPrefix(), "api.game.messages.player.killedbyplayer", TimeSpigotAPI.getInstance().getRankManager().getPlayersRank(player.getUniqueId()).getPlayersNameWithRankColor(player.getUniqueId()), TimeSpigotAPI.getInstance().getRankManager().getPlayersRank(killer.getUniqueId()).getPlayersNameWithRankColor(killer.getUniqueId())));
                SpecialPvP.getInstance().getIngameManager().sendIngameScoreboard(player, SpecialPvP.getInstance());
            });

            timeStatsKiller.setKills(timeStatsKiller.getKills() + 1);
            killer.playSound(killer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 0);
        }

        if (player.getMetadata("lives").get(0).asInt() < 1) {
            Player killer = player.getKiller();
            TimePlayer timeKiller = TimeSpigotAPI.getInstance().getTimePlayerManager().getTimePlayer(killer);
            TimeStatsPlayer timeStatsKiller = TimeSpigotAPI.getInstance().getTimeStatsPlayerManager().getTimeStatsPlayer(killer, "SpecialPvP");
            SpecialPvP.getInstance().getPlayers().remove(player);
            SpecialPvP.getInstance().getSpecatePlayers().add(player);

            Bukkit.getScheduler().runTaskLater(SpecialPvP.getInstance(), () -> {
                player.spigot().respawn();
                player.teleport(new Location(Bukkit.getWorld("world"), 111.5, 114.00, -262.5, -45, 0));
                player.setAllowFlight(true);
                TimeSpigotAPI.getInstance().getTablistManager().registerRankTeam(player, "§c✗ §8» ", "", ChatColor.GRAY, 18);
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 1, 0);
                player.getInventory().setItem(1, new ItemManager(Material.COMPASS, 1).setDisplayName(I18n.format(player, "api.game.item.teleporter")).build());
                SpecialPvP.getInstance().getDefaultGameQuitItem().setItem(player);

            }, 10);

            Bukkit.getOnlinePlayers().forEach(current -> {
                current.hidePlayer(player);
                current.sendMessage(I18n.format(player, SpecialPvP.getInstance().getPrefix(), "api.game.messages.player.killedbyplayerfinal", TimeSpigotAPI.getInstance().getRankManager().getPlayersRank(player.getUniqueId()).getPlayersNameWithRankColor(player.getUniqueId()), TimeSpigotAPI.getInstance().getRankManager().getPlayersRank(killer.getUniqueId()).getPlayersNameWithRankColor(killer.getUniqueId())));
                SpecialPvP.getInstance().getIngameManager().sendIngameScoreboard(current, SpecialPvP.getInstance());
            });

            killer.sendMessage(I18n.format(player, SpecialPvP.getInstance().getPrefix(), "api.game.actionbar.playerkilledplayer", TimeSpigotAPI.getInstance().getRankManager().getPlayersRank(player.getUniqueId()).getPlayersNameWithRankColor(player.getUniqueId())));
            timeKiller.setCoins(timeKiller.getCoins() + 100);
            timeStatsKiller.setKills(timeStatsKiller.getKills() + 1);
            killer.playSound(killer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 0);
        }

        if (SpecialPvP.getInstance().getPlayers().size() == 1) {
            SpecialPvP.getInstance().getCountdownManager().startEndCountdown();
            SpecialPvP.getInstance().setGameState(GameState.ENDING);

            TimePlayer timeWinner = TimeSpigotAPI.getInstance().getTimePlayerManager().getTimePlayer(SpecialPvP.getInstance().getPlayers().get(0));
            TimeStatsPlayer timeStatsWinner = TimeSpigotAPI.getInstance().getTimeStatsPlayerManager().getTimeStatsPlayer(SpecialPvP.getInstance().getPlayers().get(0), "SpecialPvP");
            Player winner = SpecialPvP.getInstance().getPlayers().get(0);

            timeWinner.setCoins(timeWinner.getCoins() + 300);
            timeStatsWinner.setWins(timeStatsWinner.getWins() + 1);
            winner.sendTitle(I18n.format(winner, "api.game.title.win.top"), I18n.format(winner, "api.game.title.win.bottom"));
            winner.sendMessage(I18n.format(winner, SpecialPvP.getInstance().getPrefix(), "api.game.messages.playerhaswongame", 300));

            Bukkit.getOnlinePlayers().forEach(current -> {
                current.teleport(new Location(Bukkit.getWorld("world"), 111.5, 114.00, -262.5, -45, 0));
                if (current != Bukkit.getPlayer(timeWinner.getPlayerUuid())) {
                    current.playSound(current.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 0);
                }
                current.getInventory().clear();
                SpecialPvP.getInstance().getDefaultGameQuitItem().setItem(current);
                if (winner != current)
                    current.sendTitle(I18n.format(current, "api.game.title.loose.top"), I18n.format(current, "api.game.title.loose.bottom", (Object) TimeSpigotAPI.getInstance().getRankManager().getPlayersRank(winner.getUniqueId()).getPlayersNameWithRankColor(winner.getUniqueId())));
                current.showPlayer(current);
                SpecialPvP.getInstance().getScoreboardManager().sendEndScoreboardToPlayer(current, winner);
                if(current != winner) TimeSpigotAPI.getInstance().getTimeStatsPlayerManager().getTimeStatsPlayer(current, "SpecialPvP").setLooses(TimeSpigotAPI.getInstance().getTimeStatsPlayerManager().getTimeStatsPlayer(current, "SpecialPvP").getLooses() + 1);
                TimeSpigotAPI.getInstance().getTimePlayerManager().updateTimePlayer(TimeSpigotAPI.getInstance().getTimePlayerManager().getTimePlayer(current));
            });
        }
    }

}
