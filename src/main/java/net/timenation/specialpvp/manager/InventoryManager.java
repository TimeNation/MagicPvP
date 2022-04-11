package net.timenation.specialpvp.manager;

import net.timenation.specialpvp.SpecialPvP;
import net.timenation.specialpvp.manager.kits.KitType;
import net.timenation.timespigotapi.manager.ItemManager;
import net.timenation.timespigotapi.manager.language.I18n;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * Created by Moritz on 11.04.2022
 *
 * @author ItzMxritz (blockpixels.de)
 */
public class InventoryManager {

    private final ItemStack blackGlass = new ItemManager(Material.BLACK_STAINED_GLASS_PANE, 1).setDisplayName("§r").build();

    public void openSelectKitInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9 * 6, I18n.format(player, "api.game.inventory.specialpvp.kit.title"));

        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, blackGlass);
        }

        for (int i = 45; i < 54; i++) {
            inventory.setItem(i, blackGlass);
        }

        inventory.setItem(17, blackGlass);
        inventory.setItem(26, blackGlass);
        inventory.setItem(35, blackGlass);
        inventory.setItem(44, blackGlass);

        inventory.setItem(9, blackGlass);
        inventory.setItem(18, blackGlass);
        inventory.setItem(27, blackGlass);
        inventory.setItem(36, blackGlass);

        for (KitType kitType : KitType.values()) {
            inventory.addItem(new ItemManager(kitType.getKitObject().getItem()).setDisplayName(kitType.getKitName(player)).setLore(kitType.getKitDescription(player)).build());
        }

        player.openInventory(inventory);
    }

    public void openPlayersInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, I18n.format(player, "api.game.inventory.specialpvp.navigator.title"));

        for (Player players : SpecialPvP.getInstance().getPlayers()) {
            inventory.addItem(new ItemManager(Material.PLAYER_HEAD, 1).setDisplayName("§8» §a" + players.getName()).setSkullOwner(Bukkit.getPlayer(players.getUniqueId())).build());
        }

        player.openInventory(inventory);
    }

}
