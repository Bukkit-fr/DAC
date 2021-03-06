package fr.aumgn.dac;

import java.util.ArrayList;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.aumgn.dac.config.DACArena;

public class DACJoinStep {
	
	private DAC plugin;
	private DACArena arena;
	private HashSet<DACColor> colors;
	private ArrayList<DACPlayer> players;

	private ChatColor G = ChatColor.BLUE;
	
	public DACJoinStep(DAC plugin, DACArena arena) {
		this.plugin = plugin;
		this.arena = arena;
		colors = new HashSet<DACColor>();
		players = new ArrayList<DACPlayer>();
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(G + 
					"Une nouvelle partie de Dé à coudre dans " + arena.getName() + " a été débutée.");
			player.sendMessage(G +
					"Utilisez \"/dac join\" dans la zone de départ pour la rejoindre.");
		}
	}
	
	public DACArena getArena() {
		return arena;
	}

	public ArrayList<DACPlayer> getPlayers() {
		return players;
	}

	public void notify(String message) {
		for (DACPlayer player : players) {
			player.getPlayer().sendMessage(message);
		}
	}
	
	public boolean isMinReached() {
		return (players.size() > 1);
	}
	
	public boolean isMaxReached() {
		return (players.size() == 12);
	}
	
	private boolean isColorAvailable(String name) {
		DACColor color = DACColor.get(name);
		if (color == null) {
			return false;
		} else {
			return isColorAvailable(color);
		}
	}
	
	private boolean isColorAvailable(DACColor color) {
		return !colors.contains(color);
	}
	
	private DACColor getFirstColorAvailable() {
		for (DACColor color : DACColor.values()) {
			if (!colors.contains(color)) {
				return color;
			}
		}
		// Should never be reached;
		return DACColor.first();
	}

	public void addPlayer(Player player, String[] names) {
		int i = 0;
		DACColor color;
		while (i < names.length && !isColorAvailable(names[i])) { i++; }
		if (i == names.length) {
			color = getFirstColorAvailable();
		} else {
			color = DACColor.get(names[i]);
		}
		addPlayer(player, color);
	}

	private void addPlayer(Player player, DACColor color) {
		if (players.size() > 0) {
			player.sendMessage(G + "Joueurs Actuels :");
			for (DACPlayer dacPlayer : players) {
				player.sendMessage("  " + dacPlayer.getDisplayName());
			}
		}
		DACPlayer dacPlayer = new DACPlayer(plugin, player, color);
		players.add(dacPlayer);
		colors.add(color);
		notify(dacPlayer.getDisplayName() + G + " a rejoint la partie");
	}

	public boolean contains(Player player) {
		for (DACPlayer dacPlayer : players) {
			if (dacPlayer.getPlayer().equals(player)) {
				return true;
			}
		}
		return false;
	}

	public void remove(Player player) {
		for (DACPlayer dacPlayer : players) {
			if (dacPlayer.getPlayer().equals(player)) {
				notify(dacPlayer.getDisplayName() + G + " a quitté la partie.");				
				players.remove(dacPlayer);
				colors.remove(dacPlayer.getColor());
				return;
			}
		}
	}

	public void stop() {
		for (DACPlayer player : players) {
			player.getPlayer().sendMessage(G +
				"La partie a été arretée.");
		}
	}

}
