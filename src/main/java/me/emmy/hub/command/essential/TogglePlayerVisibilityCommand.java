package me.emmy.hub.command.essential;

import lombok.Getter;
import me.emmy.hub.player.PlayerState;
import me.emmy.hub.util.CC;
import me.emmy.hub.api.command.BaseCommand;
import me.emmy.hub.api.command.Command;
import me.emmy.hub.api.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Emmy
 * @project Forest
 * @date 16/08/2024 - 21:55
 */
@Getter
public class TogglePlayerVisibilityCommand extends BaseCommand {

    private static final Set<Player> invisiblePlayers = new HashSet<>();
    private static final Set<Player> cooldown = new HashSet<>();

    @Override
    @Command(name = "toggleplayervisibility", aliases = "tpv")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        if (cooldown.contains(player)) {
            player.sendMessage(CC.translate("&cPlease wait 5 seconds before using this command again."));
            return;
        }

        cooldown.add(player);
        Bukkit.getScheduler().runTaskLaterAsynchronously(main, () -> cooldown.remove(player), 100L);

        if (PlayerState.isState(player, PlayerState.FIGHTING)) {
            player.sendMessage(CC.translate("&cYou cannot use this command while in PvP mode."));
            return;
        }

        if (invisiblePlayers.contains(player)) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                player.showPlayer(onlinePlayer);
            }
            player.sendMessage(CC.translate("&aYou are now visible to other players."));
            invisiblePlayers.remove(player);
        } else {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                player.hidePlayer(onlinePlayer);
            }
            player.sendMessage(CC.translate("&cYou are now invisible to other players."));
            invisiblePlayers.add(player);
        }
    }
}
