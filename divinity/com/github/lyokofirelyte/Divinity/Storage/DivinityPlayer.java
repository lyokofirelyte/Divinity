package com.github.lyokofirelyte.Divinity.Storage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.dsh105.holoapi.HoloAPI;
import com.dsh105.holoapi.api.Hologram;
import com.dsh105.holoapi.api.HologramFactory;
import com.github.lyokofirelyte.Divinity.Divinity;
import com.github.lyokofirelyte.Divinity.DivinityUtils;
import com.github.lyokofirelyte.Divinity.PublicUtils.ParticleEffect;

public class DivinityPlayer extends DivinityStorage {

	public DivinityPlayer(UUID n, Divinity i) {
		super(n, i);
	}
	
	private List<String> activeEffects = new ArrayList<String>();
		
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
		} catch (Exception e){}
		
		try {
			HoloAPI.getManager().clearFromFile(getStr(DPI.HOLO_ID));
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
	
	public void lockEffect(String name, ParticleEffect eff, int offsetX, int offsetY, int offsetZ, int speed, int amount, int range, long cycleDelay){
		if (!activeEffects.contains(name)){
			activeEffects.add(name);
			api.repeat(this, "playEffect", 0L, cycleDelay, "playerEffects" + name, eff, offsetX, offsetY, offsetZ, speed, amount, range, Bukkit.getPlayer(uuid()));
		}
	}
	
	public void playEffect(ParticleEffect eff, int offsetX, int offsetY, int offsetZ, int speed, int amount, int range, Player p){
		try {
			Location l = p.getLocation();
			eff.display(offsetX, offsetY, offsetZ, speed, amount, new Location(l.getWorld(), l.getX(),  l.getY()+1, l.getZ()), range);
		} catch (Exception e){}
	}
	
	public void remEffect(String name){
		if (activeEffects.contains(name)){
			activeEffects.remove(name);
			api.cancelTask("playerEffects" + name);
		}
	}
	
	public void clearEffects(){
		for (String effect : activeEffects){
			api.cancelTask("playerEffects" + effect);
		}
		activeEffects = new ArrayList<String>();
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