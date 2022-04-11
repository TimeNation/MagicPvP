package net.timenation.specialpvp;

import net.timenation.specialpvp.commands.StartCommand;
import net.timenation.specialpvp.commands.UnnickCommand;
import net.timenation.specialpvp.listener.*;
import net.timenation.specialpvp.manager.CountdownManager;
import net.timenation.specialpvp.manager.IngameManager;
import net.timenation.specialpvp.manager.InventoryManager;
import net.timenation.specialpvp.manager.kits.KitManager;
import net.timenation.timespigotapi.manager.game.TimeGame;
import net.timenation.timespigotapi.manager.game.defaultitems.DefaultGameExplainItem;
import net.timenation.timespigotapi.manager.game.defaultitems.DefaultGameNavigatorItem;
import net.timenation.timespigotapi.manager.game.defaultitems.DefaultGameQuitItem;
import net.timenation.timespigotapi.manager.game.features.TrampolineFeature;
import net.timenation.timespigotapi.manager.game.gamestates.GameState;
import net.timenation.timespigotapi.manager.game.manager.ConfigManager;
import net.timenation.timespigotapi.manager.game.modules.NickModule;
import net.timenation.timespigotapi.manager.game.scoreboard.ScoreboardManager;
import net.timenation.timespigotapi.manager.game.team.TeamManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;

public final class SpecialPvP extends TimeGame {

    private static SpecialPvP instance;
    private IngameManager ingameManager;
    private CountdownManager countdownManager;
    private InventoryManager inventoryManager;
    private KitManager kitManager;
    private DefaultGameQuitItem defaultGameQuitItem;
    private DefaultGameNavigatorItem defaultGameNavigatorItem;

    @Override
    public void onEnable() {
        instance = this;
        ingameManager = new IngameManager();
        countdownManager = new CountdownManager(this);
        kitManager = new KitManager();
        inventoryManager = new InventoryManager();
        defaultGameQuitItem = new DefaultGameQuitItem(this, 7);

        setPrefix("SpecialPvP");
        setColor("§9");
        setSecoundColor("§9");
        setGameState(GameState.LOBBY);
        setScoreboardManager(new ScoreboardManager(this));
        setGameWithKits(true);
        setCountdown(60);
        defaultGameNavigatorItem = new DefaultGameNavigatorItem(this, 1);
        new DefaultGameExplainItem(this,6, "api.game.specialpvp.explain");
        new TrampolineFeature(this);
        new NickModule(this);

        Bukkit.createWorld(new WorldCreator("Castle"));
        setGameMap("Castle", "Pixelbiester", Bukkit.getWorld("Castle"));

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
        pluginManager.registerEvents(new LobbyProtection(), this);
        pluginManager.registerEvents(new PlayerInteractListener(), this);
        pluginManager.registerEvents(new InventoryClickListener(), this);
        pluginManager.registerEvents(new PlayerDeathListener(), this);
        pluginManager.registerEvents(new PlayerItemConsumeListener(), this);

        getCommand("start").setExecutor(new StartCommand());
        getCommand("unnick").setExecutor(new UnnickCommand(this));
    }

    public static SpecialPvP getInstance() {
        return instance;
    }

    public IngameManager getIngameManager() {
        return ingameManager;
    }

    public CountdownManager getCountdownManager() {
        return countdownManager;
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public DefaultGameQuitItem getDefaultGameQuitItem() {
        return defaultGameQuitItem;
    }

    public DefaultGameNavigatorItem getDefaultGameNavigatorItem() {
        return defaultGameNavigatorItem;
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
