package net.timenation.magicpvp.listener;

import net.timenation.magicpvp.MagicPvP;
import net.timenation.timespigotapi.manager.ItemManager;
import net.timenation.timespigotapi.manager.game.gamestates.GameState;
import net.timenation.timespigotapi.manager.language.I18n;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;

public class LobbyProtection implements Listener {

    @EventHandler
    public void handleBlockBreak(BlockBreakEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void handlePlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void handlePlayerBucketFill(PlayerBucketFillEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void handleBlockPlace(BlockPlaceEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void handleInventoryClick(InventoryClickEvent event) {
        if(MagicPvP.getInstance().getGameState() != GameState.INGAME || MagicPvP.getInstance().getSpecatePlayers().contains(event.getWhoClicked()) || event.getSlot() == 7 || event.getSlot() == 8) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleEntityDamageEvent(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (MagicPvP.getInstance().getGameState() != GameState.INGAME || MagicPvP.getInstance().getSpecatePlayers().contains(player)) {
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
        event.setCancelled(true);
    }

    @EventHandler
    public void handlePlayerPickupItem(PlayerPickupItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void handleFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void handlePlayerChat(PlayerChatEvent event) {
        if (MagicPvP.getInstance().getSpecatePlayers().contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handlePlayerArmorStandManipulate(PlayerArmorStandManipulateEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void handleEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!MagicPvP.getInstance().getGameState().equals(GameState.INGAME)) {
            if (event.getEntity() instanceof ArmorStand) {
                event.setCancelled(true);
            }
        } else {
            if (event.getDamager() instanceof Player player) {
                if (MagicPvP.getInstance().getSpecatePlayers().contains(player)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void handleProjectileHit(ProjectileHitEvent event) {
        Entity player = event.getHitEntity();

        if (event.getEntity().getType() == EntityType.TRIDENT) {
            event.getEntity().remove();
            Player shooter = (Player) event.getEntity().getShooter();

            shooter.getInventory().addItem(new ItemManager(Material.TRIDENT).addEnchantment(Enchantment.LOYALTY, 1).setDisplayName("??8?? ??9Poseidon??s Gabel").addEnchantment(Enchantment.DURABILITY, 2).addEnchantment(Enchantment.DAMAGE_ALL, 2).build());
        }
    }

    @EventHandler
    public void handlePlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (MagicPvP.getInstance().getGameState() == GameState.INGAME) {
            if (MagicPvP.getInstance().getSpecatePlayers().contains(event.getPlayer())) {
                if (player.getLocation().getY() <= 0) {
                    player.teleport(new Location(Bukkit.getWorld("world"), 111.5, 114.00, -262.5, -45, 0));
                }
            }

            return;
        }

        if (player.getLocation().getY() <= 100) {
            player.teleport(new Location(Bukkit.getWorld("world"), 111.5, 114.00, -262.5, -45, 0));
            player.sendMessage(I18n.format(player, "api.game.messages.jumpinvoid", MagicPvP.getInstance().getPrefix()));
        }
    }
}
