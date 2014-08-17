package com.github.lyokofirelyte.Divinity.Storage;

import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface DivinityRing {
	
	public String name();
	
	public String getDest();
	
	public String[] getCenter();
	
	public Location getCenterLoc();
	
	public boolean isInOperation();
	
	public int getMatId();
	
	public byte getMatByte();
	
	public List<Player> getPlayers();
	
	public void addPlayer(Player name);
	
	public void remPlayer(Player name);
	
	public void setCenter(String[] c);
	
	public void setCenter(String c);
	
	public void setDest(String dest);
	
	public void setRingMaterial(int id, byte by);
	
	public void setInOperation(boolean b);
}