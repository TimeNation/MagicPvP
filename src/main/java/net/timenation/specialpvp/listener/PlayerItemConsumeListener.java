package net.timenation.specialpvp.listener;

import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class PlayerItemConsumeListener implements Listener {

    @EventHandler
    public void handlePlayerItemConsume(PlayerItemConsumeEvent event) {
        var player = event.getPlayer();

        switch (event.getItem().getType()) {
            case ENCHANTED_GOLDEN_APPLE -> player.playSound(player.getLocation(), Sound.BLOCK_BEEHIVE_EXIT, 3, 2);
        }
    }

}
