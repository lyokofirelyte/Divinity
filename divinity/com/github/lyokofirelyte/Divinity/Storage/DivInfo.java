package com.github.lyokofirelyte.Divinity.Storage;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public interface DivInfo {

	public String name();
	
	public Object getRawInfo(Object i);
	
	public String getStr(Object i);
	
	public int getInt(Object i);
	
	public long getLong(Object i);
	
	public double getDouble(Object i);
	
	public boolean getBool(Object i);
	
	public List<String> getList(Object i);
	
	public Location getLoc(Object i);
	
	public ItemStack[] getStack(Object i);
	
	public void set(Object i, Object infos);
}