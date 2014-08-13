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

public class DivinityPlayer {

	private UUID uuid;
	private String name;
	
	private Divinity api;
	
	public DivinityPlayer(UUID u, Divinity i){
		api = i;
		uuid = u;
		if (Bukkit.getPlayer(u) != null){
			name = Bukkit.getPlayer(u).getName();
		} else {
			name = Bukkit.getOfflinePlayer(u).getName();
		}
	}
	
	public DivinityPlayer(String system, Divinity i){
		api = i;
		name = "system";
	}
	
	public Map<DPI, Object> info = new HashMap<DPI, Object>();
	
	public String name(){
		return name;
	}
	
	public UUID uuid(){
		return uuid;
	}
	
	public Object getRawInfo(DPI i){
		return info.get(i);
	}
	
	public String getDPI(DPI i){
		
		if (info.containsKey(i)){
			return info.get(i).toString();
		}
		
		return "none";
	}
	
	public Integer getIntDPI(DPI i){
		
		if (info.containsKey(i)){
			try {
				return Integer.parseInt(info.get(i).toString());
			} catch (Exception e){
				return 0;
			}
		}
		
		return 0;
	}
	
	public Long getLongDPI(DPI i){
		
		if (info.containsKey(i)){
			try {
				return Long.parseLong(info.get(i).toString());
			} catch (Exception e){
				return 0L;
			}
		}
		
		return 0L;
	}
	
	public boolean getBoolDPI(DPI i){
		
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
	public List<String> getListDPI(DPI i){
		
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
	
	public Location getLocDPI(DPI i){
		
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
	
	public ItemStack[] getStackDPI(DPI i){
		
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
	
	public void setDPI(DPI i, Object infos){
		info.put(i, infos);
		if (api.divManager.getAllUsers().size() > 0){
			api.update(this);
		}
	}
}