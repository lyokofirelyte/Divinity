package com.github.lyokofirelyte.Divinity.Storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.Divinity.Divinity;

public class DivinityRing {

	private Divinity main;
	
	private String name;
	private String destination;
	private String[] center;
	private List<Player> players;
	private boolean inOperation = false;
	private Map<Integer, Byte> mat = new HashMap<Integer, Byte>();
	
	public DivinityRing(Divinity i, String name){
		main = i;
		this.name = name;
	}
	
	public String name(){
		return name;
	}
	
	public String[] getCenter(){
		return center;
	}
	
	public String getDest(){
		return destination;
	}
	
	public Location getCenterLoc(){
		return new Location(Bukkit.getWorld(center[0]), Double.parseDouble(center[1]), Double.parseDouble(center[2]), Double.parseDouble(center[3]), Float.parseFloat(center[4]), Float.parseFloat(center[5]));
	}
	
	public boolean isInOperation(){
		return inOperation;
	}
	
	public int getMatId(){
		return new ArrayList<Integer>(mat.keySet()).get(0);
	}
	
	public byte getMatByte(){
		return new ArrayList<Byte>(mat.values()).get(0);
	}
	
	public List<Player> getPlayers(){
		return players;
	}
	
	public void addPlayer(Player name){
		if (!players.contains(name)){
			players.add(name);
		}
	}
	
	public void remPlayer(Player name){
		if (players.contains(name)){
			players.remove(name);
		}
	}
	
	public void setCenter(String[] c){
		center = c;
	}
	
	public void setCenter(String c){
		center = c.split(" ");
	}
	
	public void setDest(String dest){
		destination = dest;
	}
	
	public void setRingMaterial(int id, byte by){
		mat.put(id, by);
	}
	
	public void setInOperation(boolean b){
		inOperation = b;
	}
}