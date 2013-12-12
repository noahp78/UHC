package me.tacticalsk8er.UHC;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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
		Main.PlayerCount = (Main.PlayerCount - 1);
		Player p = e.getEntity();

		if (Main.PlayerCount == 0) {
			e.setDeathMessage(p.getName() + ChatColor.GREEN + " Won the game!");
		}
		if (!(Main.PlayerCount == 0)) {
			e.setDeathMessage("Player " + p.getName().toString() + " Died");
			Main.PlayerCount--;
			spectators.addPlayer(p);
			p.setFlying(true);
			// Invisibility for spectators
			for (Player players : plugin.getServer().getOnlinePlayers()) {
				players.hidePlayer(p);
			}
		}

	}

	// Player loses if player leaves
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		e.getPlayer().setHealth(0);
		Main.PlayerCount--;
	}

	// Used for stopping player movement when count down timer is going.
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if (plugin.GameCountDown)
			e.setCancelled(true);

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

	@EventHandler
	public void OnLogin(PlayerJoinEvent event) {
		// Kick player on join if the game has started...
		if (plugin.GameStarted) {

			event.setJoinMessage("");
			event.getPlayer().kickPlayer("Game In Progress - Try again later");

		}
	}

}
