package net.timenation.magicpvp.listener;

import net.timenation.magicpvp.MagicPvP;
import net.timenation.timespigotapi.manager.game.gamestates.GameState;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void handlePlayerInteract(PlayerInteractEvent event) {
        var player = event.getPlayer();

        if (event.getItem() == null || event.getItem().getItemMeta() == null || event.getItem().getType().equals(Material.AIR)) return;

        if (MagicPvP.getInstance().getSpecatePlayers().contains(player) || MagicPvP.getInstance().getGameState().equals(GameState.LOBBY) || MagicPvP.getInstance().getGameState().equals(GameState.STARTING)) {
            event.setCancelled(true);
        }

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            switch (event.getItem().getType()) {
                case BARREL -> {
                    if (MagicPvP.getInstance().getGameState() == GameState.LOBBY || MagicPvP.getInstance().getGameState() == GameState.STARTING) {
                        MagicPvP.getInstance().getInventoryManager().openSelectKitInventory(player);
                    }
                }
                case COMPASS -> {
                    if (MagicPvP.getInstance().getGameState().equals(GameState.INGAME)) {
                        MagicPvP.getInstance().getInventoryManager().openPlayersInventory(player);
                    }
                }
            }
        }
    }
}
