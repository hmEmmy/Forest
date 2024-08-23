package me.emmy.hub.spawn.command;

import me.emmy.hub.Forest;
import me.emmy.hub.util.CC;
import me.emmy.hub.api.command.BaseCommand;
import me.emmy.hub.api.command.Command;
import me.emmy.hub.api.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Forest
 * @date 14/08/2024
 */
public class SetSpawnCommand extends BaseCommand {
    @Override
    @Command(name = "setspawn", permission = "forest.staff")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        boolean enableSpawnTeleport = Forest.getInstance().getConfigHandler().getConfig("settings.yml").getBoolean("spawn.teleporting", true);

        Forest.getInstance().getSpawnHandler().saveLocation(player.getLocation());
        player.sendMessage(CC.translate(Forest.getInstance().getConfigHandler().getConfig("messages.yml").getString("messages.spawn-set")));

        if (!enableSpawnTeleport) {
            player.sendMessage(CC.translate(Forest.getInstance().getConfigHandler().getConfig("messages.yml").getString("messages.spawn-disabled")));
            System.out.println(" ");
            System.out.println(Forest.getInstance().getConfigHandler().getConfig("messages.yml").getString("messages.spawn-disabled-console"));
            System.out.println(" ");
        }
    }
}