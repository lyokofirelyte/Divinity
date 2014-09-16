package com.github.lyokofirelyte.Divinity.Storage;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import com.github.lyokofirelyte.Divinity.Divinity;
import com.github.lyokofirelyte.Divinity.Manager.ParticleEffect;

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
	
	public void loadEffects(){
		if (contains("Effects")){
			for (String effectName : getConfigurationSection("Effects").getKeys(false)){
				String path = "Effects." + effectName + ".";
				String[] loc = getString(path + "Location").split(" ");
				cancelEffect(effectName);
				addEffect(effectName, ParticleEffect.fromName(getString(path + "Effect")), getInt(path + "OSX"), getInt(path + "OSY"), 
					getInt(path + "OSZ"), getInt(path + "Speed"), getInt(path + "Amount"), 
					new Location(Bukkit.getWorld(loc[0]), Integer.parseInt(loc[1]), Integer.parseInt(loc[2]), Integer.parseInt(loc[3])), 
					getInt(path + "Range"), getLong(path + "Cycle"));
			}
		}
	}
	
	public void addEffect(String name, ParticleEffect eff, int offsetX, int offsetY, int offsetZ, int speed, int amount, Location center, int range, long cycleDelay){
		if (!contains("Effects." + name)){
			String path = "Effects." + name + ".";
			set(path + "OSX", offsetX);
			set(path + "OSY", offsetY);
			set(path + "OSZ", offsetZ);
			set(path + "Speed", speed);
			set(path + "Amount", amount);
			set(path + "Location", center.getWorld().getName() + " " + center.getBlockX() + " " + center.getBlockY() + " " + center.getBlockZ());
			set(path + "Range", range);
			set(path + "Cycle", cycleDelay);
			set(path + "Effect", eff.toString());
			api.repeat(this, "playEffect", 0L, cycleDelay, "effects" + name, eff, offsetX, offsetY, offsetZ, speed, amount, center, range);
		}
	}
	
	public void remEffect(String name){
		cancelEffect(name);
		set("Effects." + name, null);
	}
	
	public void playEffect(ParticleEffect eff, int offsetX, int offsetY, int offsetZ, int speed, int amount, Location center, int range){
		eff.display(offsetX, offsetY, offsetZ, speed, amount, center, range);
	}
	
	public void cancelEffect(String name){
		api.cancelTask("effects" + name);
	}
}