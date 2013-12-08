package me.tacticalsk8er.UHC.Commands;

import me.tacticalsk8er.UHC.Main;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandStart {
	
	public CommandStart(Main plugin, CommandSender sender, String[] args){
		sender.sendMessage("Creating New World");
		WorldCreator UHCreate = new WorldCreator("UHC");
		World UHC = UHCreate.createWorld();
		
		for(Player p : plugin.getServer().getOnlinePlayers()){
			Location tele = new Location(UHC, 0.0, 65.0, 0.0);
			p.teleport(tele);
		}
	}
	
}
