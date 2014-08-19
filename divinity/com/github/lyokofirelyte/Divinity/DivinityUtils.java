package com.github.lyokofirelyte.Divinity;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockIterator;

import com.github.lyokofirelyte.Divinity.Storage.DPI;

public class DivinityUtils {

	public static Divinity api;
	
	public DivinityUtils(Divinity i){
		api = i;
	}
	
	public static String h = "&3Elysian &7❅ &b";
	private static String h2 = "&7❅ &b";
	
	public List<String> AS(List<String> s){
		
		List<String> toReturn = new ArrayList<String>();
		
		for (String ss : s){
			toReturn.add(AS(ss));
		}
		
		return toReturn;
		
	}
	
	public static String AS(String message){
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public static BlockFace getPlayerDirection(Player player) {
		
		List<BlockFace> faces = Arrays.asList(
			BlockFace.WEST, BlockFace.WEST_NORTH_WEST, BlockFace.NORTH_NORTH_WEST,
			BlockFace.NORTH, BlockFace.NORTH_NORTH_EAST, BlockFace.NORTH_EAST,
			BlockFace.EAST_NORTH_EAST, BlockFace.EAST, BlockFace.EAST_SOUTH_EAST,
			BlockFace.SOUTH_EAST,BlockFace.SOUTH_SOUTH_EAST, BlockFace.SOUTH, 
			BlockFace.SOUTH_SOUTH_WEST, BlockFace.SOUTH_WEST, BlockFace.WEST_SOUTH_WEST, BlockFace.WEST
		);

		float y = player.getLocation().getYaw();
	     
		if( y < 0 ){y += 360;}
	     
		y %= 360;
	     
		int i = (int)((y+8) / 22.5);
		
		return faces.get(i) != null ? faces.get(i) : BlockFace.WEST;
	}
	
	public static void s(CommandSender sender, String message){
		if (sender instanceof Player){
			if (api.getDivPlayer((Player)sender).getBool(DPI.ELY)){
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', h2 + message));
			} else {
				api.getDivPlayer((Player)sender).set(DPI.ELY, true);
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', h + message));
			}
		} else {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', h + message));
		}
	}
	
	public static void bc(String message){
		Bukkit.broadcastMessage(AS(h + message));
	}
	
	public static void customBC(String message){
		Bukkit.broadcastMessage(AS(message));
	}

	public Sound getRandomNote(){
		
		final List<Sound> sounds = Arrays.asList(Sound.NOTE_BASS, Sound.NOTE_BASS_DRUM, Sound.NOTE_BASS_GUITAR, Sound.NOTE_PIANO, Sound.NOTE_PLING, Sound.NOTE_SNARE_DRUM, Sound.NOTE_STICKS);
		
		Random rand = new Random();
		int nextInt = rand.nextInt(sounds.size()-1);
		return sounds.get(nextInt);
	}
	
    public Boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public String encrypt(String toEncrypt, String type){ 
    	
        try { 
            MessageDigest digest = MessageDigest.getInstance(type);               
            digest.update(toEncrypt.getBytes()); 
            byte[] bytes = digest.digest();       

            StringBuilder sb = new StringBuilder(); 

            for (int i = 0; i < bytes.length; i++) { 
                sb.append(String.format("%02X", bytes[i])); 
            } 

            return sb.toString().toLowerCase();
        } 
        catch (Exception exc) { return null; }
    }

	public String getRandomChatColor(){
		
		final List<String> colors = Arrays.asList("&0", "&1", "&2", "&3", "&4", "&5", "&6", "&7", "&8", "&9", "&a", "&b", "&c", "&d", "&e", "&f");
		
		Random rand = new Random();
		int nextInt = rand.nextInt(colors.size()-1);
		return colors.get(nextInt);
	}
	
	public Color getRandomColor(){
		
		final List<Color> colors = Arrays.asList(Color.SILVER, Color.RED, Color.WHITE, Color.BLUE, Color.ORANGE, Color.FUCHSIA, Color.AQUA, Color.PURPLE, Color.GREEN, Color.TEAL, Color.YELLOW);
	
		Random rand = new Random();
		int nextInt = rand.nextInt(colors.size()-1);
		return colors.get(nextInt);
	}
	
	public DyeColor getRandomDyeColor(){
		
		final List<DyeColor> colors = Arrays.asList(DyeColor.RED, DyeColor.WHITE, DyeColor.BLUE, DyeColor.ORANGE, DyeColor.GREEN, DyeColor.BLACK, DyeColor.PURPLE, DyeColor.SILVER, DyeColor.YELLOW);
	
		Random rand = new Random();
		int nextInt = rand.nextInt(colors.size()-1);
		return colors.get(nextInt);
	}

	public String getTime() {
	  	Calendar cal = Calendar.getInstance();
	  	cal.getTime();
	  	SimpleDateFormat sdf = new SimpleDateFormat("HH.mm.ss");
	  	return ( sdf.format(cal.getTime()) );
	}
	
	public String getTime(Long l) {
	  	Calendar cal = Calendar.getInstance();
	  	cal.setTimeInMillis(l);
	  	SimpleDateFormat sdf = new SimpleDateFormat("M.dd @ HH.mm");
	  	return ( sdf.format(cal.getTime()) );
	}
	
	public String getTimeFull() {
		Calendar cal = Calendar.getInstance();
	  	cal.getTime();
	  	SimpleDateFormat sdf = new SimpleDateFormat("EEEEE, MMMMM dd, H.mm");
	  	return ( sdf.format(cal.getTime()) );
	}
	
    public List<Location> circle (Location loc, Integer r, Integer h, Boolean hollow, Boolean sphere, int plus_y) {
        List<Location> circleblocks = new ArrayList<Location>();
        int cx = loc.getBlockX();
        int cy = loc.getBlockY();
        int cz = loc.getBlockZ();
        for (int x = cx - r; x <= cx +r; x++)
            for (int z = cz - r; z <= cz +r; z++)
                for (int y = (sphere ? cy - r : cy); y < (sphere ? cy + r : cy + h); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r*r && !(hollow && dist < (r-1)*(r-1))) {
                        Location l = new Location(loc.getWorld(), x, y + plus_y, z);
                        circleblocks.add(l);
                        }
                    }
     
        return circleblocks;
    }
    
    public List<String> strCircle(Location loc, Integer r, Integer h, Boolean hollow, Boolean sphere, int plus_y) {
        List<String> circleblocks = new ArrayList<String>();
        int cx = loc.getBlockX();
        int cy = loc.getBlockY();
        int cz = loc.getBlockZ();
        for (int x = cx - r; x <= cx +r; x++)
            for (int z = cz - r; z <= cz +r; z++)
                for (int y = (sphere ? cy - r : cy); y < (sphere ? cy + r : cy + h); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r*r && !(hollow && dist < (r-1)*(r-1))) {
                    	String l = loc.getWorld().getName() + " " + x + " " + (y+plus_y) + " " + z;
                        circleblocks.add(l);
                        }
                    }
     
        return circleblocks;
    }
    
    public boolean isInteger(String s) {
        try {
          Integer.parseInt(s);
        } catch (NumberFormatException e) {
          return false;
        }

        return true;
      }

      public String createString(String[] args, int firstArg) {
        	
        String msg = args[firstArg];
        
        for (int x = firstArg+1; x < args.length; x++){
        	msg = msg + " " + args[x];
        }
        
        return msg;
      }
      
 	 public Entity getTarget(final Player player) {
		 
	        BlockIterator iterator = new BlockIterator(player.getWorld(), player
	                .getLocation().toVector(), player.getEyeLocation()
	                .getDirection(), 0, 100);
	        Entity target = null;
	        while (iterator.hasNext()) {
	            Block item = iterator.next();
	            for (Entity entity : player.getNearbyEntities(100, 100, 100)) {
	                int acc = 2;
	                for (int x = -acc; x < acc; x++)
	                    for (int z = -acc; z < acc; z++)
	                        for (int y = -acc; y < acc; y++)
	                            if (entity.getLocation().getBlock()
	                                    .getRelative(x, y, z).equals(item)) {
	                                return target = entity;
	                            }
	            }
	        }
	        return target;
	    }

	public void effects(Location ll){
		
		List<Location> circleblocks = circle(ll, 3, 1, true, false, 0);
		List<Location> circleblocks2 = circle(ll, 3, 1, true, false, 1);
	
		for (Location l : circleblocks){
			ll.getWorld().playEffect(l, Effect.SMOKE, 0);
			ll.getWorld().playEffect(l, Effect.MOBSPAWNER_FLAMES, 0);
			ll.getWorld().playEffect(l, Effect.ENDER_SIGNAL, 0);
		}
			
		for (Location l : circleblocks2){
			ll.getWorld().playEffect(l, Effect.SMOKE, 0);
			ll.getWorld().playEffect(l, Effect.MOBSPAWNER_FLAMES, 0);
			ll.getWorld().playEffect(l, Effect.ENDER_SIGNAL, 0);
		}
	}

	@SuppressWarnings("deprecation")
	public void specialEffects(Location ll, Material m){
		
		List<Location> circleblocks = circle(ll, 3, 1, true, false, 0);
		List<Location> circleblocks2 = circle(ll, 3, 1, true, false, 1);
	
		for (Location l : circleblocks){
			ll.getWorld().playEffect(l, Effect.STEP_SOUND, m.getId());
		}
			
		for (Location l : circleblocks2){
			ll.getWorld().playEffect(l, Effect.STEP_SOUND, m.getId());
		}
	}
	
	public void lowerEffects(Location ll){
		
		List<Location> circleblocks = circle(ll, 3, 1, true, false, 0);

		for (Location l : circleblocks){
			ll.getWorld().playEffect(l, Effect.ENDER_SIGNAL, 0);
		}
	}
 	
	public void effects(Player q){
		
		List<Location> circleblocks = circle(q.getLocation(), 3, 1, true, false, 0);
		List<Location> circleblocks2 = circle(q.getLocation(), 3, 1, true, false, 1);
	
		for (Location l : circleblocks){
			q.getWorld().playEffect(l, Effect.SMOKE, 0);
			q.getWorld().playEffect(l, Effect.MOBSPAWNER_FLAMES, 0);
			q.getWorld().playEffect(l, Effect.ENDER_SIGNAL, 0);
		}
			
		for (Location l : circleblocks2){
			q.getWorld().playEffect(l, Effect.SMOKE, 0);
			q.getWorld().playEffect(l, Effect.MOBSPAWNER_FLAMES, 0);
			q.getWorld().playEffect(l, Effect.ENDER_SIGNAL, 0);
		}
	}
	
	public Boolean dispName(ItemStack i, String s){
		if (i != null && i.hasItemMeta() && i.getItemMeta().hasDisplayName() && i.getItemMeta().getDisplayName().substring(2).equals(s) && i.getItemMeta().hasLore()){
			return true;
		}
		return false;
	}
	
	public EntityType getRandomEntity(){
		List<EntityType> entities = new ArrayList<EntityType>();
		entities.add(EntityType.CREEPER);
		entities.add(EntityType.SPIDER);
		entities.add(EntityType.PIG_ZOMBIE);
		entities.add(EntityType.SKELETON);
		entities.add(EntityType.SILVERFISH);
		entities.add(EntityType.SPIDER);
		entities.add(EntityType.ZOMBIE);
		entities.add(EntityType.ZOMBIE);
		entities.add(EntityType.SKELETON);
		Random rand = new Random();
		int sel = rand.nextInt(entities.size());
		return entities.get(sel);
	}
	
}