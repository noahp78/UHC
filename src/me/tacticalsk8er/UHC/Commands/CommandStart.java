package me.tacticalsk8er.UHC.Commands;

import me.tacticalsk8er.UHC.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
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

	public CommandStart(Main plugin, CommandSender sender, String[] args) {

		int Raduis = plugin.getConfig().getInt("GameSettings.Raduis");

		sender.sendMessage("Creating New World");
		WorldCreator UHCreate = new WorldCreator("UHC");
		final World UHC = UHCreate.createWorld();
		UHC.setDifficulty(Difficulty.HARD);
		UHC.setPVP(true);
		UHC.setGameRuleValue("naturalRegeneration", "false");

		

		// Teleport players to new world
		sender.sendMessage("Teleporting Players");
		StringBuilder players = new StringBuilder();
		for (Player p : plugin.getServer().getOnlinePlayers()) {
			Location tele = UHC.getSpawnLocation();
			p.teleport(tele);
			players.append(p.getName() + " ");
			p.sendMessage(ChatColor.GOLD + "[UHC]" + ChatColor.GREEN + "Game Starting soon, prepare for some lag.");
			plugin.PlayerCount++;

		}

		sender.sendMessage("Spreading Players");
		plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "spreadplayers 0 0 " + plugin.getConfig().getInt("GameSettings.SpreadDistance") + " " + plugin.getConfig().getInt("GameSettings.Raduis") + " true " + players);
		// Set up Walls (MAY CAUSE LAG)
		// TODO Test to see if this causes lag
		for (int i = 0; i <= Raduis; i++) {
			for (int y = 0; y <= 128; y++) {
				Block block = UHC.getBlockAt(Raduis - i, y, Raduis);
				Block block2 = UHC.getBlockAt(Raduis, y, Raduis - i);
				block.setType(Material.BEDROCK);
				block2.setType(Material.BEDROCK);
			}
			for (int y = 0; y <= 128; y++) {
				Block block = UHC.getBlockAt(-Raduis - i, y, Raduis);
				Block block2 = UHC.getBlockAt(-Raduis, y, Raduis - i);
				block.setType(Material.BEDROCK);
				block2.setType(Material.BEDROCK);
			}
			for (int y = 0; y <= 128; y++) {
				Block block = UHC.getBlockAt(Raduis - i, y, -Raduis);
				Block block2 = UHC.getBlockAt(Raduis, y, -Raduis - i);
				block.setType(Material.BEDROCK);
				block2.setType(Material.BEDROCK);
			}
			for (int y = 0; y <= 128; y++) {
				Block block = UHC.getBlockAt(-Raduis - i, y, -Raduis);
				Block block2 = UHC.getBlockAt(-Raduis, y, -Raduis - i);
				block.setType(Material.BEDROCK);
				block2.setType(Material.BEDROCK);
			}
		}
		// Timed Start
		plugin.GameCountDown = true;
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

			int seconds = 5;

			public void run() {
				if (seconds != 0) {
					Bukkit.getServer().broadcastMessage(seconds + " second(s) remaining!");
					for (Player p : UHC.getPlayers()) {
						p.playNote(p.getLocation(), Instrument.PIANO, Note.natural(0, Note.Tone.C));
					}
					seconds--;
				} else if (seconds = 0) {
					Bukkit.getServer().broadcastMessage("Game Started!");
					for (Player p : UHC.getPlayers()) {
						p.playNote(p.getLocation(), Instrument.PIANO, Note.natural(1, Note.Tone.C));
						p.setFoodLevel(20);
						Main.GameCountDown = false;
						Main.GameStarted = true;
					}
				}
			}

		}, 20L, 100L);

	}

}
