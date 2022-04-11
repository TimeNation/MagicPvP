package net.timenation.specialpvp.listener;

import net.timenation.specialpvp.SpecialPvP;
import net.timenation.specialpvp.manager.kits.KitType;
import net.timenation.timespigotapi.TimeSpigotAPI;
import net.timenation.timespigotapi.manager.ItemManager;
import net.timenation.timespigotapi.manager.game.gamestates.GameState;
import net.timenation.timespigotapi.manager.language.I18n;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        var player = event.getPlayer();

        if (SpecialPvP.getInstance().getGameState().equals(GameState.LOBBY) || SpecialPvP.getInstance().getGameState().equals(GameState.STARTING)) {
            player.teleport(new Location(Bukkit.getWorld("world"), 111.5, 114.00, -262.5, -45, 0));
            player.getInventory().setItem(2, new ItemManager(Material.BARREL, 1).setDisplayName(I18n.format(player, "api.game.item.kit", SpecialPvP.getInstance().getPrefix())).build());
            SpecialPvP.getInstance().getKitManager().getPlayerKitTypeMap().put(player, KitType.DEFAULT);
            SpecialPvP.getInstance().getPlayerKit().put(player, SpecialPvP.getInstance().getKitManager().getPlayerKitTypeMap().get(player).getKitTranslateKey(player));

            if (Bukkit.getOnlinePlayers().size() == SpecialPvP.getInstance().getNeededPlayers()) {
                SpecialPvP.getInstance().getCountdownManager().startCountdown();
            }

            Bukkit.getOnlinePlayers().forEach(current -> {
                current.sendMessage(I18n.format(current, SpecialPvP.getInstance().getPrefix(), "api.game.messages.join", TimeSpigotAPI.getInstance().getRankManager().getPlayersRank(player.getUniqueId()).getPlayersRankAndName(player.getUniqueId()), Bukkit.getOnlinePlayers().size(), Bukkit.getMaxPlayers()));
                SpecialPvP.getInstance().getScoreboardManager().sendLobbyKitScoreboardToPlayer(current, SpecialPvP.getInstance().getCountdownManager().getCountdown(), SpecialPvP.getInstance().getColor() + I18n.format(current, SpecialPvP.getInstance().getKitManager().getKitFromPlayer(current).getKitTranslateKey(current)));
            });

            return;
        }

        SpecialPvP.getInstance().getSpecatePlayers().add(player);
        SpecialPvP.getInstance().getKitManager().getPlayerKitTypeMap().put(player, KitType.DEFAULT);
        Bukkit.getOnlinePlayers().forEach(current -> current.hidePlayer(SpecialPvP.getInstance(), player));
        player.getInventory().clear();
        player.getInventory().setItem(1, new ItemManager(Material.COMPASS, 1).setDisplayName(I18n.format(player, "api.game.item.teleporter")).build());
        SpecialPvP.getInstance().getDefaultGameQuitItem().setItem(player);
        TimeSpigotAPI.getInstance().getTablistManager().registerRankTeam(player, "§c✗ §8» ", "", ChatColor.GRAY, 18);
        player.teleport(new Location(Bukkit.getWorld("world"), 111.5, 114.00, -262.5, -45, 0));
        player.setAllowFlight(true);
    }

}
