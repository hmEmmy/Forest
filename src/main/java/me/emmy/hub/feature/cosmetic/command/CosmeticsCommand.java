package me.emmy.hub.feature.cosmetic.command;

import me.emmy.hub.feature.cosmetic.menu.CosmeticsMenu;
import me.emmy.hub.api.command.BaseCommand;
import me.emmy.hub.api.command.Command;
import me.emmy.hub.api.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Forest
 * @date 16/08/2024 - 16:55
 */
public class CosmeticsCommand extends BaseCommand {
    @Override
    @Command(name = "cosmetics")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        new CosmeticsMenu().openMenu(player);
    }
}
