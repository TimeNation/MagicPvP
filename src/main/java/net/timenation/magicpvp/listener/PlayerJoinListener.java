package net.timenation.magicpvp.listener;

import net.timenation.magicpvp.MagicPvP;
import net.timenation.magicpvp.manager.kits.KitType;
import net.timenation.timespigotapi.TimeSpigotAPI;
import net.timenation.timespigotapi.manager.ItemManager;
import net.timenation.timespigotapi.manager.game.gamestates.GameState;
import net.timenation.timespigotapi.manager.language.I18n;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void handlePlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (MagicPvP.getInstance().getGameState().equals(GameState.LOBBY) || MagicPvP.getInstance().getGameState().equals(GameState.STARTING)) {
            player.teleport(new Location(Bukkit.getWorld("world"), 111.5, 114.00, -262.5, -45, 0));
            player.getInventory().setItem(2, new ItemManager(MagicPvP.getInstance().getCountdownManager().countdown > 10 ? Material.BARREL : Material.BARRIER, 1).setDisplayName(I18n.format(player, "api.game.item.kitvoting", MagicPvP.getInstance().getPrefix())).build());
            MagicPvP.getInstance().getPlayerKit().put(player, MagicPvP.getInstance().getColor() + "Voting...");

            if (Bukkit.getOnlinePlayers().size() == MagicPvP.getInstance().getNeededPlayers()) {
                MagicPvP.getInstance().getCountdownManager().startCountdown();
            }

            Bukkit.getOnlinePlayers().forEach(current -> {
                current.sendMessage(I18n.format(current, MagicPvP.getInstance().getPrefix(), "api.game.messages.join", TimeSpigotAPI.getInstance().getRankManager().getPlayersRank(player.getUniqueId()).getPlayersRankAndName(player.getUniqueId()), Bukkit.getOnlinePlayers().size(), Bukkit.getMaxPlayers()));
                MagicPvP.getInstance().getScoreboardManager().sendLobbyKitScoreboardToPlayer(current, MagicPvP.getInstance().getCountdownManager().getCountdown(), MagicPvP.getInstance().getColor() + "Voting...");
            });

            return;
        }

        MagicPvP.getInstance().getSpecatePlayers().add(player);
        MagicPvP.getInstance().getKitManager().getPlayerKitTypeMap().put(player, KitType.DEFAULT);
        Bukkit.getOnlinePlayers().forEach(current -> current.hidePlayer(MagicPvP.getInstance(), player));
        player.getInventory().clear();
        player.getInventory().setItem(1, new ItemManager(Material.COMPASS, 1).setDisplayName(I18n.format(player, "api.game.item.teleporter")).build());
        MagicPvP.getInstance().getDefaultGameQuitItem().setItem(player);
        player.teleport(new Location(Bukkit.getWorld("world"), 111.5, 114.00, -262.5, -45, 0));
        player.setAllowFlight(true);

        Bukkit.getScheduler().runTaskLater(MagicPvP.getInstance(), () -> TimeSpigotAPI.getInstance().getTablistManager().registerRankTeam(player, "§c✗ §8» ", "", ChatColor.GRAY, 18), 5);
    }

}
