package com.github.lyokofirelyte.Divinity.Commands;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import net.minecraft.util.gnu.trove.map.hash.THashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.SimplePluginManager;

import com.github.lyokofirelyte.Divinity.Divinity;
import com.github.lyokofirelyte.Divinity.DivinityUtils;
import com.github.lyokofirelyte.Divinity.Storage.DPI;
import com.github.lyokofirelyte.Divinity.Storage.DivinitySystem;


public class DivinityRegistry implements CommandExecutor {
	
	public Divinity main;
	
	public DivinityRegistry(Divinity i){
		main = i;
	}
          
	public static void args(Object clazz, CommandSender s, String cmd, String[] args){
    		
		for (int i = 0; i < args.length; i++){
			args[i] = args[i].toLowerCase();
		}
    		
		try {
    			
			boolean triggered = false;
    			
			for (Method m : clazz.getClass().getMethods()){
				DivArg anno = m.getAnnotation(DivArg.class);
				if (anno != null){
					for (String arg : anno.refs()){
						if (arg.equalsIgnoreCase(args[0])){
							if (s.hasPermission(anno.perm())){
								triggered = true;
								if (anno.player()){
									if (s instanceof Player){
										m.invoke(clazz, (Player) s, args);
									} else {
										DivinityUtils.s(s, "&cConsole players cannot run this command!");
									}
								} else {
									m.invoke(clazz, s, args);
								}
							} else {
								DivinityUtils.s(s, "&cYou don't have permission for that!");
							}
						}
					}
				}
			}
    			
			if (!(triggered)){
				DivinityUtils.s(s, "&cThere's a time and a place for everything!");
			}
    			
		} catch (Exception e){
			Bukkit.getLogger().log(Level.SEVERE, "An internal error has occured involving the command '" + cmd + "'!");
			e.printStackTrace();	
		}
	}

	public void registerCommands(Object... classes){
                
		Field f = null;
		CommandMap scm = null;
	                
		try {
			f = SimplePluginManager.class.getDeclaredField("commandMap");
			f.setAccessible(true);
			scm = (CommandMap) f.get(Bukkit.getPluginManager());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	                
		for (Object obj : classes){
			for (Method method : obj.getClass().getMethods()) {
				if (method.getAnnotation(DivCommand.class) != null){
					DivCommand anno = method.getAnnotation(DivCommand.class);
					try {
						DivCmd command = new DivCmd(anno.aliases()[0]);
						command.setUsage(anno.help());
						command.setAliases(Arrays.asList(anno.aliases()));
						command.setDescription(anno.desc());
						/*if (scm.getCommand(anno.aliases()[0]) != null){
							scm.getCommand(anno.aliases()[0]).unregister(scm);
						}*/
						scm.register("ely", command);
						command.setExecutor(this);
						main.commandMap.put(Arrays.asList(anno.aliases()), obj);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	
    	if (sender instanceof Player){
    		if (main.getDivPlayer((Player)sender).getBool(DPI.DISABLED)){
    			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&oYou are prevented from using commands at this time."));
    			return true;
    		}
    	}
    	
    	List<String> filteredCmds = Arrays.asList("bio", "tell", "pm", "t", "msg", "r");
    	
    	if (filteredCmds.contains(cmd)){
	    	DivinitySystem system = main.getSystem();
	    	Map<Integer, String> filters = new THashMap<Integer, String>();
	    	
	    	for (int x = 0; x < args.length; x++){
	    		for (String filter : system.getList(DPI.FILTER)){
	    			if (ChatColor.stripColor(DivinityUtils.AS(args[x].toLowerCase())).contains(filter.split(" % ")[0])){
	    				filters.put(x, args[x].replace(filter.split(" % ")[0], filter.split(" % ")[1]));
	    			}
	    		}
	    	}
	    	
	    	for (Integer i : filters.keySet()){
	    		args[i] = filters.get(i);
	    	}
    	}
    	
    	for (List<String> cmdList : main.commandMap.keySet()){
    		if (cmdList.contains(label)){
    			for (String command : cmdList){
    				if (command.equals(label)){
    					Object obj = main.commandMap.get(cmdList);
    					for (Method m : obj.getClass().getMethods()){
    						if (m.getAnnotation(DivCommand.class) != null && Arrays.asList(m.getAnnotation(DivCommand.class).aliases()).contains(command)){
    							try {
    								DivCommand anno = m.getAnnotation(DivCommand.class);
    								if ((sender instanceof Player && main.getDivPlayer((Player)sender).getList(DPI.PERMS).contains(anno.perm())) || sender instanceof Player == false || sender.isOp()){
    									if (args.length > anno.max() || args.length < anno.min()){
    										DivinityUtils.s(sender, anno.help());
    										return true;
    									}     
    									if (anno.name().equals("none")){
    										if (anno.player()){
    											if (sender instanceof Player){
    												m.invoke(obj, (Player) sender, args);
    											} else {
    												DivinityUtils.s(sender, "&cConsole players cannot run this command!");
    											}
    										} else {
    											m.invoke(obj, sender, args);
    										}
    									} else {
    										if (anno.player()){
    											if (sender instanceof Player){
    												m.invoke(obj, (Player) sender, args, label);
    											} else {
    												DivinityUtils.s(sender, "&cConsole players cannot run this command!");
    											}
    										} else {
    											m.invoke(obj, sender, args, label);
    										}
    									}
    								} else {
    									DivinityUtils.s(sender, "&4No permission!");
    								}
    							} catch (Exception e) {
    								e.printStackTrace();
    							}
    						}
    					}
    				}
    			}
    		}
    	}
    	return true;
    }
}