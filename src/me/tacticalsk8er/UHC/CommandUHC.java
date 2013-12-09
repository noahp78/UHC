package me.tacticalsk8er.UHC;

import me.tacticalsk8er.UHC.Commands.CommandHelp;
import me.tacticalsk8er.UHC.Commands.CommandStart;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/*
 * This is the main class where commands will be executed and handled.
 * If you want to see what each command does, go to the commands folder.
 */

public class CommandUHC implements CommandExecutor{
	
	Main plugin;
	
	public CommandUHC(Main plugin){
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		if(args.length == 0){
			new CommandHelp(sender, args);
		} else if(args.length > 0){
			if(args[0].equalsIgnoreCase("help")){
				new CommandHelp(sender, args);
			}
			if(args[0].equalsIgnoreCase("start")){
				new CommandStart(plugin, sender, args);
			}
			if(args[0].equalsIgnoreCase("stop")){
				
			}
			if(args[0].equalsIgnoreCase("teams")){
				
			}
			if(args[0].equalsIgnoreCase("rules")){
				
			}
		}
		return false;
	}
	
}
