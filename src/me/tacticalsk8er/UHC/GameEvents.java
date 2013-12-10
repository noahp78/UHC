package me.tacticalsk8er.UHC;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Team;

/*
 * Ever UHC game going will use this listener to run the game, and add features such as spectating.
 * This class requires:
 * 	- Main Class (JavaPlugin)
 * 	- Game World (The world that a game is being held in)
 */

public class GameEvents implements Listener {
	
	Main plugin;
	World GameWorld;
	Team spectator;
	
	public GameEvents(Main plugin, World GameWorld){
		this.plugin = plugin;
		this.GameWorld = GameWorld;
		
		//Setup Spectator Team
		spectator = plugin.setupSpectatorTeam();
	}
	
	@EventHandler
	public void onPlayerGameDeath(PlayerDeathEvent e){
		Main.PlayerCount=(Main.PlayerCount-1);
		if (Main.PlayerCount==0) {
			e.setDeathMessage(e.getEntity().getName() + ChatColor.GREEN + " Won the game!");
		}
		if (!(Main.PlayerCount==0)) {
			e.setDeathMessage("Player " + e.getEntity().getName().toString() + " Died and Didn't win");
		}
		
	}
	@SuppressWarnings("deprecation")
	public void onPlayerLeave(PlayerQuitEvent e) {
		e.getPlayer().setHealth(0);
		
	}
	
	//Used for stopping player movement when count down timer is going.
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
		
	}
	
}
