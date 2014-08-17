package com.github.lyokofirelyte.Divinity.Manager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import com.github.lyokofirelyte.Divinity.Divinity;
import com.github.lyokofirelyte.Divinity.DivinityUtils;
import com.github.lyokofirelyte.Divinity.Storage.DAI;
import com.github.lyokofirelyte.Divinity.Storage.DPI;
import com.github.lyokofirelyte.Divinity.Storage.DRF;
import com.github.lyokofirelyte.Divinity.Storage.DivinityAlliance;
import com.github.lyokofirelyte.Divinity.Storage.DivinityPlayer;
import com.github.lyokofirelyte.Divinity.Storage.DivinityRegion;
import com.github.lyokofirelyte.Divinity.Storage.DivinityRing;
import com.github.lyokofirelyte.Divinity.Storage.DivinitySkillPlayer;
import com.github.lyokofirelyte.Divinity.Storage.DivinityStorage;
import com.github.lyokofirelyte.Divinity.Storage.ElySkill;
import com.github.lyokofirelyte.Divinity.Storage.DRI;

public class DivinityManager {

	private Divinity api;
	
	public DivinityManager(Divinity div){
		api = div;
		enums.put("player", new ArrayList<Enum<?>>(Arrays.asList(DPI.values())));
		enums.put("alliance", new ArrayList<Enum<?>>(Arrays.asList(DAI.values())));
		enums.put("region", new ArrayList<Enum<?>>(Arrays.asList(DRI.values())));
		enums.put("player2", new ArrayList<Enum<?>>(Arrays.asList(ElySkill.values())));
	}
	
	private Map<UUID, DivinityPlayer> players = new HashMap<UUID, DivinityPlayer>();
	private Map<String, DivinityAlliance> alliances = new HashMap<String, DivinityAlliance>();
	private Map<String, DivinityRegion> regions = new HashMap<String, DivinityRegion>();
	private Map<String, DivinityRing> rings = new HashMap<String, DivinityRing>();
	
	private Map<String, List<Enum<?>>> enums = new HashMap<String, List<Enum<?>>>();
	
	private DivinityPlayer system;
	
	private String sysDir = "./plugins/Divinity/";
	private String dir = "./plugins/Divinity/users/";
	private String allianceDir = "./plugins/Divinity/alliances/";
	private String regionsDir = "./plugins/Divinity/regions/";
	private String ringsDir = "./plugins/Divinity/rings/";
	
	public int userSize(){
		return players.keySet().size();
	}
	
	public Map<Boolean, DivinityPlayer> searchForPlayer(String s){
		
		Map<Boolean, DivinityPlayer> results = new HashMap<Boolean, DivinityPlayer>();
		
		for (DivinityPlayer dp : players.values()){
			if (dp.name().toLowerCase().startsWith(s.toLowerCase())){
				results.put(true, dp);
				break;
			}
		}
		
		if (!results.containsKey(true)){
			results.put(false, getAllUsers().get(0));
		}
		
		return results;
	}
	
	public DivinityPlayer getSystem(){
		return system;
	}
	
	public List<DivinityPlayer> getAllUsers(){
		
		List<DivinityPlayer> ps = new ArrayList<DivinityPlayer>();
		
		for (DivinityPlayer dp : players.values()){
			ps.add(dp);
		}
		
		return ps;
	}
	
	public Map<UUID, DivinityPlayer> getMap(){
		return players;
	}
	
	public Map<String, DivinityAlliance> getAllianceMap(){
		return alliances;
	}
	
	public Map<String, DivinityRegion> getRegionMap(){
		return regions;
	}
	
	public Map<String, DivinityRing> getRingMap(){
		return rings;
	}
	
	public DivinityRegion getRegion(String name){
		return regions.containsKey(name.toLowerCase()) ? regions.get(name.toLowerCase()) : makeObject("region", name.toLowerCase());
	}
	
	public DivinityAlliance getAlliance(String name){
		return alliances.containsKey(name.toLowerCase()) ? alliances.get(name.toLowerCase()) : makeObject("alliance", name.toLowerCase());
	}
	
	public DivinityPlayer getDivinityPlayer(UUID uuid){
		return players.containsKey(uuid) ? players.get(uuid) : makeObject("player", uuid.toString());
	}
	
	public DivinityPlayer getDivinityPlayer(Player player){
		return getDivinityPlayer(player.getUniqueId());
	}
	
	public DivinitySkillPlayer getSkillPlayer(Player player){
		return (DivinitySkillPlayer) getDivinityPlayer(player.getUniqueId());
	}
	
	public DivinityRing getDivinityRing(String name){
		return rings.containsKey(name) ? rings.get(name) : makeObject("ring", name.toLowerCase());
	}
	
	private DivinityStorage makeObject(String objectType, String name){
		
		String currentDir = objectType.equals("player") ? dir : objectType.equals("alliance") ? allianceDir : objectType.equals("region") ? regionsDir : objectType.equals("ring") ? ringsDir : sysDir;
		File folder = new File(currentDir);
		File file = new File(currentDir + name + ".yml");
		boolean newFile = false;
		
		if (!Arrays.asList(folder.list()).contains(name + ".yml")){
			try {
				file.createNewFile();
				newFile = true;
			} catch (IOException e) {}
		}
		
		try {
			return unpackObject(file, name, objectType, newFile);
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	private DivinityStorage unpackObject(File file, String name, String objectType, boolean newFile) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		DivinityStorage storage = objectType.equals("player") ? new DivinityStorage(UUID.fromString(name), api) : new DivinityStorage(name, api);
		YamlConfiguration yaml = newFile ? new YamlConfiguration() : YamlConfiguration.loadConfiguration(file);
		
		for (String enumName : enums.keySet()){
			for (Enum<?> enumType : enums.get(enumName)){
				if (yaml.contains(enumType.toString()) && (enumName.startsWith(objectType) || objectType.equals("system"))){
					storage.set(enumType, yaml.get(enumType.toString()));
					System.out.println(name + " " + enumName + " " + enumType.toString() + " " + yaml.get(enumType.toString()));
				}
			}
		}
		
		switch (objectType){
		
			case "system":
				storage.set(DPI.DISPLAY_NAME, "&6Console");
				storage.set(DPI.PM_COLOR, "&f");
				system = storage;
			break;
			
			case "player": storage = newFile ? newUser(UUID.fromString(name), storage) : storage; players.put(UUID.fromString(name), storage); break;
			case "alliance": alliances.put(name, storage); break;
			case "region": regions.put(name, storage); break;
			
			case "ring":
				storage.setCenter((yaml.getString("CENTER")).split(" "));
				storage.setDest(yaml.getString("DEST"));
				storage.setInOperation(false);
				storage.setRingMaterial(yaml.getInt("MAT_ID"), (byte) yaml.getInt("BYTE_ID"));
				rings.put(name, storage);
			break;
		}
		
		return storage;
	}
	
	private DivinityStorage newUser(UUID uuid, DivinityStorage player){

		DivinityUtils.bc("Welcome &6" + player.name() + " &bto Worlds Apart!");
		Bukkit.getPlayer(player.uuid()).setDisplayName("&7" + player.name());
		Bukkit.getPlayer(player.uuid()).teleport(new Location(Bukkit.getWorld("world"), -53D, 85D, -20D));
		
		player.set(DPI.JOIN_MESSAGE, "Joined!");
		player.set(DPI.QUIT_MESSAGE, "Left!");
		player.set(DPI.ALLIANCE_COLOR, "&3");
		player.set(DPI.PM_COLOR, "&d");
		player.set(DPI.GLOBAL_COLOR, "&f");
		player.set(DPI.BALANCE, 0);
		player.set(DPI.RANK_NAME, "Guest");
		player.set(DPI.RANK_COLOR, "&8");
		player.set(DPI.RANK_DESC, "&6Guest rank. No perms at all, except chat!");
		player.set(DPI.STAFF_DESC, "&6A guest that should register soon!");
		player.set(DPI.STAFF_COLOR, "&7");
		player.set(DPI.DISPLAY_NAME, "&7" + player.name());
		player.set(DPI.ALLIANCE_COLOR_1, "&7");
		player.set(DPI.ALLIANCE_COLOR_2, "&7");
		player.set(DPI.AFK_TIME, 0);
		player.set(DPI.AFK_TIME_INIT, 0);
		player.set(DPI.DEATHLOCS_TOGGLE, "true");
		player.set(DPI.ALLIANCE_TOGGLE, "true");
		player.set(DPI.PARTICLES_TOGGLE, "true");
		player.set(DPI.FIREWORKS_TOGGLE, "true");
		player.set(DPI.SCOREBOARD_TOGGLE, "true");
		player.set(DPI.PLAYER_DESC, "&7&oGeneric player biography / description.\n&7&oUse /bio <message> to change this.");
		player.set(DPI.PERMS, new ArrayList<String>());
		player.set(DPI.CHAT_FILTER_TOGGLE, true);
		
		return player;
	}

	private void saveRes(File file, DivinityStorage dp) throws IOException {
		
		YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
		
		for (String enumName : enums.keySet()){
			if (enums.get(enumName).get(0) instanceof DRF == false){
				for (Enum<?> enumType : enums.get(enumName)){
					if (dp.getRawInfo(enumType) != null){
						if (dp.getRawInfo(enumType) instanceof Location){
							Location l = (Location) dp.getRawInfo(enumType);
							yaml.set(enumType.toString(), l.getWorld().getName() + " " + l.getBlockX() + " " + l.getBlockY() + " " + l.getBlockZ() + " " + l.getPitch() + " " + l.getYaw());
						} else {
							yaml.set(enumType.toString(), dp.getRawInfo(enumType));
						}
					}
				}
			}
		}
		
		for (DRF info : DRF.values()){
			if (dp.getFlags().containsKey(info)){
				yaml.set(info.s(), dp.getFlag(info));
			}
		}
		
		yaml.save(file);
	}
	
	private void saveRing(File file, DivinityRing ring) throws IOException {
		
		YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
		
		String center = ring.getCenter()[0];
		
		for (int i = 1; i < ring.getCenter().length; i++){
			center = center + " " + ring.getCenter()[i];
		}
		
		yaml.set("CENTER", center);
		yaml.set("MAT_ID", ring.getMatId());
		yaml.set("BYTE_ID", ring.getMatByte());
		yaml.set("DEST", ring.getDest());
		
		yaml.save(file);
	}
	
	public void load() throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		players = new HashMap<UUID, DivinityPlayer>();
		alliances = new HashMap<String, DivinityAlliance>();
		regions = new HashMap<String, DivinityRegion>();
		rings = new HashMap<String, DivinityRing>();
		
		List<String> dirs = Arrays.asList(dir, allianceDir, regionsDir, ringsDir, sysDir);
		String objectType = "";

		for (String d : dirs){
			
			if (!new File(d).exists()){
				new File(d).mkdirs();
			}
			
			objectType = d.equals(dir) ? "player" : d.equals(allianceDir) ? "alliance" : d.equals(regionsDir) ? "region" : d.equals(sysDir) ? "system" : "ring";
			
			for (String file : new File(d).list()){
				if (!file.contains("~") && !d.equals(sysDir) || (d.equals(sysDir) && file.equals("system.yml"))){
					makeObject(objectType, file.replace(".yml", ""));
				}
			}
			
			if (system == null){
				makeObject("system", "system");
			}
		}
		
		DivinityUtils.bc("Divinity - &6" + userSize() + " &busers loaded.");
	}
	
	public void save() throws IOException {
		
		for (DivinityPlayer dp : players.values()){
			File file = new File(dir + dp.uuid().toString() + ".yml");
			saveRes(file, (DivinityStorage) dp);
		}
		
		for (DivinityAlliance dp : alliances.values()){
			File file = new File(allianceDir + dp.name() + ".yml");
			saveRes(file, (DivinityStorage) dp);
		}
		
		for (DivinityRegion region : regions.values()){
			File file = new File(regionsDir + region.name() + ".yml");
			saveRes(file, (DivinityStorage) region);
		}
		
		for (DivinityRing ring : rings.values()){
			File file = new File(ringsDir + ring.name() + ".yml");
			saveRing(file, ring);
		}
		
		saveRes(new File(sysDir + "system.yml"), (DivinityStorage)system);
		
		DivinityUtils.bc("Divinity - &6" + userSize() + " &busers saved.");
	}
}