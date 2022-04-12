package net.timenation.magicpvp.manager;

import net.timenation.magicpvp.MagicPvP;
import net.timenation.timespigotapi.manager.language.I18n;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ManaManager {

    private final HashMap<Player, Integer> playerMana;
    private final HashMap<Player, BossBar> bossBarMap;

    public ManaManager() {
        this.playerMana = new HashMap<>();
        this.bossBarMap = new HashMap<>();

        Bukkit.getScheduler().runTaskTimerAsynchronously(MagicPvP.getInstance(), () -> MagicPvP.getInstance().getPlayers().forEach(this::addManaToPlayer), 0, 20);
    }

    public void setPlayersMana(Player player, int mana) {
        this.playerMana.put(player, mana);
    }

    public void addManaToPlayer(Player player) {
        if(getPlayersMana(player) <= 20) {
            this.playerMana.put(player, getPlayersMana(player) == 20 ? 20 : this.playerMana.get(player) + 1);
            this.bossBarMap.get(player).setProgress(getPlayersMana(player) == 20 ? 1 : this.bossBarMap.get(player).getProgress() + 0.05);
            this.bossBarMap.get(player).setTitle(I18n.format(player, "api.game.bossbar.magicpvp.mana", getPlayersMana(player), MagicPvP.getInstance().getColor()));
        }

        if (getPlayersMana(player) < 5) {
            this.bossBarMap.get(player).setColor(BarColor.RED);
        } else {
            this.bossBarMap.get(player).setColor(BarColor.GREEN);
        }
    }

    public void removeManaFromPlayer(Player player, ManaLevel manaLevel) {
        this.playerMana.put(player, this.playerMana.get(player) - manaLevel.getMana());
        switch (manaLevel) {
            case LEVEL_5 -> getPlayersBossBar(player).setProgress(getPlayersBossBar(player).getProgress() - 0.25);
            case LEVEL_7 -> getPlayersBossBar(player).setProgress(getPlayersBossBar(player).getProgress() - 0.35);
            case LEVEL_10 -> getPlayersBossBar(player).setProgress(getPlayersBossBar(player).getProgress() - 0.5);
        }
        this.bossBarMap.get(player).setTitle(I18n.format(player, "api.game.bossbar.magicpvp.mana", getPlayersMana(player), MagicPvP.getInstance().getColor()));
        if (getPlayersMana(player) < 5) {
            this.bossBarMap.get(player).setColor(BarColor.RED);
        } else {
            this.bossBarMap.get(player).setColor(BarColor.GREEN);
        }
    }

    public int getPlayersMana(Player player) {
        return this.playerMana.get(player);
    }

    public void addPlayerBossBar(Player player) {
        this.bossBarMap.put(player, Bukkit.createBossBar(I18n.format(player, "api.game.bossbar.magicpvp.mana", 20, MagicPvP.getInstance().getColor()), BarColor.GREEN, BarStyle.SOLID));
        this.bossBarMap.get(player).addPlayer(player);
    }

    public BossBar getPlayersBossBar(Player player) {
        return this.bossBarMap.get(player);
    }

    public enum ManaLevel {
        LEVEL_5(5),
        LEVEL_7(7),
        LEVEL_10(10);

        private int mana;

        ManaLevel(int mana) {
            this.mana = mana;
        }

        public int getMana() {
            return mana;
        }
    }
}
