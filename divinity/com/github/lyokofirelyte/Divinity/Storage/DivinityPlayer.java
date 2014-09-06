package com.github.lyokofirelyte.Divinity.Storage;

import java.util.UUID;

import org.bukkit.Bukkit;

import com.github.lyokofirelyte.Divinity.Divinity;
import com.github.lyokofirelyte.Divinity.DivinityUtils;

public class DivinityPlayer extends DivinityStorage {

	public DivinityPlayer(UUID n, Divinity i) {
		super(n, i);
	}
		
	public UUID uuid(){
		return uuid;
	}
		
	public boolean isOnline(){
		return Bukkit.getPlayer(uuid) != null;
	}
		
	public int getLevel(ElySkill skill){
		return Integer.parseInt(getStr(skill).split(" ")[0].replace("none", "0"));
	}
		
	public int getXP(ElySkill skill){
		return Integer.parseInt(getStr(skill).split(" ")[1].replace("none", "0"));
	}
		
	public int getNeededXP(ElySkill skill){
		return Integer.parseInt(getStr(skill).split(" ")[2].replace("none", "0").split("\\.")[0]);
	}
		
	public boolean hasLevel(ElySkill skill, int level){
		return Integer.parseInt(getStr(skill).split(" ")[0].replace("none", "0")) >= level;
	}
		
	public void s(String message){
		if (isOnline()){
			DivinityUtils.s(Bukkit.getPlayer(uuid), message);
		}
	}
		
	public void err(String message){
		if (isOnline()){
			DivinityUtils.s(Bukkit.getPlayer(uuid), "&c&o" + message);
		}
	}
}