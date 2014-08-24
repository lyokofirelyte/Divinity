package com.github.lyokofirelyte.Divinity.Manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import com.github.lyokofirelyte.Divinity.Divinity;
import com.github.lyokofirelyte.Divinity.DivinityUtils;
import com.github.lyokofirelyte.Divinity.Storage.DPI;
import com.github.lyokofirelyte.Divinity.Storage.DivStorageModule;
import com.github.lyokofirelyte.Divinity.Storage.DivinityPlayer;
import com.github.lyokofirelyte.Divinity.Storage.DivinityStorage;

public class DivinityManager {

	private Divinity api;
	
	public DivinityManager(Divinity div){
		api = div;
		refresh();
	}
	
	public Map<String, Map<String, DivinityStorage>> data = new HashMap<>();
	public List<Class<? extends Enum>> enums = new ArrayList<>();
	
	private List<String> dirs = Arrays.asList(dir, allianceDir, regionsDir, ringsDir, sysDir);
	
	final static public String sysDir = "./plugins/Divinity/system/";
	final static public String dir = "./plugins/Divinity/users/";
	final static public String allianceDir = "./plugins/Divinity/alliances/";
	final static public String regionsDir = "./plugins/Divinity/regions/";
	final static public String ringsDir = "./plugins/Divinity/rings/";
	
	public DivinityPlayer searchForPlayer(String s){

		for (DivinityStorage dp : data.get(dir).values()){
			if (dp.name().toLowerCase().startsWith(s.toLowerCase()) || dp.uuid().toString().equals(s)){
				return (DivinityPlayer) dp;
			}
		}
		
		try {
			if (Bukkit.getPlayer(UUID.fromString(s)) != null){
				return (DivinityPlayer) modifyObject(dir, s, true, true);
			}
		} catch (Exception e){}
		
		return null;
	}
	
	public Collection<DivinityStorage> getAllUsers(){
		return data.get(dir).values();
	}
	
	public Map<String, DivinityStorage> getMap(String directory){
		return data.containsKey(directory) ? data.get(directory) : new HashMap<String, DivinityStorage>();
	}
	
	public DivinityStorage getStorage(String directory, String name){
		return data.get(directory).containsKey(name) ? data.get(directory).get(name) : modifyObject(directory, name, true, true);
	}
	
	public YamlConfiguration lc(File file){
		return YamlConfiguration.loadConfiguration(file); 
	}
	
	private YamlConfiguration userTemplate() {
		try {
			return YamlConfiguration.loadConfiguration(api.getResource("newUser.yml"));
		} catch (Exception e){
			e.printStackTrace();
			return new YamlConfiguration();
		}
	}
	
	@SuppressWarnings("rawtypes")
	private DivinityStorage modifyObject(String directory, String name, boolean newFile, boolean load){
		
		File file = new File(directory + name + ".yml");
		
		if (!Arrays.asList(new File(directory).list()).contains(name + ".yml") && load){
			try { file.createNewFile(); } catch (Exception e){} newFile = true;
		}
		
		YamlConfiguration yaml = newFile && directory.equals(dir) && load ? userTemplate() : newFile && load ? new YamlConfiguration() : lc(file);
		DivinityStorage storage = directory.equals(dir) && load ? new DivinityStorage(UUID.fromString(name), api) : load ? new DivinityStorage(name, api) : data.get(directory).get(name);
		
		if (load){
			switch (directory){
				case dir: storage = newFile ? newUser(storage.new Player(storage.uuid(), api)) : storage.new Player(storage.uuid(), api); break;
				case allianceDir: storage = storage.new Alliance(name, api); break;
				case regionsDir: storage = storage.new Region(name, api); break;
				case ringsDir: storage = storage.new Ring(name, api); break;
				case sysDir: storage = storage.new System(name, api); break;
			}
		}
		
		try {

			for (Class<? extends Enum> enumClass : enums){
				if (enumClass.getMethod("s", null).getAnnotation(DivStorageModule.class) != null && Arrays.asList(enumClass.getMethod("s", null).getAnnotation(DivStorageModule.class).types()).contains(directory)){
					for (Enum<?> enumValue : enumClass.getEnumConstants()){
						if (load){
							if (yaml.contains(enumValue.toString())){
								storage.set(enumValue, yaml.get(enumValue.toString()));
							}
						} else {
							if (storage.getRawInfo(enumValue) != null || (storage instanceof DivinityStorage.Region && ((DivinityStorage.Region)storage).getFlags().containsKey(enumValue))){
								if (storage.getRawInfo(enumValue) instanceof Location){
									Location l = (Location) storage.getRawInfo(enumValue);
									yaml.set(enumValue.toString(), l.getWorld().getName() + " " + l.getBlockX() + " " + l.getBlockY() + " " + l.getBlockZ() + " " + l.getPitch() + " " + l.getYaw());
								} else {
									yaml.set(enumValue.toString(), storage.getRawInfo(enumValue));
								}
							}
						}
					}
				}
			}
			
		} catch (Exception e){}
		
		if (load){
			data.get(directory).put(name, storage);
		} else {
			try { yaml.save(file); } catch (Exception e){}
		}

		return storage;
	}
	
	private DivinityStorage.Player newUser(DivinityStorage.Player player){

		DivinityUtils.bc("Welcome &6" + player.name() + " &bto Worlds Apart!");
		Bukkit.getPlayer(player.uuid()).setDisplayName("&7" + player.name());
		player.set(DPI.DISPLAY_NAME, "&7" + player.name());
		
		return player;
	}
	
	public void load() throws Exception {

		for (String d : dirs){
			
			if (!new File(d).exists()){
				new File(d).mkdirs();
			}
			
			for (String file : new File(d).list()){
				if (!file.contains("~")){
					modifyObject(d, file.replace(".yml", ""), false, true);
				}
			}
		}
		
		api.getSystem().setMarkkit(lc(new File(DivinityManager.sysDir + "markkit.yml")));
		
		DivinityUtils.bc("Divinity has reloaded.");
		DivinityUtils.bc("&7&o" + getAllUsers().size() + " users, " + getMap(allianceDir).size() + " alliances, and " + getMap(regionsDir).size() + " regions.");
	}
	
	public void save() throws IOException {
		
		for (String objectType : data.keySet()){
			for (String objectName : data.get(objectType).keySet()){
				modifyObject(objectType, objectName, false, false);
			}
		}
		
		DivinityUtils.bc("Divinity has saved.");
		DivinityUtils.bc("&7&o" + getAllUsers().size() + " users, " + getMap(allianceDir).size() + " alliances, and " + getMap(regionsDir).size() + " regions.");
	}
	
	private void refresh(){
		for (String d : dirs){
			data.put(d, new HashMap<String, DivinityStorage>());
		}
	}
}