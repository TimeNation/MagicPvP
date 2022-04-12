package net.timenation.magicpvp.commands;

import net.timenation.magicpvp.MagicPvP;
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
            if(Bukkit.getOnlinePlayers().size() >= MagicPvP.getInstance().getNeededPlayers()) {
                if(MagicPvP.getInstance().getCountdownManager().getCountdown() > 10) {
                    MagicPvP.getInstance().getCountdownManager().startGame();
                    player.sendMessage(I18n.format(player, "api.game.messages.startgame", MagicPvP.getInstance().getPrefix()));
                } else {
                    if(MagicPvP.getInstance().getGameState().equals(GameState.INGAME)) {
                        player.sendMessage(I18n.format(player, "api.game.messages.isingame", MagicPvP.getInstance().getPrefix()));
                    } else if(MagicPvP.getInstance().getGameState().equals(GameState.ENDING)) {
                        player.sendMessage(I18n.format(player, "api.game.messages.gameisended", MagicPvP.getInstance().getPrefix()));
                    } else {
                        player.sendMessage(I18n.format(player, "api.game.messages.gamealreadystarts", MagicPvP.getInstance().getPrefix()));
                    }
                }
            } else {
                player.sendMessage(I18n.format(player, "api.game.messages.notenoughplayerstostart", MagicPvP.getInstance().getPrefix()));
            }
        } else {
            player.sendMessage(I18n.format(player, "api.velocity.messages.nopermissions", MagicPvP.getInstance().getPrefix()));
        }
        return false;
    }
}
