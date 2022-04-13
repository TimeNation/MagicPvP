package net.timenation.magicpvp.manager.skills;

import net.timenation.magicpvp.MagicPvP;
import net.timenation.magicpvp.manager.ManaManager;
import net.timenation.timespigotapi.manager.language.I18n;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;

public class ThorsHammer implements Listener {

    private final MagicPvP magicPvP;

    public ThorsHammer(MagicPvP magicPvP) {
        this.magicPvP = magicPvP;
        Bukkit.getPluginManager().registerEvents(this, magicPvP);
    }

    @EventHandler
    public void handlePlayerInteract(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();

        if (event.getHand().equals(EquipmentSlot.HAND) && event.getRightClicked() instanceof Player target && player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(I18n.format(player, "api.game.magicpvp.item.thor_hammer"))) {
            if (magicPvP.getManaManager().getPlayersMana(player) >= 10) {
                magicPvP.getManaManager().removeManaFromPlayer(player, ManaManager.ManaLevel.LEVEL_10);
                target.setVelocity(new Vector(0, 1.5, 0));
                target.setKiller(player);
                player.sendMessage(I18n.format(player, "api.game.messages.magicpvp.usethorshammer", magicPvP.getPrefix()));
                return;
            }

            player.sendMessage(I18n.format(player, "api.game.messages.magicpvp.notenoughtmana", magicPvP.getPrefix()));
        }
    }
}