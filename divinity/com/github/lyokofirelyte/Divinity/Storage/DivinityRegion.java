package com.github.lyokofirelyte.Divinity.Storage;

import java.util.List;
import java.util.Map;

import net.minecraft.util.gnu.trove.map.hash.THashMap;

import org.bukkit.Bukkit;
import org.bukkit.World;

import com.github.lyokofirelyte.Divinity.Divinity;

public class DivinityRegion extends DivinityStorage {
	
	public DivinityRegion(String n, Divinity i) {
		super(n, i);
	}

	public int getPriority(){
		return getInt(DRI.PRIORITY);
	}
	
	public int getLength(){
		return getInt(DRI.LENGTH);
	}
	
	public int getWidth(){
		return getInt(DRI.WIDTH);
	}
	
	public int getHeight(){
		return getInt(DRI.HEIGHT);
	}
	
	public int getArea(){
		return getInt(DRI.AREA);
	}
	
	public String getMaxBlock(){
		return getStr(DRI.MAX_BLOCK);
	}
	
	public String getMinBlock(){
		return getStr(DRI.MIN_BLOCK);
	}
	
	public boolean isDisabled(){
		return getBool(DRI.DISABLED);
	}
	
	public boolean getFlag(DRF flag){
		return getBool(flag);
	}
	
	public Map<DRF, Boolean> getFlags(){
		Map<DRF, Boolean> flagMap = new THashMap<>();
		for (DRF f : DRF.values()){
			if (contains(f.toString())){
				flagMap.put(f, getBool(f));
			}
		}
		return flagMap;
	}
	
	public boolean canBuild(org.bukkit.entity.Player p){
		
		DivinityPlayer dp = api.getDivPlayer(p);
		
		for (String perm : (List<String>)getList(DRI.PERMS)){
			if (dp.getList(DPI.PERMS).contains(perm)){
				return true;
			}
		}
		
		return getBool(DRI.DISABLED) ? true : false;
	}

	public World world() {
		return Bukkit.getWorld(getStr(DRI.WORLD));
	}

	public String getWorld() {
		return getStr(DRI.WORLD);
	}

	public List<String> getPerms() {
		return getList(DRI.PERMS);
	}	
}