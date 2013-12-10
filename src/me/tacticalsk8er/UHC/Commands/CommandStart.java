package me.tacticalsk8er.UHC.Commands;

import me.tacticalsk8er.UHC.Main;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Note;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/*
 * Starts a UHC game.
 * This class requires:
 * 	- Main Class (JavaPlugin)
 * 	- CommandSender (To send important messages)
 *  - Command Args (To get other info for future features)
 */

public class CommandStart {
	
	public CommandStart(Main plugin, CommandSender sender, String[] args){
		sender.sendMessage("Creating New World");
		WorldCreator UHCreate = new WorldCreator("UHC");
		final World UHC = UHCreate.createWorld();
		UHC.setDifficulty(Difficulty.HARD);
		UHC.setPVP(true);
		UHC.setGameRuleValue("naturalRegeneration", "false");
		
		sender.sendMessage("Teleporting Players");
		StringBuilder players = new StringBuilder();
		for(Player p : plugin.getServer().getOnlinePlayers()){
			Location tele = UHC.getSpawnLocation();
			p.teleport(tele);
			players.append(p.getName() + " ");
		}
		
		sender.sendMessage("Spreading Players");
		plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "spreadplayers 0 0 0 5000 false " + players);
		
		//Timed Start
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			int s = 5;
			public void run() {
				if(s!=0){
					Bukkit.getServer().broadcastMessage(s + " second(s) remaining!");
					for(Player p: UHC.getPlayers()){
						p.playNote(p.getLocation(), Instrument.PIANO, Note.natural(0, Note.Tone.C));
					}
				} else if (s <= 0) {
					Bukkit.getServer().broadcastMessage("Game Started!");
					for(Player p: UHC.getPlayers()){
						p.playNote(p.getLocation(), Instrument.PIANO, Note.natural(1, Note.Tone.C));
					}
				}
			}
			
		}, 20L, 100L);
		
	}
	
}
