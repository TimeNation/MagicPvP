package net.timenation.specialpvp.manager;

import net.timenation.specialpvp.SpecialPvP;
import net.timenation.timespigotapi.manager.game.TimeGame;
import net.timenation.timespigotapi.manager.game.countdown.Countdown;
import org.bukkit.Bukkit;

/**
 * Created by Moritz on 11.04.2022
 *
 * @author ItzMxritz (blockpixels.de)
 */
public class CountdownManager extends Countdown<SpecialPvP> {

    public CountdownManager(TimeGame timeGame) {
        super(timeGame);
    }

    @Override
    public void teleport() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.getInventory().setContents(SpecialPvP.getInstance().getKitManager().getKitFromPlayer(player).getKitObject().getContent());
            player.getInventory().setArmorContents(SpecialPvP.getInstance().getKitManager().getKitFromPlayer(player).getKitObject().getArmor());
        });
    }

    @Override
    public void before0() {

    }
}
