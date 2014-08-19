package com.github.lyokofirelyte.Divinity.Storage;

import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface DivinityRing extends DivInfo {
	
	public String getDest();
	
	public String[] getCenter();
	
	public Location getCenterLoc();
	
	public boolean isInOperation();
	
	public int getMatId();
	
	public byte getMatByte();
	
	public List<Player> getPlayers();
	
	public void addPlayer(Player name);
	
	public void remPlayer(Player name);
	
	public void setInOperation(boolean a);
}