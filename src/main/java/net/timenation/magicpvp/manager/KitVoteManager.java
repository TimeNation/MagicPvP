package net.timenation.magicpvp.manager;

import net.timenation.magicpvp.MagicPvP;
import net.timenation.magicpvp.manager.kits.KitType;
import net.timenation.timespigotapi.manager.language.I18n;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class KitVoteManager {

    private final HashMap<KitType, Integer> kitVoteMap;
    private final HashMap<Player, KitType> playerVotedMap;

    public KitVoteManager() {
        this.kitVoteMap = new HashMap<>();
        this.playerVotedMap = new HashMap<>();

        for (KitType kit : KitType.values()) {
            this.kitVoteMap.put(kit, 0);
        }
    }

    public void playerVoteForKit(Player player, KitType kitType) {
        if (!MagicPvP.getInstance().getKitVoteManager().hasPlayerVotedForKit(player, kitType)) {
            if(MagicPvP.getInstance().getKitVoteManager().playerHasVotedForKit(player)) MagicPvP.getInstance().getKitVoteManager().removeVoteFromKit(MagicPvP.getInstance().getKitVoteManager().getVotedKitFromPlayer(player));
            MagicPvP.getInstance().getKitVoteManager().addVoteToKit(player, kitType);
            player.sendMessage(I18n.format(player, MagicPvP.getInstance().getPrefix(), "api.game.messages.voteforkit", MagicPvP.getInstance().getColor() + "§l" + kitType.getKitTranslateKey(player)));
            player.closeInventory();
        } else {
            player.sendMessage(I18n.format(player, MagicPvP.getInstance().getPrefix(), "api.game.messages.alreadyvotedforkit", MagicPvP.getInstance().getColor() + "§l" + kitType.getKitTranslateKey(player)));
        }
    }

    public void addVoteToKit(Player player, KitType kit) {
        this.kitVoteMap.put(kit, this.kitVoteMap.get(kit) + 1);
        this.playerVotedMap.put(player, kit);
    }

    public void removeVoteFromKit(KitType kit) {
        this.kitVoteMap.put(kit, this.kitVoteMap.get(kit) - 1);
    }

    public int getVotesFromKit(KitType kit) {
        return this.kitVoteMap.get(kit);
    }

    public KitType getVotedKitFromPlayer(Player player) {
        return this.playerVotedMap.get(player);
    }

    public KitType getKitWithMaxVotes() {
        for (Map.Entry<KitType, Integer> kitTypeIntegerEntry : this.kitVoteMap.entrySet()) {
            if(kitTypeIntegerEntry.getValue().equals(Collections.max(this.kitVoteMap.values()))) {
                return kitTypeIntegerEntry.getKey();
            }
        }

        return null;
    }

    public boolean hasPlayerVotedForKit(Player player, KitType kitType) {
        return kitType == this.playerVotedMap.get(player);
    }

    public boolean playerHasVotedForKit(Player player) {
        return this.playerVotedMap.get(player) != null;
    }

    public HashMap<KitType, Integer> getKitVoteMap() {
        return kitVoteMap;
    }

    public HashMap<Player, KitType> getPlayerVotedMap() {
        return playerVotedMap;
    }
}
