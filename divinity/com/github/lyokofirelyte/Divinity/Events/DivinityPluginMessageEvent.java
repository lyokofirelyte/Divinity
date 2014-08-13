package com.github.lyokofirelyte.Divinity.Events;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class DivinityPluginMessageEvent extends DivinityEventHandler {
	
	private boolean cancelled = false;
    private static final HandlerList handlers = new HandlerList();
    private CommandSender commandSender;
    private Player p;
    private String message;
    private String type;
    private String[] extras;

    public DivinityPluginMessageEvent(CommandSender p, String type) {
        commandSender = p;
        if (commandSender instanceof Player){
        	this.p = (Player) p;
        }
        this.type = type;
    }
    
    public DivinityPluginMessageEvent(CommandSender p, String type, String[] extras) {
        commandSender = p;
        if (commandSender instanceof Player){
        	this.p = (Player) p;
        }
        this.type = type;
        this.extras = extras;
    }

	@Override
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
    public static HandlerList getHandlerList() {
        return handlers;
    }
	
	public String[] getExtras(){
		return extras;
	}
	
	public Player getPlayer(){
		return p;
	}
	
	public CommandSender getSender(){
		return commandSender;
	}
	
	public String getMessage(){
		return message;
	}
	
	public String getType(){
		return type;
	}
	
	public void setMessage(String m){
		message = m;
	}
	
	public void setType(String t){
		type = t;
	}
}