package com.github.lyokofirelyte.Divinity.Manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import com.github.lyokofirelyte.Divinity.Divinity;
import com.github.lyokofirelyte.Divinity.DivinityUtils;
import com.github.lyokofirelyte.Divinity.Storage.DPI;
import com.github.lyokofirelyte.Divinity.Storage.DivinityAlliance;
import com.github.lyokofirelyte.Divinity.Storage.DivinityPlayer;
import com.github.lyokofirelyte.Divinity.Storage.DivinityRegion;
import com.github.lyokofirelyte.Divinity.Storage.DivinityRing;
import com.github.lyokofirelyte.Divinity.Storage.DivinityStorage;
import com.github.lyokofirelyte.Divinity.Storage.DivinityGame;
import com.github.lyokofirelyte.Divinity.Storage.DivinitySystem;

public class DivinityManager {

	private Divinity api;
	
	public DivinityManager(Divinity div){
		api = div;
		refresh();
	}
	
	public Map<String, Map<String, DivinityStorage>> data = new HashMap<>();
	private List<String> dirs = Arrays.asList(dir, allianceDir, regionsDir, ringsDir, sysDir, gamesDir, backupDir);
	
	final static public String sysDir = "./plugins/Divinity/system/";
	final static public String dir = "./plugins/Divinity/users/";
	final static public String allianceDir = "./plugins/Divinity/alliances/";
	final static public String regionsDir = "./plugins/Divinity/regions/";
	final static public String ringsDir = "./plugins/Divinity/rings/";
	final static public String gamesDir = "./plugins/Divinity/games/";
	final static public String backupDir = "./plugins/Divinity/backup/";
	
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
		return data.containsKey(directory) && data.get(directory).containsKey(name) ? data.get(directory).get(name) : modifyObject(directory, name, true, true);
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

	public DivinityStorage modifyObject(String directory, String name, boolean newFile, boolean load){
		
		if (directory.equals(dir)){
			try {
				UUID u = UUID.fromString(name);
			} catch (Exception e){
				if (new File(directory + name + ".yml").exists()){
					new File(directory + name + ".yml").delete();
				}
				return null;
			}
		}
		
		if (!new File(directory).exists()){
			new File(directory).mkdirs();
		}
		
		File file = new File(directory + name + ".yml");
		
		if (!Arrays.asList(new File(directory).list()).contains(name + ".yml") && load){
			try { file.createNewFile(); } catch (Exception e){} newFile = true;
		}
		
		YamlConfiguration yaml = newFile && directory.equals(dir) && load ? userTemplate() : newFile && load ? new YamlConfiguration() : lc(file);
		DivinityStorage storage = directory.equals(dir) && load ? new DivinityStorage(UUID.fromString(name), api) : load ? new DivinityStorage(name, api) : data.get(directory).get(name);
		
		if (load){
			switch (directory){
				case dir: storage = newFile ? newUser(new DivinityPlayer(storage.uuid(), api)) : new DivinityPlayer(storage.uuid(), api); break;
				case allianceDir: storage = new DivinityAlliance(name, api); break;
				case regionsDir: storage = new DivinityRegion(name, api); break;
				case ringsDir: storage = new DivinityRing(name, api); break;
				case sysDir: storage = new DivinitySystem(name, api); break;
				case gamesDir: storage = new DivinityGame("system", name, api);
				default:
					if (directory.startsWith(gamesDir)){
						storage = new DivinityGame(directory.split("\\/")[4], name, api);
					}
				break;
			}
		}

		if (load){
			for (String sec : yaml.getKeys(true)){
				storage.set(sec, yaml.get(sec));
			}
		}
		
		if (load){
			if (!data.containsKey(directory)){
				data.put(directory, new HashMap<String, DivinityStorage>());
			}
			data.get(directory).put(name, storage);
		} else {
			try { storage.save(file); } catch (Exception e){}
		}

		return storage;
	}
	
	private DivinityPlayer newUser(DivinityPlayer player){

		DivinityUtils.bc("Welcome &6" + player.name() + " &bto Worlds Apart!");
		Bukkit.getPlayer(player.uuid()).setDisplayName("&7" + player.name());
		player.set(DPI.DISPLAY_NAME, "&7" + player.name());
		
		return player;
	}
	
	public void load(boolean full) throws Exception {
		
		if (full){

			for (String d : dirs){
				
				if (!new File(d).exists()){
					new File(d).mkdirs();
				}
				
				for (String file : new File(d).list()){
					if (!file.contains("~") && file.contains(".yml") && !new File(file).isDirectory()){
						modifyObject(d, file.replace(".yml", ""), false, true);
					}
				}
			}
			
			api.getSystem().setMarkkit(lc(new File(DivinityManager.sysDir + "markkit.yml")));
			
		} else {
			
			for (String gameFolder : new File(gamesDir).list()){
				if (new File(gamesDir + gameFolder).isDirectory()){
					for (String gameFile : new File(gamesDir + gameFolder + "/").list()){
						if (!new File(gameFile).isDirectory() && gameFile.contains("yml")){
							modifyObject(gamesDir + gameFolder + "/", gameFile.replace(".yml", ""), false, true);
						}
					}
				} else if (gameFolder.contains(".yml")){
					modifyObject(gamesDir, gameFolder.replace(".yml", ""), false, true);
				}
			}
		}
	}
	
	public void save() throws IOException {
		
		for (String objectType : data.keySet()){
			for (String objectName : data.get(objectType).keySet()){
				modifyObject(objectType, objectName, false, false);
			}
		}
	}
	
	public void backup(){
		
		String backup = backupDir + api.divUtils.getMonthAndDay() + "@" + ((int) new File(backupDir).list().length+1) + "/";
		List<String> paths = new ArrayList<String>();
		Set<String> datas = new HashSet<String>(data.keySet());
		
		for (String dir : datas){
			if (!dir.contains("logger") && !dir.contains("backup")){
				String path = backup + dir.substring(dir.indexOf(dir.split("\\/")[3]));
				File backupDir = new File(path);
				backupDir.mkdirs();
				data.put(path, data.get(dir));
				paths.add(path);
			}
		}
		
		try {
			save();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (String path : paths){
			data.remove(path);
		}
	}
	
	private void refresh(){
		for (String d : dirs){
			data.put(d, new HashMap<String, DivinityStorage>());
		}
	}
}