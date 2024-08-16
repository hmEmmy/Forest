package me.emmy.hub.feature.pvpmode.command.admin;

import me.emmy.hub.Forest;
import me.emmy.hub.utils.CC;
import me.emmy.hub.utils.command.BaseCommand;
import me.emmy.hub.utils.command.Command;
import me.emmy.hub.utils.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Forest
 * @date 16/08/2024 - 20:16
 */
public class SetPvPSpawnCommand extends BaseCommand {
    @Override
    @Command(name = "setpvpspawn", permission = "forest.admin")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        Forest.getInstance().getPvpModeRepository().setPvPSpawn(player.getLocation());
        player.sendMessage(CC.translate(Forest.getInstance().getConfig("messages.yml").getString("messages.set-pvp-spawn")));
    }
}
