package net.timenation.magicpvp.manager;

import net.timenation.magicpvp.MagicPvP;
import net.timenation.timespigotapi.manager.ItemManager;
import net.timenation.timespigotapi.manager.game.TimeGame;
import net.timenation.timespigotapi.manager.game.countdown.Countdown;
import net.timenation.timespigotapi.manager.language.I18n;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.metadata.FixedMetadataValue;

public class CountdownManager extends Countdown<MagicPvP> {

    public CountdownManager(TimeGame timeGame) {
        super(timeGame);
    }

    @Override
    public void teleport() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            tpcount++;

            player.setMetadata("lives", new FixedMetadataValue(MagicPvP.getInstance(), 3));
            MagicPvP.getInstance().getIngameManager().sendIngameScoreboard(player, game);
            MagicPvP.getInstance().getIngameManager().teleportPlayerToLocation(player, tpcount, game);
            MagicPvP.getInstance().getManaManager().setPlayersMana(player, 20);
            Bukkit.getScheduler().runTask(game, () -> MagicPvP.getInstance().getManaManager().addPlayerBossBar(player));
            player.getInventory().setContents(MagicPvP.getInstance().getKitManager().getKitFromPlayer(player).getKitObject().getContent());
            player.getInventory().setArmorContents(MagicPvP.getInstance().getKitManager().getKitFromPlayer(player).getKitObject().getArmor());

            player.getInventory().setItem(7, new ItemManager(Material.END_ROD).setDisplayName(I18n.format(player, "api.game.magicpvp.item.lightning")).build());
            player.getInventory().setItem(8, new ItemManager(Material.IRON_AXE).setDisplayName(I18n.format(player, "api.game.magicpvp.item.thor_hammer")).build());

            if (MagicPvP.getInstance().getKitManager().getKitFromPlayer(player).getKitName(player).equals(I18n.format(player, "api.game.magicpvp.kits.axe"))) {
                player.getInventory().setItemInOffHand(new ItemManager(Material.SHIELD).addEnchantment(Enchantment.DURABILITY, 1).build());
            }
        });
    }

    @Override
    public void before0() {

    }

    @Override
    public void atEnd() {
        Bukkit.getOnlinePlayers().forEach(player -> { MagicPvP.getInstance().getManaManager().getPlayersBossBar(player).removeAll(); });
    }
}
