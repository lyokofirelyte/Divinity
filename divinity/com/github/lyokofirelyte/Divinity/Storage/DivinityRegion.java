package com.github.lyokofirelyte.Divinity.Storage;

import java.util.List;
import java.util.Map;

import org.bukkit.World;
import org.bukkit.entity.Player;

public interface DivinityRegion extends DivInfo {

	public String name();
	
	public int getPriority();
	
	public int getLength();
	
	public int getWidth();
	
	public int getHeight();
	
	public int getArea();
	
	public String getMaxBlock();
	
	public String getMinBlock();
	
	public boolean isDisabled();
	
	public boolean getFlag(DRF flag);
	
	public boolean canBuild(Player p);
	
	public World world();
	
	public String getWorld();
	
	public List<String> getPerms();
	
	public Map<DRF, Boolean> getFlags();
}