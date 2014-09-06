package com.github.lyokofirelyte.Divinity.Storage;

import org.bukkit.configuration.file.YamlConfiguration;

import com.github.lyokofirelyte.Divinity.Divinity;

public class DivinitySystem extends DivinityStorage {

	public DivinitySystem(String n, Divinity i) {
		super(n, i);
		set(DPI.DISPLAY_NAME, "&6Console");
		set(DPI.PM_COLOR, "&f");
	}
	
	private YamlConfiguration markkitYaml;
	
	public YamlConfiguration getMarkkit(){
		return markkitYaml;
	}
	
	public void setMarkkit(YamlConfiguration yaml){
		markkitYaml = yaml;
	}
}