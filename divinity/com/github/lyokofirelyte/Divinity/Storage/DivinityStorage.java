package com.github.lyokofirelyte.Divinity.Storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import com.github.lyokofirelyte.Divinity.Divinity;

public class DivinityStorage implements DivinityPlayer, DivinityAlliance, DivinityRing, DivinitySkillPlayer, DivinityRegion {

	private Divinity api;
	private UUID uuid;
	private String name;
	
	// rings //
	private String destination;
	private String[] center;
	
	private List<Player> players;
	private Map<Integer, Byte> mat = new HashMap<Integer, Byte>();
	
	private boolean inOperation = false;
	
	// regions //
	
	private Map<DRF, Boolean> flags = new HashMap<DRF, Boolean>();
	
	public DivinityStorage(UUID u, Divinity i){
		api = i;
		uuid = u;
		if (Bukkit.getPlayer(u) != null){
			name = Bukkit.getPlayer(u).getName();
		} else {
			name = Bukkit.getOfflinePlayer(u).getName();
		}
	}

	public DivinityStorage(String name, Divinity i){
		api = i;
		this.name = name;
	}
	
	public Map<Object, Object> stuff = new HashMap<Object, Object>();
	
	public String name(){
		return name;
	}
	
	public UUID uuid(){
		return uuid;
	}
	
	public Object getRawInfo(Object i){
		return stuff.containsKey(i) ? stuff.get(i) : null;
	}
	
	public String getStr(Object i){

		if (stuff.containsKey(i)){
			if (stuff.get(i) instanceof String){
				return (String) stuff.get(i);
			} else {
				return stuff.get(i) + "";
			}
		} else {
			return "none";
		}
	}
	
	public int getInt(Object i){

		if (stuff.containsKey(i)){
			if (stuff.get(i) instanceof Integer){
				return (Integer) stuff.get(i);
			} else {
				return api.divUtils.isInteger(stuff.get(i) + "") ? Integer.parseInt(stuff.get(i) + "") : 0;
			}
		} else {
			return 0;
		}
	}
	
	public long getLong(Object i){

		if (stuff.containsKey(i)){
			if (stuff.get(i) instanceof Long){
				return (Long) stuff.get(i);
			} else {
				try {
					return Long.parseLong(stuff.get(i) + "");
				} catch (Exception e){
					return 0L;
				}
			}
		} else {
			return 0L;
		}
	}
	
	public double getDouble(Object i){

		if (stuff.containsKey(i)){
			if (stuff.get(i) instanceof Double){
				return (Double) stuff.get(i);
			} else {
				try {
					return Double.parseDouble(stuff.get(i) + "");
				} catch (Exception e){
					return 0D;
				}
			}
		} else {
			return 0D;
		}
	}
	
	public boolean getBool(Object i){

		if (stuff.containsKey(i)){
			if (stuff.get(i) instanceof Boolean){
				return (Boolean) stuff.get(i);
			} else {
				try {
					return Boolean.valueOf(stuff.get(i) + "");
				} catch (Exception e){
					return false;
				}
			}
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> getList(Object i){
		
		if (stuff.containsKey(i)){
			try {
				if (stuff.get(i) instanceof List){
					return (List<String>) stuff.get(i);
				}
				stuff.put(i, new ArrayList<String>());
			} catch (Exception e){
				stuff.put(i, new ArrayList<String>());
			}
		} else {
			stuff.put(i, new ArrayList<String>());
		}
		
		return (List<String>) stuff.get(i);
	}
	
	public Location getLoc(Object i){
		
		if (stuff.containsKey(i)){
			if (stuff.get(i) instanceof Location){
				return (Location) stuff.get(i);
			}
			try {
				String[] loc = stuff.get(i).toString().split(" ");
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
	
	public ItemStack[] getStack(Object i){
		
		ItemStack[] inv = null;
		
		if (stuff.containsKey(i)){
			if (stuff.get(i) instanceof ItemStack[]){
				inv = (ItemStack[]) stuff.get(i);
			} else {
				stuff.put(i, new ItemStack[]{});
				inv = new ItemStack[]{};
			}
		} else {
			stuff.put(i, new ItemStack[]{});
			inv = new ItemStack[]{};
		}
		
		return inv;
	}
	
	public void set(Object i, Object infos){
		
		stuff.put(i, infos);
		
		/*if (i instanceof DPI){
			if (api.divManager.getAllUsers().size() > 0){
				api.update(this);
			}
		} else if (i instanceof DAI){
			api.aUpdate(this);
		}*/
	}
	
	// skills //

	@Override
	public int getLevel(ElySkill skill) {
		return stuff.containsKey(skill) ? getInt(skill) : 0;
	}

	@Override
	public void setLevel(ElySkill skill, int level) {
		stuff.put(skill, level);
	}

	@Override
	public boolean hasLevel(ElySkill skill, int level) {
		return getLevel(skill) >= level;
	}
	
	// rings //
	
	public String[] getCenter(){
		return center;
	}
	
	public String getDest(){
		return destination;
	}
	
	public Location getCenterLoc(){
		return new Location(Bukkit.getWorld(center[0]), Double.parseDouble(center[1]), Double.parseDouble(center[2]), Double.parseDouble(center[3]), Float.parseFloat(center[4]), Float.parseFloat(center[5]));
	}
	
	public boolean isInOperation(){
		return inOperation;
	}
	
	public int getMatId(){
		return new ArrayList<Integer>(mat.keySet()).get(0);
	}
	
	public byte getMatByte(){
		return new ArrayList<Byte>(mat.values()).get(0);
	}
	
	public List<Player> getPlayers(){
		return players;
	}
	
	public void addPlayer(Player name){
		if (!players.contains(name)){
			players.add(name);
		}
	}
	
	public void remPlayer(Player name){
		if (players.contains(name)){
			players.remove(name);
		}
	}
	
	public void setCenter(String[] c){
		center = c;
	}
	
	public void setCenter(String c){
		center = c.split(" ");
	}
	
	public void setDest(String dest){
		destination = dest;
	}
	
	public void setRingMaterial(int id, byte by){
		mat.put(id, by);
	}
	
	public void setInOperation(boolean b){
		inOperation = b;
	}
	
	// regions //
	
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
		return flags.containsKey(flag) ? flags.get(flag) : false;
	}
	
	public Map<DRF, Boolean> getFlags(){
		return flags;
	}
	
	public boolean canBuild(Player p){
		
		DivinityPlayer dp = api.getDivPlayer(p);
		
		for (String perm : getList(DRI.PERMS)){
			if (dp.getList(DPI.PERMS).contains(perm)){
				return true;
			}
		}
		
		return getBool(DRI.DISABLED) ? true : false;
	}
	

	@Override
	public World world() {
		return Bukkit.getWorld(getStr(DRI.WORLD));
	}

	@Override
	public String getWorld() {
		return getStr(DRI.WORLD);
	}

	@Override
	public List<String> getPerms() {
		return getList(DRI.PERMS);
	}
	
	// alliances //
	
	public boolean exists(){
		return api.divManager.getAllianceMap().containsKey(name);
	}

}