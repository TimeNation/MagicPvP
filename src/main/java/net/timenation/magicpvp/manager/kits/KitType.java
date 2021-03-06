package net.timenation.magicpvp.manager.kits;

import net.timenation.timespigotapi.manager.ItemManager;
import net.timenation.timespigotapi.manager.game.kit.KitObject;
import net.timenation.timespigotapi.manager.language.I18n;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public enum KitType {

    DEFAULT("api.game.magicpvp.kits.default", "api.game.magicpvp.kits.default.name", "api.game.magicpvp.kits.default.description", new KitObject("",
            ChatColor.DARK_PURPLE,
            new ItemManager(Material.LEATHER_CHESTPLATE).setLeatherArmorColor(Color.PURPLE).addItemFlags(ItemFlag.HIDE_DYE, ItemFlag.HIDE_ATTRIBUTES).build(),
            new ItemStack[]{ new ItemManager(Material.IRON_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 3).addEnchantment(Enchantment.KNOCKBACK, 2).addEnchantment(Enchantment.DURABILITY, 3).build(), new ItemManager(Material.ENCHANTED_GOLDEN_APPLE, 1).build() },
            new ItemStack[]{ new ItemManager(Material.LEATHER_BOOTS).setLeatherArmorColor(Color.PURPLE).build(), new ItemManager(Material.LEATHER_LEGGINGS).setLeatherArmorColor(Color.PURPLE).build(), new ItemManager(Material.CHAINMAIL_CHESTPLATE).build(), new ItemManager(Material.LEATHER_HELMET).setLeatherArmorColor(Color.PURPLE).build() }
            )),

    AXE("api.game.magicpvp.kits.axe", "api.game.magicpvp.kits.axe.name", "api.game.magicpvp.kits.axe.description", new KitObject("",
            ChatColor.DARK_PURPLE,
            new ItemManager(Material.DIAMOND_AXE).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build(),
            new ItemStack[]{ new ItemManager(Material.DIAMOND_AXE).addEnchantment(Enchantment.DAMAGE_ALL, 3).addEnchantment(Enchantment.DURABILITY, 3).build(), new ItemManager(Material.ENCHANTED_GOLDEN_APPLE, 1).build() },
            new ItemStack[]{ new ItemManager(Material.IRON_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build(), new ItemManager(Material.IRON_LEGGINGS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build(), new ItemManager(Material.IRON_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build(), new ItemManager(Material.IRON_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build() }
            )),
    DROWNED("api.game.magicpvp.kits.drowned", "api.game.magicpvp.kits.drowned.name", "api.game.magicpvp.kits.drowned.description", new KitObject("",
            ChatColor.DARK_PURPLE,
            new ItemManager(Material.TRIDENT).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build(),
            new ItemStack[]{ new ItemManager(Material.TRIDENT).addEnchantment(Enchantment.LOYALTY, 1).setDisplayName("??8?? ??5Poseidon??s Gabel").addEnchantment(Enchantment.DURABILITY, 2).addEnchantment(Enchantment.DAMAGE_ALL, 2).build(), new ItemManager(Material.ENCHANTED_GOLDEN_APPLE, 1).build() },
            new ItemStack[]{ new ItemManager(Material.LEATHER_BOOTS).setLeatherArmorColor(Color.PURPLE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build(), new ItemManager(Material.LEATHER_LEGGINGS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).setLeatherArmorColor(Color.PURPLE).build(), new ItemManager(Material.LEATHER_CHESTPLATE).setLeatherArmorColor(Color.PURPLE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build(), new ItemManager(Material.LEATHER_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).setLeatherArmorColor(Color.PURPLE).build() }
    )),
    HEALER("api.game.magicpvp.kits.healer", "api.game.magicpvp.kits.healer.name", "api.game.magicpvp.kits.healer.description", new KitObject("",
            ChatColor.DARK_PURPLE,
            new ItemManager(Material.SPLASH_POTION, 1).addPotionEffect(PotionEffectType.HEAL, 1, 0, true).addItemFlags(ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ATTRIBUTES).build(),
            new ItemStack[]{ new ItemManager(Material.DIAMOND_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 1).addEnchantment(Enchantment.DURABILITY, 3).build(), new ItemManager(Material.ENCHANTED_GOLDEN_APPLE, 1).build() },
            new ItemStack[]{ new ItemManager(Material.CHAINMAIL_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build(),
                    new ItemManager(Material.CHAINMAIL_LEGGINGS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build(),
                    new ItemManager(Material.LEATHER_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).setLeatherArmorColor(Color.RED).build(),
                    new ItemManager(Material.CHAINMAIL_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build()}
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
