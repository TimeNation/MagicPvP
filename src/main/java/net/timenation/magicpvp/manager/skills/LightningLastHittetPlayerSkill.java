package net.timenation.magicpvp.manager.skills;

import net.timenation.magicpvp.MagicPvP;
import net.timenation.magicpvp.manager.ManaManager;
import net.timenation.timespigotapi.manager.language.I18n;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class LightningLastHittetPlayerSkill implements Listener {

    private final MagicPvP magicPvP;

    public LightningLastHittetPlayerSkill(MagicPvP magicPvP) {
        this.magicPvP = magicPvP;
        Bukkit.getPluginManager().registerEvents(this, magicPvP);
    }

    @EventHandler
    public void handlePlayerInteract(PlayerInteractEvent event) {
        if(event.getAction().isLeftClick()) return;

        if (event.getAction().isRightClick() && event.getItem().getType().equals(Material.END_ROD)) {
            Player player = event.getPlayer();
            if(player.getKiller() instanceof Player && player.getKiller() != null) {
                if(magicPvP.getManaManager().getPlayersMana(player) >= 7) {
                    magicPvP.getWorld().strikeLightning(player.getKiller().getLocation());
                    magicPvP.getManaManager().removeManaFromPlayer(player, ManaManager.ManaLevel.LEVEL_7);
                    player.getKiller().setKiller(player);
                    player.sendMessage(I18n.format(player, "api.game.messages.magicpvp.strikelightningatlastdamager", magicPvP.getPrefix()));
                    return;
                }

                player.sendMessage(I18n.format(player, "api.game.messages.magicpvp.notenoughtmana", magicPvP.getPrefix()));
                return;
            }

            player.sendMessage(I18n.format(player, "api.game.messages.magicpvp.cantstrikelightningatlastplayer", magicPvP.getPrefix()));
        }
    }
}
