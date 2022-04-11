package net.timenation.specialpvp;

import net.timenation.specialpvp.commands.StartCommand;
import net.timenation.specialpvp.listener.PlayerJoinListener;
import net.timenation.specialpvp.listener.PlayerQuitListener;
import net.timenation.specialpvp.manager.CountdownManager;
import net.timenation.specialpvp.manager.kits.KitManager;
import net.timenation.timespigotapi.manager.game.TimeGame;
import net.timenation.timespigotapi.manager.game.defaultitems.DefaultGameExplainItem;
import net.timenation.timespigotapi.manager.game.defaultitems.DefaultGameNavigatorItem;
import net.timenation.timespigotapi.manager.game.defaultitems.DefaultGameQuitItem;
import net.timenation.timespigotapi.manager.game.features.TrampolineFeature;
import net.timenation.timespigotapi.manager.game.gamestates.GameState;
import net.timenation.timespigotapi.manager.game.manager.ConfigManager;
import net.timenation.timespigotapi.manager.game.modules.NickModule;
import net.timenation.timespigotapi.manager.game.team.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;

public final class SpecialPvP extends TimeGame {

    private static SpecialPvP instance;

    private DefaultGameQuitItem defaultGameQuitItem;
    private KitManager kitManager;
    private CountdownManager countdownManager;

    @Override
    public void onEnable() {
        instance = this;

        setPrefix("SpecialPvP");
        setColor("§9");
        setSecoundColor("§9");
        setGameState(GameState.LOBBY);
        setGameWithKits(true);
        setCountdown(60);

        this.defaultGameQuitItem = new DefaultGameQuitItem(this, 7);
        new DefaultGameNavigatorItem(this, 1);
        new DefaultGameExplainItem(this,6, "api.game.specialpvp.explain");
        new TrampolineFeature(this);
        new NickModule(this);

        this.kitManager = new KitManager();
        this.countdownManager = new CountdownManager(this);

        for (World world : Bukkit.getWorlds()) {
            world.setDifficulty(Difficulty.EASY);
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            world.setGameRule(GameRule.KEEP_INVENTORY, false);
            world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            world.setGameRule(GameRule.DO_FIRE_TICK, false);
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
            world.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);
            world.setTime(6000);
            world.setStorm(false);
        }

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);

        getCommand("start").setExecutor(new StartCommand());
    }

    public static SpecialPvP getInstance() {
        return instance;
    }

    public DefaultGameQuitItem getDefaultGameQuitItem() {
        return defaultGameQuitItem;
    }

    public CountdownManager getCountdownManager() {
        return countdownManager;
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    @Override
    public ArrayList<Player> getPlayers() {
        return players;
    }

    @Override
    public ArrayList<Player> getSpecatePlayers() {
        return specatePlayer;
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public int getNeededPlayers() {
        return 1;
    }

    @Override
    public ConfigManager getConfigManager() {
        return configManager;
    }

    @Override
    public TeamManager getTeamManager() {
        return teamManager;
    }
}
