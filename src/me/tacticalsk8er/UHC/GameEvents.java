package me.tacticalsk8er.UHC;

import org.bukkit.ChatColor;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
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
	Team spectators;

	public GameEvents(Main plugin, Team spectators) {
		this.plugin = plugin;

		this.spectators = spectators;
	}

	// When player dies, player joins spectators, is invisible, and can fly.
	@EventHandler
	public void onPlayerGameDeath(PlayerDeathEvent e) {
		if (Main.GameStarted) {
			Main.PlayerCount = (Main.PlayerCount - 1);
			Player p = e.getEntity();

			if (Main.PlayerCount == 0) {
				e.setDeathMessage("Player " + p.getName().toString() + " Died");
				spectators.addPlayer(p);
				p.setAllowFlight(true);
				p.setFlying(true);
				// Invisibility for spectators
				for (Player players : plugin.getServer().getOnlinePlayers()) {
					players.hidePlayer(p);
				}
				for (Player player : plugin.getServer().getOnlinePlayers()) {
					if (!(plugin.Spectators.hasPlayer(player))) {
						plugin.getServer().broadcastMessage(player.getName() + ChatColor.GREEN + " Won the game!");
						spectators.addPlayer(player);
						player.setFlying(true);
						// Invisibility for spectators
						for (Player players : plugin.getServer().getOnlinePlayers()) {
							players.hidePlayer(player);
						}
					}
				}
			}
			if (!(Main.PlayerCount == 0)) {
				e.setDeathMessage("Player " + p.getName().toString() + " Died");
				Main.PlayerCount--;
				spectators.addPlayer(p);
				p.setAllowFlight(true);
				p.setFlying(true);
				// Invisibility for spectators
				for (Player players : plugin.getServer().getOnlinePlayers()) {
					players.hidePlayer(p);
				}
			}
		}

	}

	// Player loses if player leaves
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		if (Main.GameStarted) {
			e.getPlayer().setHealth(0);
			Main.PlayerCount--;
		}
	}

	// Used for stopping player movement when count down timer is going.
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if (plugin.GameCountDown) {
			e.getPlayer().teleport(e.getFrom());
			e.getPlayer().sendMessage("You are not allowed to move till the countdown is finished!");
		}

	}

	// Spectators Can't Break Blocks
	@EventHandler
	public void onSpectatorBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if (spectators.hasPlayer(p)) {
			e.setCancelled(true);
			p.sendMessage("You can't break blocks while spectating!");
		}
	}

	// Spectator Can't Hurt Players
	@EventHandler
	public void onSpectatorHurtPlayer(EntityDamageByEntityEvent e) {
		if ((e.getEntity() instanceof Player) && (e.getDamager() instanceof Player)) {
			Player damager = (Player) e.getEntity();
			if (spectators.hasPlayer(damager)) {
				e.setCancelled(true);
				damager.sendMessage("You can't hurt players while spectating!");
			}
		}
	}
// kick player if game has started, NEEDS Checking if the player died ingame or not (crashes on world generation, read time outs)
	@EventHandler
	public void OnLogin(PlayerJoinEvent event) {
		if (plugin.GameStarted) {
			event.setJoinMessage("");
			event.getPlayer().kickPlayer("Game In Progress - Try again later");}
		

		}
		
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event){
		//see if the enderdragon died
		Entity entity = event.getEntity();
		if (entity instanceof EnderDragon){
			if (plugin.getConfig().getBoolean("GameSettings.EnderdragonGameEnd")){
				if (event.getEntity().getKiller() != null) {
					 Player player = event.getEntity().getKiller();
					 plugin.getServer().broadcastMessage(player.getName() + ChatColor.GREEN + player.getName().toString() + "won the games by killing the EnderDragon");
					 
			}
			
			
			
		}
	}

}
}

