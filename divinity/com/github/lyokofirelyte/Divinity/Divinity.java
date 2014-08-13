package com.github.lyokofirelyte.Divinity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.github.lyokofirelyte.Divinity.Commands.DivinityRegistry;
import com.github.lyokofirelyte.Divinity.JSON.FW;
import com.github.lyokofirelyte.Divinity.Manager.DivinityManager;
import com.github.lyokofirelyte.Divinity.Storage.DPI;
import com.github.lyokofirelyte.Divinity.Storage.DivinityAlliance;
import com.github.lyokofirelyte.Divinity.Storage.DivinityPlayer;
import com.github.lyokofirelyte.Divinity.Storage.DivinityRegion;
import com.github.lyokofirelyte.Divinity.Storage.DivinityRing;

public class Divinity extends DivinityAPI {
	
	public DivinityManager divManager;
	public DivinityUtils divUtils;
	public DivinityRegistry divReg;
	public FW fw;
    public static Map <List<String>, Object> commandMap = new HashMap<>();
	
	@Override
	public void onEnable(){
		
		divManager = new DivinityManager(this);
		divUtils = new DivinityUtils(this);
		divReg = new DivinityRegistry(this);
		fw = new FW(this);
		
		try {
			divManager.load();
		} catch (IOException e) {
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
	
	public DivinityPlayer getSystem(){
		return divManager.getSystem();
	}
	
	public DivinityPlayer getDivPlayer(Player player){
		return divManager.getDivinityPlayer(player);
	}

	public DivinityPlayer getDivPlayer(UUID player){
		return divManager.getDivinityPlayer(player);
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
	
	public void update(DivinityPlayer dp){
		if (!dp.name().equals("system")){
			divManager.getMap().put(dp.uuid(), dp);
		}
	}
	
	public void aUpdate(DivinityAlliance dp){
		if (!dp.name().equals("system")){
			divManager.getAllianceMap().put(dp.name(), dp);
		}
	}
	
	public void event(Event e){
		Bukkit.getPluginManager().callEvent(e);
	}
	
	public boolean perms(Player p, String perm){
		if (getDivPlayer(p).getListDPI(DPI.PERMS).contains(perm)){
			return true;
		}
		return false;
	}
}