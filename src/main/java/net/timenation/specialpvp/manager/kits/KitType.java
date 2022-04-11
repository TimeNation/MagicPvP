package net.timenation.specialpvp.manager.kits;

import net.timenation.specialpvp.SpecialPvP;
import net.timenation.timespigotapi.manager.ItemManager;
import net.timenation.timespigotapi.manager.game.kit.KitObject;
import net.timenation.timespigotapi.manager.language.I18n;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by Moritz on 11.04.2022
 *
 * @author ItzMxritz (blockpixels.de)
 */
public enum KitType {

    DEFAULT("api.game.specialpvp.kits.default", "api.game.specialpvp.kits.default.name", "api.game.specialpvp.kits.default.description", new KitObject("",
            ChatColor.BLUE,
            new ItemManager(Material.LEATHER_CHESTPLATE).setLeatherArmorColor(Color.BLUE).build(),
            new ItemStack[]{ new ItemManager(Material.IRON_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.KNOCKBACK, 2).addEnchantment(Enchantment.DURABILITY, 3).build(), new ItemManager(Material.ENCHANTED_GOLDEN_APPLE, 1).build() },
            new ItemStack[]{ new ItemManager(Material.LEATHER_BOOTS).setLeatherArmorColor(Color.BLUE).build(), new ItemManager(Material.LEATHER_LEGGINGS).setLeatherArmorColor(Color.BLUE).build(), new ItemManager(Material.CHAINMAIL_CHESTPLATE).build(), new ItemManager(Material.LEATHER_HELMET).setLeatherArmorColor(Color.BLUE).build() }
            ));

    private final String kitName;
    private final String kitNameTranslateKey;
    private final String kitDescription;
    private final KitObject kitObject;

    KitType(String kitName, String kitNameTranslateKey, String kitDescription, KitObject kitObject) {
        this.kitName = kitName;
        this.kitNameTranslateKey = kitNameTranslateKey;
        this.kitDescription = kitDescription;
        this.kitObject = kitObject;
    }

    public KitObject getKitObject() {
        return kitObject;
    }

    public String getKitName(Player player) {
        return I18n.format(player, kitName);
    }

    public List<String> getKitDescription(Player player) {
        return I18n.formatLines(player, kitDescription);
    }

    public String getKitTranslateKey(Player player) {
        return I18n.format(player, kitNameTranslateKey);
    }

    public static KitType getType(Player player, String kitName) {
        for (final KitType type : values()) {
            if (!type.getKitTranslateKey(player).equalsIgnoreCase(kitName)) {
                continue;
            }
            return type;
        }
        return null;
    }

}
