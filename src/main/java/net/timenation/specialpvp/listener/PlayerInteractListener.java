package net.timenation.specialpvp.listener;

import net.timenation.specialpvp.SpecialPvP;
import net.timenation.timespigotapi.manager.game.gamestates.GameState;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        var player = event.getPlayer();

        if (event.getItem() == null) return;
        if (event.getItem().getItemMeta() == null) return;
        if (event.getItem().getType().equals(Material.AIR)) return;

        if (SpecialPvP.getInstance().getSpecatePlayers().contains(player) || SpecialPvP.getInstance().getGameState().equals(GameState.LOBBY) || SpecialPvP.getInstance().getGameState().equals(GameState.STARTING)) {
            event.setCancelled(true);
        }

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            switch (event.getItem().getType()) {
                case BARREL -> {
                    if (SpecialPvP.getInstance().getGameState() == GameState.LOBBY || SpecialPvP.getInstance().getGameState() == GameState.STARTING) {
                        SpecialPvP.getInstance().getInventoryManager().openSelectKitInventory(player);
                    }
                }
                case COMPASS -> {
                    if (SpecialPvP.getInstance().getGameState().equals(GameState.INGAME)) {
                        SpecialPvP.getInstance().getInventoryManager().openPlayersInventory(player);
                    }
                }
            }
        }


    }

}
