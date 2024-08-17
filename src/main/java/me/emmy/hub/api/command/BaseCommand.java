package me.emmy.hub.api.command;

import me.emmy.hub.Forest;

public abstract class BaseCommand {

	public Forest main = Forest.getInstance();

	public BaseCommand() {
		main.getCommandFramework().registerCommands(this);
	}

	public abstract void onCommand(CommandArgs command);

}
