package net.timenation.specialpvp.manager;

import net.timenation.specialpvp.SpecialPvP;
import net.timenation.timespigotapi.manager.ItemManager;
import net.timenation.timespigotapi.manager.game.TimeGame;
import net.timenation.timespigotapi.manager.game.countdown.Countdown;
import net.timenation.timespigotapi.manager.language.I18n;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.metadata.FixedMetadataValue;

public class CountdownManager extends Countdown<SpecialPvP> {

    public CountdownManager(TimeGame timeGame) {
        super(timeGame);
    }

    @Override
    public void teleport() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            tpcount++;

            player.setMetadata("lives", new FixedMetadataValue(SpecialPvP.getInstance(), 3));
            SpecialPvP.getInstance().getIngameManager().sendIngameScoreboard(player, game);
            SpecialPvP.getInstance().getIngameManager().teleportPlayerToLocation(player, tpcount, game);
            player.getInventory().setContents(SpecialPvP.getInstance().getKitManager().getKitFromPlayer(player).getKitObject().getContent());
            player.getInventory().setArmorContents(SpecialPvP.getInstance().getKitManager().getKitFromPlayer(player).getKitObject().getArmor());

            player.getInventory().setItem(7, new ItemManager(Material.END_ROD).setDisplayName(I18n.format(player, "api.game.specialpvp.item.lightning")).build());
            player.getInventory().setItem(8, new ItemManager(Material.IRON_AXE).setDisplayName(I18n.format(player, "api.game.specialpvp.item.thor_hammer")).build());

            if (SpecialPvP.getInstance().getKitManager().getKitFromPlayer(player).getKitName(player).equals(I18n.format(player, "api.game.specialpvp.kits.axe"))) {
                player.getInventory().setItemInOffHand(new ItemManager(Material.SHIELD).addEnchantment(Enchantment.DURABILITY, 1).build());
            }
        });
    }

    @Override
    public void before0() {

    }
}
