package com.github.lyokofirelyte.Divinity.Storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import com.github.lyokofirelyte.Divinity.Divinity;

public class DivinityStorage {

	public Divinity api;
	public UUID uuid;
	
	private String name;
	private String gameName;
	private boolean isGame = false;
	
	public DivinityStorage(UUID u, Divinity i){
		api = i;
		uuid = u;
		name = Bukkit.getPlayer(u) != null ? Bukkit.getPlayer(u).getName() : Bukkit.getOfflinePlayer(u).getName();
	}

	public DivinityStorage(String n, Divinity i){
		api = i;
		name = n;
	}
	
	public DivinityStorage(String gameType, String n, Divinity i){
		gameName = gameType;
		name = n;
		api = i;
		isGame = true;
	}
	
	public Map<String, Object> stuff = new HashMap<String, Object>();
	
	public String name(){
		return name;
	}
	
	public UUID uuid(){
		return uuid;
	}
	
	public Divinity api(){
		return api;
	}
	
	public boolean isGame(){
		return isGame;
	}
	
	public String gameName(){
		return isGame ? gameName : "none";
	}
	
	public Object getRawInfo(Enum<?> i){
		return stuff.containsKey(i.toString()) ? stuff.get(i.toString()) : null;
	}
	
	public String getStr(Enum<?> i){

		if (stuff.containsKey(i.toString())){
			if (stuff.get(i.toString()) instanceof String){
				return (String) stuff.get(i.toString());
			} else {
				return stuff.get(i.toString()) + "";
			}
		} else {
			return "none";
		}
	}
	
	public int getInt(Enum<?> i){

		if (stuff.containsKey(i.toString())){
			if (stuff.get(i.toString()) instanceof Integer){
				return (Integer) stuff.get(i.toString());
			} else {
				return api.divUtils.isInteger(stuff.get(i.toString()) + "") ? Integer.parseInt(stuff.get(i.toString()) + "") : 0;
			}
		} else {
			return 0;
		}
	}
	
	public long getLong(Enum<?> i){

		if (stuff.containsKey(i.toString())){
			if (stuff.get(i.toString()) instanceof Long){
				return (Long) stuff.get(i.toString());
			} else {
				try {
					return Long.parseLong(stuff.get(i.toString()) + "");
				} catch (Exception e){
					return 0L;
				}
			}
		} else {
			return 0L;
		}
	}
	
	public byte getByte(Enum<?> i){

		if (stuff.containsKey(i.toString())){
			if (stuff.get(i.toString()) instanceof Byte){
				return (Byte) stuff.get(i.toString());
			} else {
				try {
					return Byte.parseByte(stuff.get(i.toString()) + "");
				} catch (Exception e){
					return 0;
				}
			}
		} else {
			return 0;
		}
	}
	
	public double getDouble(Enum<?> i){

		if (stuff.containsKey(i.toString())){
			if (stuff.get(i.toString()) instanceof Double){
				return (Double) stuff.get(i.toString());
			} else {
				try {
					return Double.parseDouble(stuff.get(i.toString()) + "");
				} catch (Exception e){
					return 0D;
				}
			}
		} else {
			return 0D;
		}
	}
	
	public boolean getBool(Enum<?> i){

		if (stuff.containsKey(i.toString())){
			if (stuff.get(i.toString()) instanceof Boolean){
				return (Boolean) stuff.get(i.toString());
			} else {
				try {
					return Boolean.valueOf(stuff.get(i.toString()) + "");
				} catch (Exception e){
					return false;
				}
			}
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> getList(Enum<?> i){
		
		if (stuff.containsKey(i.toString())){
			try {
				if (stuff.get(i.toString()) instanceof List){
					return (List<String>) stuff.get(i.toString());
				}
				stuff.put(i.toString(), new ArrayList<String>());
			} catch (Exception e){
				stuff.put(i.toString(), new ArrayList<String>());
			}
		} else {
			stuff.put(i.toString(), new ArrayList<String>());
		}
		
		return (List<String>) stuff.get(i.toString());
	}
	
	public Location getLoc(Enum<?> i){
		
		if (stuff.containsKey(i.toString())){
			if (stuff.get(i.toString()) instanceof Location){
				return (Location) stuff.get(i.toString());
			}
			try {
				String[] loc = stuff.get(i.toString()).toString().split(" ");
				if (loc.length == 4){
					return new Location(Bukkit.getWorld(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2]), Double.parseDouble(loc[3]));
				} else if (loc.length == 6){
					return new Location(Bukkit.getWorld(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2]), Double.parseDouble(loc[3]), Float.parseFloat(loc[4]), Float.parseFloat(loc[5]));
				}
			} catch (Exception e){
				return new Location(Bukkit.getWorld("world"), 0, 0, 0);
			}
		}
		
		return new Location(Bukkit.getWorld("world"), 0, 0, 0);
	}
	
	@SuppressWarnings("unchecked")
	public List<ItemStack> getStack(Enum<?> i){
		
		if (stuff.containsKey(i.toString())){
			if (stuff.get(i.toString()) instanceof List){
				return (List<ItemStack>) stuff.get(i.toString());
			}
			stuff.put(i.toString(), new ArrayList<ItemStack>());
		} else {
			stuff.put(i.toString(), new ArrayList<ItemStack>());
		}
		
		return (List<ItemStack>) stuff.get(i.toString());
	}
	
	public void set(Enum<?> i, Object infos){
		stuff.put(i.toString(), infos);
	}
}