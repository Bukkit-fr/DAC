package fr.aumgn.dac;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class DACPlayer {

	private Player player;
	private DACColor color;
	private String displayName;
	private Location startLocation;
	private int lives;
	private boolean mustConfirmate;
	private int index;

	public DACPlayer(DAC plugin, Player player, DACColor color) {
		this.player = player;
		this.color = color;
		this.lives = 0;
		String name = ChatColor.stripColor(player.getDisplayName());
		displayName = this.color.getChatColor() + name + ChatColor.WHITE; 
		startLocation = player.getLocation(); 
	}
	
	public void init(DACGame dacGame, int i) {
		this.index = i;
		mustConfirmate = false;
	}
	
	public int getIndex() {
		return index;
	}
	
	public int getPosition() {
		return index + 1;
	}

	public String getDisplayName() {
		return displayName;
	}

	public DACColor getColor() {
		return color;
	}

	public Player getPlayer() {
		return player;
	}

	public void tpToStart() {
		player.teleport(startLocation);
	}

	public boolean hasLost() {
		return lives < 0;
	}
	
	public int getLives() {
		return lives;
	}
	
	public void resetLives() {
		lives =  0; 
	}
	
	public void winLive() {
		lives++;
	}
	
	public void looseLive() {
		lives--;
	}
	
	public void looseAllLives() {
		lives = -1;
	}

	public void setMustConfirmate(boolean bool) {
		mustConfirmate = bool;
	}

	public boolean mustConfirmate() {
		return mustConfirmate;
	}

}
