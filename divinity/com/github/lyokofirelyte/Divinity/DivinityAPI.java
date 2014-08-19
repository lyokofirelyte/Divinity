package com.github.lyokofirelyte.Divinity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

/**
 * Provides access to JavaPlugin as well as the register methods.
 */
public abstract class DivinityAPI extends JavaPlugin {
	
	public List<DivinityModule> modules = new ArrayList<DivinityModule>();
	
	/**
	 * Registers the plugin with the API and calls the onRegister method.
	 * @param module
	 */
	public void register(DivinityModule module){
		modules.add(module);
		module.onRegister();
	}
	
	/**
	 * Un-registers the plugin from the API and calls the onUnRegister method.
	 * @param module
	 */
	public void unRegister(DivinityModule module){
		modules.remove(module);
		module.onUnRegister();
	}
}