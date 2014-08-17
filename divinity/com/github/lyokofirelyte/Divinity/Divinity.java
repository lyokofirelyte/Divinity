package com.github.lyokofirelyte.Divinity;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.github.lyokofirelyte.Divinity.Commands.DivinityRegistry;
import com.github.lyokofirelyte.Divinity.JSON.FW;
import com.github.lyokofirelyte.Divinity.Manager.DivinityManager;
import com.github.lyokofirelyte.Divinity.Storage.DivinityAlliance;
import com.github.lyokofirelyte.Divinity.Storage.DivinityPlayer;
import com.github.lyokofirelyte.Divinity.Storage.DivinityRegion;
import com.github.lyokofirelyte.Divinity.Storage.DivinityRing;
import com.github.lyokofirelyte.Divinity.Storage.DivinitySkillPlayer;

public class Divinity extends DivinityAPI {
	
	public DivinityManager divManager;
	public DivinityUtils divUtils;
	public DivinityRegistry divReg;
	public FW fw;
	
    public static Map <List<String>, Object> commandMap = new HashMap<>();
    public Map<String, Integer> activeTasks = new HashMap<String, Integer>();
	
	@Override
	public void onEnable(){
		
		divManager = new DivinityManager(this);
		divUtils = new DivinityUtils(this);
		divReg = new DivinityRegistry(this);
		fw = new FW(this);
		
		try {
			divManager.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onDisable(){
		
		try {
			divManager.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Bukkit.getScheduler().cancelTasks(this);
	}

	@Override
	public Divinity getApi() {
		return this;
	}
	
	public DivinitySkillPlayer matchSkillPlayer(String player){
		return (DivinitySkillPlayer) divManager.searchForPlayer(player).get(true);
	}

	public DivinityAlliance getDivAlliance(String alliance){
		return divManager.getAlliance(alliance);
	}
	
	public DivinityRegion getDivRegion(String region){
		return divManager.getRegion(region);
	}
	
	public DivinityRing getDivRing(String ring){
		return divManager.getDivinityRing(ring);
	}
	
	public DivinityPlayer getSystem(){
		return divManager.getSystem();
	}
	
	public DivinityPlayer getDivPlayer(Player player){
		return divManager.getDivinityPlayer(player);
	}

	public DivinityPlayer getDivPlayer(UUID player){
		return divManager.getDivinityPlayer(player);
	}
	
	public String AS(String s){
		return ChatColor.translateAlternateColorCodes('&', s);
	}

	public boolean perms(CommandSender cs, String perm){
		if (cs instanceof Player){
			return perms((Player)cs, perm);
		} else if (cs.isOp()){
			return true;
		}
		return false;
	}

	public void update(DivinityPlayer dp){
		if (!dp.name().equals("system")){
			divManager.getMap().put(dp.uuid(), dp);
		}
	}
	
	public void event(Event e){
		Bukkit.getPluginManager().callEvent(e);
	}
	
	public void aUpdate(DivinityAlliance dp){
		if (!dp.name().equals("system")){
			divManager.getAllianceMap().put(dp.name(), dp);
		}
	}
	
	public void cancelTask(String name){
		if (activeTasks.containsKey(name)){
			Bukkit.getScheduler().cancelTask(activeTasks.get(name));
		}
	}
	
	public void schedule(Object clazz, String method, long delay, String taskName, Object... args){
		
		for (Method m : clazz.getClass().getMethods()){
			if (m.getName().equals(method)){
				activeTasks.put(taskName, Bukkit.getScheduler().scheduleSyncDelayedTask(this, args != null ? new DivinityScheduler(clazz, m, args) : new DivinityScheduler(clazz, m), delay));
				return;
			}
		}
	}
	
	public void repeat(Object clazz, String method, long delay, long period, String taskName, Object... args){
		
		for (Method m : clazz.getClass().getMethods()){
			if (m.getName().equals(method)){
				activeTasks.put(taskName, Bukkit.getScheduler().scheduleSyncRepeatingTask(this, args != null ? new DivinityScheduler(clazz, m, args) : new DivinityScheduler(clazz, m), delay, period));
				return;
			}
		}
	}
}