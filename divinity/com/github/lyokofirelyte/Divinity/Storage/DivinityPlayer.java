package com.github.lyokofirelyte.Divinity.Storage;

import java.util.UUID;

import org.bukkit.Bukkit;

import com.dsh105.holoapi.HoloAPI;
import com.dsh105.holoapi.api.Hologram;
import com.dsh105.holoapi.api.HologramFactory;
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
	
	public void tempHologram(long delay, String[] msg){
		showHologram(msg);
		api.schedule(this, "remHologram", delay, "tempHolo" + name());
	}
	
	public void showHologram(String[] msg){
		
		if (!getStr(DPI.HOLO_ID).equals("none")){
			remHologram();
		}
		
		for (int i = 0; i < msg.length; i++){
			msg[i] = DivinityUtils.AS(new String(msg[i]));
		}
		
	   	Hologram hologram = new HologramFactory(api)
	   		.withLocation(DivinityUtils.getCardinalMove(Bukkit.getPlayer(uuid())))
		 	.withText(msg)
		  	.build();
	   	
	   	hologram.clearAllPlayerViews();
		hologram.show(Bukkit.getPlayer(uuid()));
		set(DPI.HOLO_ID, hologram.getSaveId());
	}
	
	public void remHologram(){
		try {
			HoloAPI.getManager().getHologram(getStr(DPI.HOLO_ID)).clearAllPlayerViews();
			HoloAPI.getManager().clearFromFile(HoloAPI.getManager().getHologram(getStr(DPI.HOLO_ID)));
		} catch (Exception e){}
		set(DPI.HOLO_ID, "none");
	}
	
	public void updateHologram(){
		if (!getStr(DPI.HOLO_ID).equals("none")){
			HoloAPI.getManager().getHologram(getStr(DPI.HOLO_ID)).move(DivinityUtils.getCardinalMove(Bukkit.getPlayer(uuid())));
		}
	}
	
	public Hologram getHologram(){
		if (!getStr(DPI.HOLO_ID).equals("none")){
			return HoloAPI.getManager().getHologram(getStr(DPI.HOLO_ID));
		}
		return null;
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