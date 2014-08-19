package com.github.lyokofirelyte.Divinity.Storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import com.github.lyokofirelyte.Divinity.Divinity;
import com.github.lyokofirelyte.Divinity.Manager.DivinityManager;

public class DivinityStorage implements DivInfo {

	private Divinity api;
	private UUID uuid;
	private String name;
	
	public DivinityStorage(UUID u, Divinity i){
		api = i;
		uuid = u;
		name = Bukkit.getPlayer(u) != null ? Bukkit.getPlayer(u).getName() : Bukkit.getOfflinePlayer(u).getName();
	}

	public DivinityStorage(String n, Divinity i){
		api = i;
		name = n;
	}
	
	public Map<String, Object> stuff = new HashMap<String, Object>();
	
	public String name(){
		return name;
	}
	
	public UUID uuid(){
		return uuid;
	}
	
	public Object getRawInfo(Enum<?> i){
		return stuff.containsKey(i.toString()) ? stuff.get(i.toString()) : null;
	}
	
	public String getStr(Enum<?> i){

		if (stuff.containsKey(i.toString())){
			if (stuff.get(i.toString()) instanceof String){
				return (String) stuff.get(i.toString());
			} else {
				return stuff.get(i.toString()) + "";
			}
		} else {
			return "none";
		}
	}
	
	public int getInt(Enum<?> i){

		if (stuff.containsKey(i.toString())){
			if (stuff.get(i.toString()) instanceof Integer){
				return (Integer) stuff.get(i.toString());
			} else {
				return api.divUtils.isInteger(stuff.get(i.toString()) + "") ? Integer.parseInt(stuff.get(i.toString()) + "") : 0;
			}
		} else {
			return 0;
		}
	}
	
	public long getLong(Enum<?> i){

		if (stuff.containsKey(i.toString())){
			if (stuff.get(i.toString()) instanceof Long){
				return (Long) stuff.get(i.toString());
			} else {
				try {
					return Long.parseLong(stuff.get(i.toString()) + "");
				} catch (Exception e){
					return 0L;
				}
			}
		} else {
			return 0L;
		}
	}
	
	public byte getByte(Enum<?> i){

		if (stuff.containsKey(i.toString())){
			if (stuff.get(i.toString()) instanceof Byte){
				return (Byte) stuff.get(i.toString());
			} else {
				try {
					return Byte.parseByte(stuff.get(i.toString()) + "");
				} catch (Exception e){
					return 0;
				}
			}
		} else {
			return 0;
		}
	}
	
	public double getDouble(Enum<?> i){

		if (stuff.containsKey(i.toString())){
			if (stuff.get(i.toString()) instanceof Double){
				return (Double) stuff.get(i.toString());
			} else {
				try {
					return Double.parseDouble(stuff.get(i.toString()) + "");
				} catch (Exception e){
					return 0D;
				}
			}
		} else {
			return 0D;
		}
	}
	
	public boolean getBool(Enum<?> i){

		if (stuff.containsKey(i.toString())){
			if (stuff.get(i.toString()) instanceof Boolean){
				return (Boolean) stuff.get(i.toString());
			} else {
				try {
					return Boolean.valueOf(stuff.get(i.toString()) + "");
				} catch (Exception e){
					return false;
				}
			}
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> getList(Enum<?> i){
		
		if (stuff.containsKey(i.toString())){
			try {
				if (stuff.get(i.toString()) instanceof List){
					return (List<String>) stuff.get(i.toString());
				}
				stuff.put(i.toString(), new ArrayList<String>());
			} catch (Exception e){
				stuff.put(i.toString(), new ArrayList<String>());
			}
		} else {
			stuff.put(i.toString(), new ArrayList<String>());
		}
		
		return (List<String>) stuff.get(i.toString());
	}
	
	public Location getLoc(Enum<?> i){
		
		if (stuff.containsKey(i.toString())){
			if (stuff.get(i.toString()) instanceof Location){
				return (Location) stuff.get(i.toString());
			}
			try {
				String[] loc = stuff.get(i.toString()).toString().split(" ");
				if (loc.length == 4){
					return new Location(Bukkit.getWorld(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2]), Double.parseDouble(loc[3]));
				} else if (loc.length == 6){
					return new Location(Bukkit.getWorld(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2]), Double.parseDouble(loc[3]), Float.parseFloat(loc[4]), Float.parseFloat(loc[5]));
				}
			} catch (Exception e){
				return new Location(Bukkit.getWorld("world"), 0, 0, 0);
			}
		}
		
		return new Location(Bukkit.getWorld("world"), 0, 0, 0);
	}
	
	public ItemStack[] getStack(Enum<?> i){
		
		ItemStack[] inv = null;
		
		if (stuff.containsKey(i.toString())){
			if (stuff.get(i.toString()) instanceof ItemStack[]){
				inv = (ItemStack[]) stuff.get(i.toString());
			} else {
				stuff.put(i.toString(), new ItemStack[]{});
				inv = new ItemStack[]{};
			}
		} else {
			stuff.put(i.toString(), new ItemStack[]{});
			inv = new ItemStack[]{};
		}
		
		return inv;
	}
	
	public void set(Enum<?> i, Object infos){
		stuff.put(i.toString(), infos);
	}
	
	public class Player extends DivinityStorage implements DivinityPlayer {

		public Player(UUID n, Divinity i) {
			super(n, i);
		}
		
		public UUID uuid(){
			return uuid;
		}
		
		public boolean isOnline(){
			return Bukkit.getPlayer(uuid) != null;
		}
	}
	
	public class Skill extends DivinityStorage implements DivinitySkillPlayer {

		public Skill(String n, Divinity i) {
			super(n, i);
		}

		public int getLevel(ElySkill skill){
			return stuff.containsKey(skill.toString()) ? getInt(skill) : 0;
		}
	
		public void setLevel(ElySkill skill, int level){
			stuff.put(skill.toString(), level);
		}
	
		public boolean hasLevel(ElySkill skill, int level){
			return getLevel(skill) >= level;
		}
	}

	public class Ring extends DivinityStorage implements DivinityRing {
		
		public Ring(String n, Divinity i) {
			super(n, i);
		}

		private List<org.bukkit.entity.Player> players;
		private boolean inOperation = false;
		
		public String[] getCenter(){
			return getStr(DRS.CENTER).split(" ");
		}
		
		public String getDest(){
			return getStr(DRS.DEST);
		}
		
		public Location getCenterLoc(){
			return new Location(Bukkit.getWorld(getCenter()[0]), Double.parseDouble(getCenter()[1]), Double.parseDouble(getCenter()[2]), Double.parseDouble(getCenter()[3]), Float.parseFloat(getCenter()[4]), Float.parseFloat(getCenter()[5]));
		}
		
		public boolean isInOperation(){
			return inOperation;
		}
		
		public int getMatId(){
			return getInt(DRS.MAT_ID);
		}
		
		public byte getMatByte(){
			return getByte(DRS.BYTE_ID);
		}
		
		public List<org.bukkit.entity.Player> getPlayers(){
			return players;
		}
		
		public void addPlayer(org.bukkit.entity.Player name){
			if (!players.contains(name)){
				players.add(name);
			}
		}
		
		public void remPlayer(org.bukkit.entity.Player name){
			if (players.contains(name)){
				players.remove(name);
			}
		}
		
		public void setInOperation(boolean b){
			inOperation = b;
		}
	}
	
	public class Region extends DivinityStorage implements DivinityRegion {
	
		public Region(String n, Divinity i) {
			super(n, i);
		}

		public int getPriority(){
			return getInt(DRI.PRIORITY);
		}
		
		public int getLength(){
			return getInt(DRI.LENGTH);
		}
		
		public int getWidth(){
			return getInt(DRI.WIDTH);
		}
		
		public int getHeight(){
			return getInt(DRI.HEIGHT);
		}
		
		public int getArea(){
			return getInt(DRI.AREA);
		}
		
		public String getMaxBlock(){
			return getStr(DRI.MAX_BLOCK);
		}
		
		public String getMinBlock(){
			return getStr(DRI.MIN_BLOCK);
		}
		
		public boolean isDisabled(){
			return getBool(DRI.DISABLED);
		}
		
		public boolean getFlag(DRF flag){
			return getBool(flag);
		}
		
		public Map<DRF, Boolean> getFlags(){
			Map<DRF, Boolean> flagMap = new HashMap<>();
			for (DRF f : DRF.values()){
				if (stuff.containsKey(f.toString())){
					flagMap.put(f, getBool(f));
				}
			}
			return flagMap;
		}
		
		public boolean canBuild(org.bukkit.entity.Player p){
			
			DivinityPlayer dp = api.getDivPlayer(p);
			
			for (String perm : getList(DRI.PERMS)){
				if (dp.getList(DPI.PERMS).contains(perm)){
					return true;
				}
			}
			
			return getBool(DRI.DISABLED) ? true : false;
		}

		public World world() {
			return Bukkit.getWorld(getStr(DRI.WORLD));
		}

		public String getWorld() {
			return getStr(DRI.WORLD);
		}

		public List<String> getPerms() {
			return getList(DRI.PERMS);
		}	
	}

	public class Alliance extends DivinityStorage implements DivinityAlliance {
		
		public Alliance(String n, Divinity i) {
			super(n, i);
		}

		public boolean exists(){
			return api.divManager.getMap(DivinityManager.allianceDir).containsKey(name);
		}
	}
	
	public class System extends DivinityStorage implements DivinitySystem {

		public System(String n, Divinity i) {
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
}