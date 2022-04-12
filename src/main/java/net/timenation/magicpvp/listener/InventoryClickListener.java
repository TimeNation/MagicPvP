package net.timenation.magicpvp.listener;

import net.timenation.magicpvp.MagicPvP;
import net.timenation.magicpvp.manager.kits.KitType;
import net.timenation.timespigotapi.manager.language.I18n;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {
    //api.game.title.spectator.top

    @EventHandler
    public void handleInventoryClick(InventoryClickEvent event) {
        var player = (Player) event.getWhoClicked();

        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == null || event.getCurrentItem().getItemMeta() == null || event.getCurrentItem().getItemMeta().getDisplayName() == null) return;

        if (event.getView().getTitle().equals(I18n.format(player, "api.game.inventory.specialpvp.kit.title"))) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().equals(I18n.format(player, "api.game.magicpvp.kits.default"))) {
                MagicPvP.getInstance().getKitManager().getPlayerKitTypeMap().put(player, KitType.DEFAULT);
                MagicPvP.getInstance().getPlayerKit().put(player, MagicPvP.getInstance().getKitManager().getPlayerKitTypeMap().get(player).getKitTranslateKey(player));
                MagicPvP.getInstance().getScoreboardManager().sendLobbyKitScoreboardToPlayer(player, MagicPvP.getInstance().getCountdownManager().getCountdown(), MagicPvP.getInstance().getColor() + I18n.format(player, "api.game.magicpvp.kits.default.name"));
                player.sendMessage(I18n.format(player, MagicPvP.getInstance().getPrefix(), "api.game.messages.magicpvp.kit.selected", MagicPvP.getInstance().getSecoundColor() + I18n.format(player, "api.game.magicpvp.kits.default.name")));
                player.closeInventory();
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(I18n.format(player, "api.game.magicpvp.kits.axe"))) {
                MagicPvP.getInstance().getKitManager().getPlayerKitTypeMap().put(player, KitType.AXE);
                MagicPvP.getInstance().getPlayerKit().put(player, MagicPvP.getInstance().getKitManager().getPlayerKitTypeMap().get(player).getKitTranslateKey(player));
                MagicPvP.getInstance().getScoreboardManager().sendLobbyKitScoreboardToPlayer(player, MagicPvP.getInstance().getCountdownManager().getCountdown(), MagicPvP.getInstance().getColor() + I18n.format(player, "api.game.magicpvp.kits.axe.name"));
                player.sendMessage(I18n.format(player, MagicPvP.getInstance().getPrefix(), "api.game.messages.magicpvp.kit.selected", MagicPvP.getInstance().getSecoundColor() + I18n.format(player, "api.game.magicpvp.kits.axe.name")));
                player.closeInventory();
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(I18n.format(player, "api.game.magicpvp.kits.drowned"))) {
                MagicPvP.getInstance().getKitManager().getPlayerKitTypeMap().put(player, KitType.DROWNED);
                MagicPvP.getInstance().getPlayerKit().put(player, MagicPvP.getInstance().getKitManager().getPlayerKitTypeMap().get(player).getKitTranslateKey(player));
                MagicPvP.getInstance().getScoreboardManager().sendLobbyKitScoreboardToPlayer(player, MagicPvP.getInstance().getCountdownManager().getCountdown(), MagicPvP.getInstance().getColor() + I18n.format(player, "api.game.magicpvp.kits.drowned.name"));
                player.sendMessage(I18n.format(player, MagicPvP.getInstance().getPrefix(), "api.game.messages.magicpvp.kit.selected", MagicPvP.getInstance().getSecoundColor() + I18n.format(player, "api.game.magicpvp.kits.drowned.name")));
                player.closeInventory();
            }
        }

        if (event.getView().getTitle().equals(I18n.format(player, "api.game.inventory.specialpvp.navigator.title"))) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§8» §a")) {
                Player target = Bukkit.getPlayer(event.getCurrentItem().getItemMeta().getDisplayName().replace("§8» §a", ""));
                player.teleport(target);
                player.setAllowFlight(true);
            }
        }
    }
}
