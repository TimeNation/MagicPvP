package net.timenation.specialpvp.listener;

import net.timenation.specialpvp.SpecialPvP;
import net.timenation.specialpvp.manager.kits.KitType;
import net.timenation.timespigotapi.TimeSpigotAPI;
import net.timenation.timespigotapi.manager.language.I18n;
import net.timenation.timespigotapi.timeplayer.TimePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.security.SecurityPermission;

public class InventoryClickListener implements Listener {
    //api.game.title.spectator.top

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        var player = (Player) event.getWhoClicked();
        TimePlayer timePlayer = TimeSpigotAPI.getInstance().getTimePlayerManager().getTimePlayer(player);

        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == null || event.getCurrentItem().getItemMeta() == null || event.getCurrentItem().getItemMeta().getDisplayName() == null) return;

        if (event.getView().getTitle().equals(I18n.format(player, "api.game.inventory.specialpvp.kit.title"))) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().equals(I18n.format(player, "api.game.specialpvp.kits.default"))) {
                SpecialPvP.getInstance().getKitManager().getPlayerKitTypeMap().put(player, KitType.DEFAULT);
                SpecialPvP.getInstance().getPlayerKit().put(player, SpecialPvP.getInstance().getKitManager().getPlayerKitTypeMap().get(player).getKitTranslateKey(player));
                SpecialPvP.getInstance().getScoreboardManager().sendLobbyKitScoreboardToPlayer(player, SpecialPvP.getInstance().getCountdownManager().getCountdown(), SpecialPvP.getInstance().getColor() + I18n.format(player, "api.game.specialpvp.kits.default.name"));
                player.sendMessage(I18n.format(player, SpecialPvP.getInstance().getPrefix(), "api.game.messages.specialpvp.kit.selected", SpecialPvP.getInstance().getSecoundColor() + I18n.format(player, "api.game.specialpvp.kits.default.name")));
                player.closeInventory();
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(I18n.format(player, "api.game.specialpvp.kits.axe"))) {
                SpecialPvP.getInstance().getKitManager().getPlayerKitTypeMap().put(player, KitType.AXE);
                SpecialPvP.getInstance().getPlayerKit().put(player, SpecialPvP.getInstance().getKitManager().getPlayerKitTypeMap().get(player).getKitTranslateKey(player));
                SpecialPvP.getInstance().getScoreboardManager().sendLobbyKitScoreboardToPlayer(player, SpecialPvP.getInstance().getCountdownManager().getCountdown(), SpecialPvP.getInstance().getColor() + I18n.format(player, "api.game.specialpvp.kits.axe.name"));
                player.sendMessage(I18n.format(player, SpecialPvP.getInstance().getPrefix(), "api.game.messages.specialpvp.kit.selected", SpecialPvP.getInstance().getSecoundColor() + I18n.format(player, "api.game.specialpvp.kits.axe.name")));
                player.closeInventory();
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(I18n.format(player, "api.game.specialpvp.kits.drowned"))) {
                SpecialPvP.getInstance().getKitManager().getPlayerKitTypeMap().put(player, KitType.DROWNED);
                SpecialPvP.getInstance().getPlayerKit().put(player, SpecialPvP.getInstance().getKitManager().getPlayerKitTypeMap().get(player).getKitTranslateKey(player));
                SpecialPvP.getInstance().getScoreboardManager().sendLobbyKitScoreboardToPlayer(player, SpecialPvP.getInstance().getCountdownManager().getCountdown(), SpecialPvP.getInstance().getColor() + I18n.format(player, "api.game.specialpvp.kits.drowned.name"));
                player.sendMessage(I18n.format(player, SpecialPvP.getInstance().getPrefix(), "api.game.messages.specialpvp.kit.selected", SpecialPvP.getInstance().getSecoundColor() + I18n.format(player, "api.game.specialpvp.kits.drowned.name")));
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
