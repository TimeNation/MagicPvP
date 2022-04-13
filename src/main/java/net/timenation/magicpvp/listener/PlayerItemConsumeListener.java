package net.timenation.magicpvp.listener;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class PlayerItemConsumeListener implements Listener {

    @EventHandler
    public void handlePlayerItemConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();

        if (event.getItem().getType() == Material.ENCHANTED_GOLDEN_APPLE) {
            player.playSound(player.getLocation(), Sound.BLOCK_BEEHIVE_EXIT, 3, 2);
            player.setHealth(20);
        }
    }
}
