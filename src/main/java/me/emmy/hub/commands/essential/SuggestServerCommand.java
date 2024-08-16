package me.emmy.hub.commands.essential;

import me.emmy.hub.Forest;
import me.emmy.hub.utils.CC;
import me.emmy.hub.utils.command.BaseCommand;
import me.emmy.hub.utils.command.Command;
import me.emmy.hub.utils.command.CommandArgs;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Emmy
 * @project Forest
 * @date 14/08/2024 - 22:06
 */
public class SuggestServerCommand extends BaseCommand {
    @Override
    @Command(name = "suggestserver")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        List<String> servers = new ArrayList<>(Forest.getInstance().getConfig("menus.yml").getConfigurationSection("menus.server_selector.servers").getKeys(false));

        player.closeInventory();
        player.sendMessage(CC.translate("&d&oYou should give &f&o" + servers.get(new Random().nextInt(servers.size())) + " &d&oa try!"));
    }
}
