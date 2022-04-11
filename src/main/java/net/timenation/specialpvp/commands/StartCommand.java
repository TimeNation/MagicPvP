package net.timenation.specialpvp.commands;

import net.timenation.specialpvp.SpecialPvP;
import net.timenation.timespigotapi.manager.game.gamestates.GameState;
import net.timenation.timespigotapi.manager.language.I18n;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StartCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;

        if(player.hasPermission("timenation.forcestart")) {
            if(Bukkit.getOnlinePlayers().size() >= SpecialPvP.getInstance().getNeededPlayers()) {
                if(SpecialPvP.getInstance().getCountdownManager().getCountdown() > 10) {
                    SpecialPvP.getInstance().getCountdownManager().startGame();
                    player.sendMessage(I18n.format(player, "api.game.messages.startgame", SpecialPvP.getInstance().getPrefix()));
                } else {
                    if(SpecialPvP.getInstance().getGameState().equals(GameState.INGAME)) {
                        player.sendMessage(I18n.format(player, "api.game.messages.isingame", SpecialPvP.getInstance().getPrefix()));
                    } else if(SpecialPvP.getInstance().getGameState().equals(GameState.ENDING)) {
                        player.sendMessage(I18n.format(player, "api.game.messages.gameisended", SpecialPvP.getInstance().getPrefix()));
                    } else {
                        player.sendMessage(I18n.format(player, "api.game.messages.gamealreadystarts", SpecialPvP.getInstance().getPrefix()));
                    }
                }
            } else {
                player.sendMessage(I18n.format(player, "api.game.messages.notenoughplayerstostart", SpecialPvP.getInstance().getPrefix()));
            }
        } else {
            player.sendMessage(I18n.format(player, "api.velocity.messages.nopermissions", SpecialPvP.getInstance().getPrefix()));
        }
        return false;
    }
}
