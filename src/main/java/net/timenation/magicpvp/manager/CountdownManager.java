package net.timenation.magicpvp.manager;

import net.timenation.magicpvp.MagicPvP;
import net.timenation.magicpvp.manager.kits.KitType;
import net.timenation.timespigotapi.manager.ItemManager;
import net.timenation.timespigotapi.manager.game.TimeGame;
import net.timenation.timespigotapi.manager.game.countdown.Countdown;
import net.timenation.timespigotapi.manager.language.I18n;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffectType;

public class CountdownManager extends Countdown<MagicPvP> {

    public CountdownManager(TimeGame timeGame) {
        super(timeGame);
    }

    @Override
    public void at0() {
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

            if (MagicPvP.getInstance().getKitManager().getKitFromPlayer(player).equals(KitType.AXE)) {
                player.getInventory().setItemInOffHand(new ItemManager(Material.SHIELD).addEnchantment(Enchantment.DURABILITY, 1).build());
            }
            
            if(MagicPvP.getInstance().getKitManager().getKitFromPlayer(player).equals(KitType.HEALER)) {
                for (int i = 0; i < 33; i++) {
                    player.getInventory().addItem(new ItemManager(Material.SPLASH_POTION, 1).addPotionEffect(PotionEffectType.HEAL, 2, 0, true).build());
                }
            }
        });
    }

    @Override
    public void before0() {
    }

    @Override
    public void at10() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            MagicPvP.getInstance().getKitManager().getPlayerKitTypeMap().put(player, MagicPvP.getInstance().getKitVoteManager().getKitWithMaxVotes());
            MagicPvP.getInstance().getPlayerKit().put(player, MagicPvP.getInstance().getKitVoteManager().getKitWithMaxVotes().getKitName(player));
            Bukkit.getScheduler().runTask(MagicPvP.getInstance(), () -> game.getScoreboardManager().sendLobbyKitScoreboardToPlayer(player, 10, MagicPvP.getInstance().getKitVoteManager().getKitWithMaxVotes().getKitName(player)));
            player.sendMessage(I18n.format(player, game.getPrefix(), "api.game.messages.magicpvp.10", game.getPrefix(), game.getColor(), game.getSecoundColor(), game.getGameMap(), game.getBuilder(), MagicPvP.getInstance().getKitVoteManager().getKitWithMaxVotes().getKitTranslateKey(player)));

            player.getInventory().getItem(2).setType(Material.BARRIER);
        });
    }

    @Override
    public void atEnd() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if(MagicPvP.getInstance().getManaManager().getPlayersBossBar(player) != null) MagicPvP.getInstance().getManaManager().getPlayersBossBar(player).removeAll();
        });
    }
}
