package net.timenation.specialpvp.listener;

import net.timenation.specialpvp.SpecialPvP;
import net.timenation.timespigotapi.manager.game.gamestates.GameState;
import net.timenation.timespigotapi.manager.language.I18n;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;

public class LobbyProtection implements Listener {

    @EventHandler
    public void handleBlockBreak(BlockBreakEvent event) {
        if (SpecialPvP.getInstance().getGameState() != GameState.INGAME || SpecialPvP.getInstance().getSpecatePlayers().contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handlePlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        if (SpecialPvP.getInstance().getSpecatePlayers().contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handlePlayerBucketFill(PlayerBucketFillEvent event) {
        if (SpecialPvP.getInstance().getSpecatePlayers().contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleBlockPlace(BlockPlaceEvent event) {
        if (SpecialPvP.getInstance().getGameState() != GameState.INGAME || SpecialPvP.getInstance().getSpecatePlayers().contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleInventoryClick(InventoryClickEvent event) {
        if (SpecialPvP.getInstance().getGameState() != GameState.INGAME || SpecialPvP.getInstance().getSpecatePlayers().contains((Player) event.getWhoClicked())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleEntityDamageEvent(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (SpecialPvP.getInstance().getGameState() != GameState.INGAME || SpecialPvP.getInstance().getSpecatePlayers().contains(player)) {
                event.setCancelled(true);
            }

            if (player.getKiller() instanceof Player) {
                player.setKiller(player.getKiller());
            }
        }
    }

    @EventHandler
    public void handleWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void handlePlayerDropItem(PlayerDropItemEvent event) {
        if (SpecialPvP.getInstance().getGameState() != GameState.INGAME || SpecialPvP.getInstance().getSpecatePlayers().contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handlePlayerPickupItem(PlayerPickupItemEvent event) {
        if (SpecialPvP.getInstance().getGameState() != GameState.INGAME || SpecialPvP.getInstance().getSpecatePlayers().contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleFoodLevelChange(FoodLevelChangeEvent event) {
        if (SpecialPvP.getInstance().getGameState() != GameState.INGAME) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handlePlayerChat(PlayerChatEvent event) {
        if (SpecialPvP.getInstance().getSpecatePlayers().contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handlePlayerArmorStandManipulate(PlayerArmorStandManipulateEvent event) {
        if (SpecialPvP.getInstance().getGameState() != GameState.INGAME) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!SpecialPvP.getInstance().getGameState().equals(GameState.INGAME)) {
            if (event.getEntity() instanceof ArmorStand) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void handlePlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (SpecialPvP.getInstance().getGameState() == GameState.INGAME) {
            if (SpecialPvP.getInstance().getSpecatePlayers().contains(event.getPlayer())) {
                if (player.getLocation().getY() <= 0) {
                    player.teleport(new Location(Bukkit.getWorld("world"), 111.5, 114.00, -262.5, -45, 0));
                }
            }

            return;
        }

        if (player.getLocation().getY() <= 100) {
            player.teleport(new Location(Bukkit.getWorld("world"), 111.5, 114.00, -262.5, -45, 0));
            player.sendMessage(I18n.format(player, "api.game.messages.jumpinvoid", SpecialPvP.getInstance().getPrefix()));
        }
    }
}
