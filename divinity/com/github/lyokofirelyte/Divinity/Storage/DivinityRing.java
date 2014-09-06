package com.github.lyokofirelyte.Divinity.Storage;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.github.lyokofirelyte.Divinity.Divinity;

public class DivinityRing extends DivinityStorage  {
	
	public DivinityRing(String n, Divinity i) {
		super(n, i);
	}

	private List<org.bukkit.entity.Player> players;
	private boolean inOperation = false;
	
	public String[] getCenter(){
		return getStr(DRS.CENTER).split(" ");
	}
	
	public String getDest(){
		return getStr(DRS.DEST);
	}
	
	public Location getCenterLoc(){
		return new Location(Bukkit.getWorld(getCenter()[0]), Double.parseDouble(getCenter()[1]), Double.parseDouble(getCenter()[2]), Double.parseDouble(getCenter()[3]), Float.parseFloat(getCenter()[4]), Float.parseFloat(getCenter()[5]));
	}
	
	public boolean isInOperation(){
		return inOperation;
	}
	
	public int getMatId(){
		return getInt(DRS.MAT_ID);
	}
	
	public byte getMatByte(){
		return getByte(DRS.BYTE_ID);
	}
	
	public List<org.bukkit.entity.Player> getPlayers(){
		return players;
	}
	
	public void addPlayer(org.bukkit.entity.Player name){
		if (!players.contains(name)){
			players.add(name);
		}
	}
	
	public void remPlayer(org.bukkit.entity.Player name){
		if (players.contains(name)){
			players.remove(name);
		}
	}
	
	public void setInOperation(boolean b){
		inOperation = b;
	}
}