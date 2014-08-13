package com.github.lyokofirelyte.Divinity.Storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.lyokofirelyte.Divinity.Divinity;

public class DivinityAlliance {

	private String name;
	
	private Divinity api;
	
	public DivinityAlliance(String n, Divinity i){
		name = n;
		api = i;
	}
	
	public Map<DAI, Object> info = new HashMap<DAI, Object>();
	
	public String name(){
		return name;
	}
	
	public Object getRawInfo(DAI i){
		return info.get(i);
	}
	
	public String getDAI(DAI i){
		
		if (info.containsKey(i)){
			return info.get(i).toString();
		}
		
		return "none";
	}
	
	public Integer getIntDAI(DAI i){
		
		if (info.containsKey(i)){
			try {
				return Integer.parseInt(info.get(i).toString());
			} catch (Exception e){
				return 0;
			}
		}
		
		return 0;
	}
	
	public Long getLongDAI(DAI i){
		
		if (info.containsKey(i)){
			try {
				return Long.parseLong(info.get(i).toString());
			} catch (Exception e){
				return 0L;
			}
		}
		
		return 0L;
	}
	
	public boolean getBoolDAI(DAI i){
		
		if (info.containsKey(i)){
			try {
				return Boolean.valueOf(info.get(i).toString());
			} catch (Exception e){
				return false;
			}
		}
		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getListDAI(DAI i){
		
		if (info.containsKey(i)){
			try {
				if (info.get(i) instanceof List){
					return (List<String>) info.get(i);
				}
				info.put(i, new ArrayList<String>());
			} catch (Exception e){
				info.put(i, new ArrayList<String>());
			}
		} else {
			info.put(i, new ArrayList<String>());
		}
		
		return (List<String>) info.get(i);
	}
	
	public Location getLocDAI(DAI i){
		
		if (info.containsKey(i)){
			if (info.get(i) instanceof Location){
				return (Location) info.get(i);
			}
			try {
				String[] loc = info.get(i).toString().split(" ");
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
	
	public ItemStack[] getStackDAI(DAI i){
		
		ItemStack[] inv = null;
		
		if (info.containsKey(i)){
			if (info.get(i) instanceof ItemStack[]){
				inv = (ItemStack[]) info.get(i);
			} else {
				info.put(i, new ItemStack[]{});
				inv = new ItemStack[]{};
			}
		} else {
			info.put(i, new ItemStack[]{});
			inv = new ItemStack[]{};
		}
		
		return inv;
	}
	
	public void setDAI(DAI i, Object infos){
		info.put(i, infos);
		if (api.divManager.getAllUsers().size() > 0){
			api.aUpdate(this);
		}
	}
}