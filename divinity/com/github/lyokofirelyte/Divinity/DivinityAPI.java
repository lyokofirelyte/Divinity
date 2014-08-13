package com.github.lyokofirelyte.Divinity;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class DivinityAPI extends JavaPlugin {
	public abstract void onEnable();
	public abstract void onDisable();
	public abstract Divinity getApi();
}