package net.timenation.magicpvp.manager;

import net.timenation.magicpvp.MagicPvP;
import net.timenation.magicpvp.manager.kits.KitType;
import net.timenation.timespigotapi.manager.ItemManager;
import net.timenation.timespigotapi.manager.language.I18n;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryManager {

    private final ItemStack blackGlass = new ItemManager(Material.BLACK_STAINED_GLASS_PANE, 1).setDisplayName(" ").build();

    public void openVoteKitInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9 * 6, I18n.format(player, "api.game.inventory.kitvote.title", MagicPvP.getInstance().getPrefix()));

        for (KitType kitType : KitType.values()) {
            inventory.addItem(new ItemManager(kitType.getKitObject().getItem()).setDisplayName(kitType.getKitName(player)).setLore(I18n.formatLines(player, "api.game.inventory.kitvote.votes", MagicPvP.getInstance().getColor(), MagicPvP.getInstance().getKitVoteManager().getVotesFromKit(kitType))).build());
        }

        player.openInventory(inventory);
    }

    public void openPlayersInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, I18n.format(player, "api.game.inventory.magicpvp.navigator.title"));

        for (Player players : MagicPvP.getInstance().getPlayers()) {
            inventory.addItem(new ItemManager(Material.PLAYER_HEAD, 1).setDisplayName("§8» §a" + players.getName()).setSkullOwner(Bukkit.getPlayer(players.getUniqueId())).build());
        }

        player.openInventory(inventory);
    }
}