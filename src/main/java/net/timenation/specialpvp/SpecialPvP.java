package net.timenation.specialpvp;

import com.google.gson.JsonParser;
import net.timenation.specialpvp.commands.StartCommand;
import net.timenation.specialpvp.commands.UnnickCommand;
import net.timenation.specialpvp.listener.*;
import net.timenation.specialpvp.manager.*;
import net.timenation.specialpvp.manager.kits.KitManager;
import net.timenation.specialpvp.manager.skills.LightningLastHittetPlayerSkill;
import net.timenation.timespigotapi.manager.game.TimeGame;
import net.timenation.timespigotapi.manager.game.defaultitems.DefaultGameExplainItem;
import net.timenation.timespigotapi.manager.game.defaultitems.DefaultGameNavigatorItem;
import net.timenation.timespigotapi.manager.game.defaultitems.DefaultGameQuitItem;
import net.timenation.timespigotapi.manager.game.features.TrampolineFeature;
import net.timenation.timespigotapi.manager.game.gamestates.GameState;
import net.timenation.timespigotapi.manager.game.manager.ConfigManager;
import net.timenation.timespigotapi.manager.game.modules.ForcemapModule;
import net.timenation.timespigotapi.manager.game.modules.NickModule;
import net.timenation.timespigotapi.manager.game.scoreboard.ScoreboardManager;
import net.timenation.timespigotapi.manager.game.team.TeamManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public final class SpecialPvP extends TimeGame {

    private static SpecialPvP instance;
    private IngameManager ingameManager;
    private CountdownManager countdownManager;
    private InventoryManager inventoryManager;
    private KitManager kitManager;
    private ManaManager manaManager;
    private SpecialPvPConfig specialPvPConfig;
    private DefaultGameQuitItem defaultGameQuitItem;

    @Override
    public void onEnable() {
        instance = this;
        ingameManager = new IngameManager();
        countdownManager = new CountdownManager(this);
        inventoryManager = new InventoryManager();
        kitManager = new KitManager();
        manaManager = new ManaManager();
        specialPvPConfig = new SpecialPvPConfig();
        defaultGameQuitItem = new DefaultGameQuitItem(this, 7);

        setPrefix("SpecialPvP");
        setColor("§9");
        setSecoundColor("§9");
        setGameState(GameState.LOBBY);
        setScoreboardManager(new ScoreboardManager(this));
        setGameWithKits(true);
        setCountdown(60);

        new DefaultGameNavigatorItem(this, 1);
        new DefaultGameExplainItem(this,6, "api.game.specialpvp.explain");

        new TrampolineFeature(this);
        new NickModule(this);
        new ForcemapModule(this);

        new LightningLastHittetPlayerSkill(this);

        for (File file : new File("plugins/SpecialPvP/maps").listFiles()) {
            try {
                Bukkit.createWorld(new WorldCreator(new JsonParser().parse(new FileReader(file)).getAsJsonObject().get("mapWorld").getAsString()));
            } catch (FileNotFoundException ignored) {}
        }
        setRandomGameMap();

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

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    public ManaManager getManaManager() {
        return manaManager;
    }

    public SpecialPvPConfig getSpecialPvPConfig() {
        return specialPvPConfig;
    }

    public DefaultGameQuitItem getDefaultGameQuitItem() {
        return defaultGameQuitItem;
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
        return specialPvPConfig.getInt("neededPlayers");
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
