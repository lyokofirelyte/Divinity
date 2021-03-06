package com.github.lyokofirelyte.Divinity;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.minecraft.util.gnu.trove.map.hash.THashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.reflections.Reflections;

import com.github.lyokofirelyte.Divinity.Commands.DivinityRegistry;
import com.github.lyokofirelyte.Divinity.Manager.DivinityManager;
import com.github.lyokofirelyte.Divinity.Manager.JSONManager;
import com.github.lyokofirelyte.Divinity.Manager.PlayerLocation;
import com.github.lyokofirelyte.Divinity.Manager.TeamspeakManager;
import com.github.lyokofirelyte.Divinity.Manager.WebsiteManager;
import com.github.lyokofirelyte.Divinity.PublicUtils.FW;
import com.github.lyokofirelyte.Divinity.PublicUtils.TitleExtractor;
import com.github.lyokofirelyte.Divinity.Storage.DivinityAlliance;
import com.github.lyokofirelyte.Divinity.Storage.DivinityGame;
import com.github.lyokofirelyte.Divinity.Storage.DivinityPlayer;
import com.github.lyokofirelyte.Divinity.Storage.DivinityRegion;
import com.github.lyokofirelyte.Divinity.Storage.DivinityRing;
import com.github.lyokofirelyte.Divinity.Storage.DivinitySystem;

public class Divinity extends DivinityAPI {
	
	public DivinityManager divManager;
	public DivinityUtils divUtils;
	public DivinityRegistry divReg;
	
	public JSONManager json;
	public WebsiteManager web;
	public TeamspeakManager ts3;
	public PlayerLocation playerLocation;
	public TitleExtractor title;
	public Reflections ref;
	public FW fw;
	
    public Map <List<String>, Object> commandMap = new THashMap<>();
    public Map<String, Integer> activeTasks = new THashMap<String, Integer>();
    
	@Override
	public void onEnable(){
		
		divManager = new DivinityManager(this);
		divUtils = new DivinityUtils(this);
		divReg = new DivinityRegistry(this);
		title = new TitleExtractor(this);
		playerLocation = new PlayerLocation(this);
		ts3 = new TeamspeakManager(this);
		fw = new FW(this);
		json = new JSONManager(this);
		web = new WebsiteManager(this);
		
		for (DivinityModule module : modules){
			module.onRegister();
		}
		
		load();
		ts3.start();
	}
	
	@Override
	public void onDisable(){
		
		try {
			divManager.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (DivinityModule module : modules){
			module.onUnRegister();
		}
		
		Bukkit.getScheduler().cancelTasks(this);
	}
	
	public void load(){
		
		try {
			divManager.load(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<DivinityModule> getAllModules(){
		return modules;
	}
	
	public DivinityPlayer getDivPlayer(Player player){
		return (DivinityPlayer) divManager.searchForPlayer(player.getUniqueId().toString());
	}

	public DivinityPlayer getDivPlayer(UUID player){
		return (DivinityPlayer) divManager.searchForPlayer(player.toString());
	}

	public DivinityAlliance getDivAlliance(String alliance){
		return (DivinityAlliance) divManager.getStorage(DivinityManager.allianceDir, alliance.toLowerCase());
	}
	
	public DivinityRegion getDivRegion(String region){
		return (DivinityRegion) divManager.getStorage(DivinityManager.regionsDir, region.toLowerCase());
	}
	
	public DivinityRing getDivRing(String ring){
		return (DivinityRing) divManager.getStorage(DivinityManager.ringsDir, ring.toLowerCase());
	}
	
	public DivinityGame getDivGame(String gameType, String gameName){
		return (DivinityGame) divManager.getStorage(DivinityManager.gamesDir + gameType + "/", gameName.toLowerCase());
	}
	
	public DivinitySystem getSystem(){
		return (DivinitySystem) divManager.getStorage(DivinityManager.sysDir, "system");
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
	
	public void event(Event e){
		Bukkit.getPluginManager().callEvent(e);
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