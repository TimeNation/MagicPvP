package net.timenation.magicpvp.manager.skills;

import net.timenation.magicpvp.MagicPvP;
import net.timenation.magicpvp.manager.ManaManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import java.util.UUID;

public class EvokerFangs implements Listener {

    private final MagicPvP magicPvP;

    public EvokerFangs(MagicPvP magicPvP) {
        this.magicPvP = magicPvP;
        Bukkit.getPluginManager().registerEvents(this, magicPvP);
    }

    @EventHandler
    public void handlePlayerToggleSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation().clone();

        if(event.isSneaking() && MagicPvP.getInstance().getManaManager().getPlayersMana(player) >= 5) {
            magicPvP.getManaManager().removeManaFromPlayer(player, ManaManager.ManaLevel.LEVEL_5);
            location.setX(location.getX() - 0.2);
            location.setZ(location.getZ() - 1.6);
            for (int i = 0; i < 360; i += 360 / 20) {
                double angle = (i * Math.PI / 180);
                double x = 0.5 * Math.cos(angle);
                double z = 0.5 * Math.sin(angle);
                Location particleLoc = location.add(x, 0, z);
                magicPvP.getWorld().spawnEntity(particleLoc, EntityType.EVOKER_FANGS).setMetadata("uuid", new FixedMetadataValue(magicPvP.getInstance(), player.getUniqueId().toString()));
            }
        }
    }

    @EventHandler
    public void handleEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if(event.getDamager().getType().equals(EntityType.EVOKER_FANGS) && event.getEntity() instanceof Player player) {
            player.setVelocity(event.getEntity().getLocation().getDirection().multiply(-1.2));
            player.setKiller(Bukkit.getPlayer(UUID.fromString(event.getDamager().getMetadata("uuid").get(0).asString())));
        }
    }
}
