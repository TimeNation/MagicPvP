package net.timenation.specialpvp.manager.skills;

import net.timenation.specialpvp.SpecialPvP;
import net.timenation.specialpvp.manager.ManaManager;
import net.timenation.timespigotapi.manager.language.I18n;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class LightningLastHittetPlayerSkill implements Listener {

    private final SpecialPvP specialPvP;

    public LightningLastHittetPlayerSkill(SpecialPvP specialPvP) {
        this.specialPvP = specialPvP;
        Bukkit.getPluginManager().registerEvents(this, specialPvP);
    }

    @EventHandler
    public void handlePlayerInteract(PlayerInteractEvent event) {
        if(event.getAction().isLeftClick()) return;

        if (event.getAction().isRightClick() && event.getItem().getType().equals(Material.END_ROD)) {
            Player player = event.getPlayer();
            if(player.getKiller() instanceof Player && player.getKiller() != null) {
                if(specialPvP.getManaManager().getPlayersMana(player) >= 7) {
                    specialPvP.getWorld().strikeLightning(player.getKiller().getLocation());
                    specialPvP.getManaManager().removeManaFromPlayer(player, ManaManager.ManaLevel.LEVEL_7);
                    player.getKiller().setKiller(player);
                    player.sendMessage(I18n.format(player, "api.game.messages.specialpvp.strikelightningatlastdamager", specialPvP.getPrefix()));
                    return;
                }

                player.sendMessage(I18n.format(player, "api.game.messages.specialpvp.notenoughtmana", specialPvP.getPrefix()));
                return;
            }

            player.sendMessage(I18n.format(player, "api.game.messages.specialpvp.cantstrikelightningatlastplayer", specialPvP.getPrefix()));
        }
    }
}
