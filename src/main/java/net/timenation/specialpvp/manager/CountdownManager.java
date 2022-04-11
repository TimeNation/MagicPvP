package net.timenation.specialpvp.manager;

import net.timenation.specialpvp.SpecialPvP;
import net.timenation.timespigotapi.manager.game.TimeGame;
import net.timenation.timespigotapi.manager.game.countdown.Countdown;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
        });
    }

    @Override
    public void before0() {

    }
}
