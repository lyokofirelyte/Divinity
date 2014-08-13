package com.github.lyokofirelyte.Divinity.Storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.Divinity.Divinity;

public class DivinityRegion {

	private Divinity main;
	private String name;
	
	public DivinityRegion(Divinity i, String name){
		main = i;
		this.name = name;
	}
	
	private String world;
	private int length;
	private int width;
	private int height;
	private int area;
	private int priority;
	private String maxBlock;
	private String minBlock;
	private List<String> permissions = new ArrayList<String>();
	private Map<DRI, Boolean> flags = new HashMap<DRI, Boolean>();
	private boolean disabled = false;
	
	public String name(){
		return name;
	}
	
	public int getPriority(){
		return priority;
	}
	
	public int getLength(){
		return length;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getArea(){
		return area;
	}
	
	public String getMaxBlock(){
		return maxBlock;
	}
	
	public String getMinBlock(){
		return minBlock;
	}
	
	public boolean isDisabled(){
		return disabled;
	}
	
	public boolean getFlag(DRI flag){
		return flags.containsKey(flag) ? flags.get(flag) : false;
	}
	
	public boolean canBuild(Player p){
		
		DivinityPlayer dp = main.getDivPlayer(p);
		
		for (String perm : permissions){
			if (dp.getListDPI(DPI.PERMS).contains(perm)){
				return true;
			}
		}
		
		return disabled ? true : false;
	}
	
	public World world(){
		return Bukkit.getWorld(world);
	}
	
	public String getWorld(){
		return world;
	}
	
	public List<String> getPerms(){
		return permissions;
	}
	
	public Map<DRI, Boolean> getFlags(){
		return flags;
	}
	
	public void setWorld(String w){
		world = w;
	}
	
	public void addPerm(String perm){
		if (!permissions.contains(perm)){
			permissions.add(perm);
		}
	}
	
	public void remPerm(String perm){
		if (permissions.contains(perm)){
			permissions.remove(perm);
		}
	}
	
	public void setPerms(List<String> p){
		permissions = p;
	}
	
	public void addFlag(DRI flag, boolean value){
		flags.put(flag, value);
	}
	
	public void remFlag(DRI flag){
		if (flags.containsKey(flag)){
			flags.remove(flag);
		}
	}
	
	public void setPriority(int i){
		priority = i;
	}
	
	public void setLength(int i){
		length = i;
	}
	
	public void setHeight(int i){
		height = i;
	}
	
	public void setWidth(int i){
		width = i;
	}
	
	public void setArea(int i){
		area = i;
	}
	
	public void setMaxBlock(String block){
		maxBlock = block;
	}
	
	public void setMinBlock(String block){
		minBlock = block;
	}
	
	public void setDisabled(boolean disable){
		disabled = disable;
	}
}