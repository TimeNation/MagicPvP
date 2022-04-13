package net.timenation.magicpvp.listener;

import net.timenation.magicpvp.MagicPvP;
import net.timenation.magicpvp.manager.kits.KitType;
import net.timenation.timespigotapi.manager.language.I18n;
import net.timenation.timespigotapi.mysql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void handleInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == null || event.getCurrentItem().getItemMeta() == null || event.getCurrentItem().getItemMeta().getDisplayName() == null) return;

        if (event.getView().getTitle().equals(I18n.format(player, "api.game.inventory.kitvote.title", MagicPvP.getInstance().getPrefix()))) {
            switch (event.getCurrentItem().getType()) {
                case LEATHER_CHESTPLATE -> MagicPvP.getInstance().getKitVoteManager().playerVoteForKit(player, KitType.DEFAULT);
                case DIAMOND_AXE -> MagicPvP.getInstance().getKitVoteManager().playerVoteForKit(player, KitType.AXE);
                case TRIDENT -> MagicPvP.getInstance().getKitVoteManager().playerVoteForKit(player, KitType.DROWNED);
                case SPLASH_POTION -> MagicPvP.getInstance().getKitVoteManager().playerVoteForKit(player, KitType.HEALER);
            }
        }

        if (event.getView().getTitle().equals(I18n.format(player, "api.game.inventory.magicpvp.navigator.title"))) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§8» §a")) {
                Player target = Bukkit.getPlayer(event.getCurrentItem().getItemMeta().getDisplayName().replace("§8» §a", ""));
                player.teleport(target);
                player.setAllowFlight(true);
            }
        }
    }
}
