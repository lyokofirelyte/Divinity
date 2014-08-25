package com.github.lyokofirelyte.Divinity.Storage;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public interface DivInfo {

	public String name();
	
	public Object getRawInfo(Enum<?> i);
	
	public String getStr(Enum<?> i);
	
	public int getInt(Enum<?> i);
	
	public long getLong(Enum<?> i);
	
	public double getDouble(Enum<?> i);
	
	public boolean getBool(Enum<?> i);
	
	public List<String> getList(Enum<?> i);
	
	public Location getLoc(Enum<?> i);
	
	public List<ItemStack> getStack(Enum<?> i);
	
	public void set(Enum<?> i, Object infos);
}