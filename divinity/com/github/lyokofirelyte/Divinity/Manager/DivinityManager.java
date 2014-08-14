package com.github.lyokofirelyte.Divinity.Manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.github.lyokofirelyte.Divinity.Divinity;
import com.github.lyokofirelyte.Divinity.DivinityUtils;
import com.github.lyokofirelyte.Divinity.Storage.DAI;
import com.github.lyokofirelyte.Divinity.Storage.DPI;
import com.github.lyokofirelyte.Divinity.Storage.DRI;
import com.github.lyokofirelyte.Divinity.Storage.DivinityAlliance;
import com.github.lyokofirelyte.Divinity.Storage.DivinityPlayer;
import com.github.lyokofirelyte.Divinity.Storage.DivinityRegion;
import com.github.lyokofirelyte.Divinity.Storage.DivinityRing;

public class DivinityManager {

	private Divinity api;
	
	public DivinityManager(Divinity div){
		api = div;
	}
	
	private Map<UUID, DivinityPlayer> players = new HashMap<UUID, DivinityPlayer>();
	private Map<String, DivinityAlliance> alliances = new HashMap<String, DivinityAlliance>();
	private Map<String, DivinityRegion> regions = new HashMap<String, DivinityRegion>();
	private Map<String, DivinityRing> rings = new HashMap<String, DivinityRing>();
	
	private DivinityPlayer system;
	
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
		return regions.containsKey(name.toLowerCase()) ? regions.get(name.toLowerCase()) : (DivinityRegion)makeObject("region", name.toLowerCase());
	}
	
	public DivinityAlliance getAlliance(String name){
		return alliances.containsKey(name.toLowerCase()) ? alliances.get(name.toLowerCase()) : (DivinityAlliance)makeObject("alliance", name.toLowerCase());
	}
	
	public DivinityPlayer getDivinityPlayer(UUID uuid){
		return players.containsKey(uuid) ? players.get(uuid) : (DivinityPlayer)makeObject("player", uuid.toString());
	}
	
	public DivinityPlayer getDivinityPlayer(Player player){
		return getDivinityPlayer(player.getUniqueId());
	}
	
	public DivinityRing getDivinityRing(String name){
		return rings.containsKey(name) ? rings.get(name) : (DivinityRing)makeObject("ring", name.toLowerCase());
	}
	
	private Object makeObject(String objectType, String name){
		
		String currentDir = objectType.equals("player") ? dir : objectType.equals("alliance") ? allianceDir : objectType.equals("region") ? regionsDir : ringsDir;
		File folder = new File(currentDir);
		
		if (Arrays.asList(folder.list()).contains(name + ".yml")){
			return unpackObject(new File(currentDir + name + ".yml"), name, objectType);
		}
		
		File file = new File(currentDir + name + ".yml");
		
		try {
			file.createNewFile();
		} catch (IOException e) {}

		return newObject(file, name, objectType);
	}
	
	private Object newObject(File file, String name, String objectType){
		
		YamlConfiguration yaml = new YamlConfiguration();
		yaml.set("CreationDate", api.divUtils.getTimeFull());
		Object o = null;
		
		System.out.println(name + " " + objectType);
		
		switch (objectType){
		
			case "player": o = newUser(UUID.fromString(name), new DivinityPlayer(UUID.fromString(name), api)); break;
			case "alliance": o = newAlliance(name, new DivinityAlliance(name, api)); break;
			case "region": o = newRegion(name, new DivinityRegion(api, name)); break;
			case "ring": o = newRing(name, new DivinityRing(api, name)); break;
		}
		
		try {
			yaml.save(file);
		} catch (Exception e){}
		
		return o;
	}
	
	private Object unpackObject(File file, String name, String objectType){
		
		switch (objectType){
		
			case "player":
				
				DivinityPlayer player = new DivinityPlayer(UUID.fromString(name), api);
				YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
				
				for (DPI info : DPI.values()){
					if (yaml.isInt(info.s())){
						player.setDPI(info, yaml.getInt(info.s()));
					} else if (yaml.isString(info.s())){
						player.setDPI(info, yaml.getString(info.s()));
					} else if (yaml.isList(info.s())){
						player.setDPI(info, yaml.getStringList(info.s()));
					} else if (yaml.get(info.s()) instanceof ItemStack[]){
						player.setDPI(info, yaml.get(info.s()));
					}
				}
				
				players.put(UUID.fromString(name), player);
				
			return player;
			
			case "alliance":
				
				DivinityAlliance alliance = new DivinityAlliance(name, api);
				yaml = YamlConfiguration.loadConfiguration(file);
				
				for (DAI info : DAI.values()){
					if (yaml.isInt(info.s())){
						alliance.setDAI(info, yaml.getInt(info.s()));
					} else if (yaml.isString(info.s())){
						alliance.setDAI(info, yaml.getString(info.s()));
					} else if (yaml.isList(info.s())){
						alliance.setDAI(info, yaml.getStringList(info.s()));
					} else if (yaml.get(info.s()) instanceof ItemStack[]){
						alliance.setDAI(info, yaml.get(info.s()));
					}
				}
				
				alliances.put(name, alliance);
				
			return alliance;
			
			case "region":
				
				DivinityRegion region = new DivinityRegion(api, name);
				yaml = YamlConfiguration.loadConfiguration(file);
				
				region.setWorld(yaml.getString("WORLD"));
				region.setDisabled(yaml.getBoolean("DISABLED"));
				region.setArea(yaml.getInt("AREA"));
				region.setWidth(yaml.getInt("WIDTH"));
				region.setHeight(yaml.getInt("HEIGHT"));
				region.setLength(yaml.getInt("LENGTH"));
				region.setPriority(yaml.getInt("PRIORITY"));
				region.setMaxBlock(yaml.getString("MAX_BLOCK"));
				region.setMinBlock(yaml.getString("MIN_BLOCK"));
				region.setPerms(yaml.getStringList("PERMS"));
				
				for (DRI info : DRI.values()){
					if (yaml.contains(info.s())){
						region.addFlag(info, yaml.getBoolean(info.s()));
					}
				}
				
				regions.put(name, region);
				
			return region;
			
			case "ring":
				
				DivinityRing ring = new DivinityRing(api, name);
				yaml = YamlConfiguration.loadConfiguration(file);
				
				rings.put(name, ring);
				
			return ring;
		}
		
		return null;
	}
	
	public DivinityRing newRing(String name, DivinityRing ring){
		return ring;
	}
	
	public DivinityRegion newRegion(String name, DivinityRegion region){
		
		region.setDisabled(false);
		region.setWorld("world");
		
		regions.put(name, region);
		return region;
	}
	
	private DivinityAlliance newAlliance(String name, DivinityAlliance alliance){
		
		alliance.setDAI(DAI.BALANCE, 0);
		alliance.setDAI(DAI.NAME, name);
		alliance.setDAI(DAI.MEMBERS, new ArrayList<String>());
		alliance.setDAI(DAI.TIER, 0);
		alliance.setDAI(DAI.COLOR_1, "&7");
		alliance.setDAI(DAI.COLOR_2, "&7");
		alliance.setDAI(DAI.CENTER, "0 0 0");
		
		alliances.put(name, alliance);
		return alliance;
	}
	
	private DivinityPlayer newUser(UUID uuid, DivinityPlayer player){

		DivinityUtils.bc("Welcome &6" + player.name() + " &bto Worlds Apart!");
		Bukkit.getPlayer(player.uuid()).setDisplayName("&7" + player.name());
		Bukkit.getPlayer(player.uuid()).teleport(new Location(Bukkit.getWorld("world"), -53D, 85D, -20D));
		
		player.setDPI(DPI.JOIN_MESSAGE, "Joined!");
		player.setDPI(DPI.QUIT_MESSAGE, "Left!");
		player.setDPI(DPI.ALLIANCE_COLOR, "&3");
		player.setDPI(DPI.PM_COLOR, "&d");
		player.setDPI(DPI.GLOBAL_COLOR, "&f");
		player.setDPI(DPI.BALANCE, 0);
		player.setDPI(DPI.RANK_NAME, "Guest");
		player.setDPI(DPI.RANK_COLOR, "&8");
		player.setDPI(DPI.RANK_DESC, "&6Guest rank. No perms at all, except chat!");
		player.setDPI(DPI.STAFF_DESC, "&6A guest that should register soon!");
		player.setDPI(DPI.STAFF_COLOR, "&7");
		player.setDPI(DPI.DISPLAY_NAME, "&7" + player.name());
		player.setDPI(DPI.ALLIANCE_COLOR_1, "&7");
		player.setDPI(DPI.ALLIANCE_COLOR_2, "&7");
		player.setDPI(DPI.AFK_TIME, 0);
		player.setDPI(DPI.AFK_TIME_INIT, 0);
		player.setDPI(DPI.DEATHLOCS_TOGGLE, "true");
		player.setDPI(DPI.ALLIANCE_TOGGLE, "true");
		player.setDPI(DPI.PARTICLES_TOGGLE, "true");
		player.setDPI(DPI.FIREWORKS_TOGGLE, "true");
		player.setDPI(DPI.SCOREBOARD_TOGGLE, "true");
		player.setDPI(DPI.PLAYER_DESC, "&7&oGeneric player biography / description.\n&7&oUse /bio <message> to change this.");
		player.setDPI(DPI.PERMS, new ArrayList<String>());
		player.setDPI(DPI.CHAT_FILTER_TOGGLE, true);
		
		players.put(uuid, player);
		return player;
	}
	
	private void systemLoad(File file){
		
		YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
		DivinityPlayer player = new DivinityPlayer("system", api);
		
		for (DPI info : DPI.values()){
			if (yaml.isString(info.s())){
				player.setDPI(info, yaml.getString(info.s()));
			} else if (yaml.isList(info.s())){
				player.setDPI(info, yaml.getStringList(info.s()));
			} else if (yaml.isInt(info.s())){
				player.setDPI(info, yaml.getInt(info.s()));
			}
		}
		
		try {
			yaml.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		player.setDPI(DPI.DISPLAY_NAME, "&6Console");
		player.setDPI(DPI.PM_COLOR, "&f");
		system = player;
	}

	private void saveRes(File file, DivinityPlayer dp) throws IOException {
		
		YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
		
		for (DPI info : DPI.values()){
			if (dp.getRawInfo(info) instanceof String){
				if (!dp.getDPI(info).equals("none")){
					yaml.set(info.s(), dp.getDPI(info));
				}
			} else if (dp.getRawInfo(info) instanceof List){
				if (dp.getListDPI(info).size() > 0){
					yaml.set(info.s(), dp.getListDPI(info));
				}
			} else if (dp.getRawInfo(info) instanceof Integer){
				if (dp.getIntDPI(info) != 0){
					yaml.set(info.s(), dp.getIntDPI(info));
				}
			} else if (dp.getRawInfo(info) instanceof Location){
				Location l = (Location) dp.getRawInfo(info);
				yaml.set(info.s(), l.getWorld().getName() + " " + l.getBlockX() + " " + l.getBlockY() + " " + l.getBlockZ() + " " + l.getPitch() + " " + l.getYaw());
			} else if (dp.getRawInfo(info) instanceof ItemStack[]){
				yaml.set(info.s(), (ItemStack[])dp.getRawInfo(info));
			} else {
				yaml.set(info.s(), dp.getDPI(info));
			}
		}
		
		yaml.save(file);
	}
	
	private void saveAlliance(File file, DivinityAlliance dp) throws IOException {
		
		YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
		
		for (DAI info : DAI.values()){
			if (dp.getRawInfo(info) instanceof String){
				if (!dp.getDAI(info).equals("none")){
					yaml.set(info.s(), dp.getDAI(info));
				}
			} else if (dp.getRawInfo(info) instanceof List){
				if (dp.getListDAI(info).size() > 0){
					yaml.set(info.s(), dp.getListDAI(info));
				}
			} else if (dp.getRawInfo(info) instanceof Integer){
				if (dp.getIntDAI(info) != 0){
					yaml.set(info.s(), dp.getIntDAI(info));
				}
			} else if (dp.getRawInfo(info) instanceof Location){
				Location l = (Location) dp.getRawInfo(info);
				yaml.set(info.s(), l.getWorld().getName() + " " + l.getBlockX() + " " + l.getBlockY() + " " + l.getBlockZ() + " " + l.getPitch() + " " + l.getYaw());
			} else if (dp.getRawInfo(info) instanceof ItemStack[]){
				yaml.set(info.s(), (ItemStack[])dp.getRawInfo(info));
			} else {
				yaml.set(info.s(), dp.getDAI(info));
			}
		}
		
		yaml.save(file);
	}
	
	private void saveRegion(File file, DivinityRegion region) throws IOException {
		
		YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
		yaml.set("WORLD", region.getWorld());
		yaml.set("DISABLED", region.isDisabled());
		yaml.set("MAX_BLOCK", region.getMaxBlock());
		yaml.set("MIN_BLOCK", region.getMinBlock());
		yaml.set("HEIGHT", region.getHeight());
		yaml.set("LENGTH", region.getLength());
		yaml.set("WIDTH", region.getWidth());
		yaml.set("AREA", region.getArea());
		yaml.set("PERMS", region.getPerms());
		yaml.set("PRIORITY", region.getPriority());
		
		for (DRI info : DRI.values()){
			yaml.set(info.s(), region.getFlag(info));
		}
		
		yaml.save(file);
	}
	
	private void saveRing(File file, DivinityRing ring) throws IOException {
		
		YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
		yaml.save(file);
	}
	
	public void load() throws IOException {
		
		players = new HashMap<UUID, DivinityPlayer>();
		alliances = new HashMap<String, DivinityAlliance>();
		regions = new HashMap<String, DivinityRegion>();
		rings = new HashMap<String, DivinityRing>();
		
		List<String> dirs = Arrays.asList(dir, allianceDir, regionsDir, ringsDir);
		String objectType = "";
		
		if (!new File("./plugins/Divinity/system.yml").exists()){
			new File("./plugins/Divinity/system.yml").createNewFile();
		}
		
		systemLoad(new File("./plugins/Divinity/system.yml"));
		
		for (String d : dirs){
			
			if (!new File(d).exists()){
				new File(d).mkdirs();
			}
			
			objectType = d.equals(dir) ? "player" : d.equals(allianceDir) ? "alliance" : d.equals(regionsDir) ? "region" : "rings";
			
			for (String file : new File(d).list()){
				if (!file.contains("~")){
					makeObject(objectType, file.replace(".yml", ""));
				}
			}
		}
		
		DivinityUtils.bc("Divinity - &6" + userSize() + " &busers loaded.");
	}
	
	public void save() throws IOException {
		
		for (DivinityPlayer dp : players.values()){
			File file = new File(dir + dp.uuid().toString() + ".yml");
			saveRes(file, dp);
		}
		
		for (DivinityAlliance dp : alliances.values()){
			File file = new File(allianceDir + dp.name() + ".yml");
			saveAlliance(file, dp);
		}
		
		for (DivinityRegion region : regions.values()){
			File file = new File(regionsDir + region.name() + ".yml");
			saveRegion(file, region);
		}
		
		for (DivinityRing ring : rings.values()){
			File file = new File(ringsDir + ring.name() + ".yml");
			saveRing(file, ring);
		}
		
		saveRes(new File("./plugins/Divinity/system.yml"), system);
		
		DivinityUtils.bc("Divinity - &6" + userSize() + " &busers saved.");
	}
}