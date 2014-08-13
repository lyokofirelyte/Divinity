package com.github.lyokofirelyte.Divinity.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import com.github.lyokofirelyte.Divinity.Storage.DPI;

public class DivinityChannelEvent extends DivinityEventHandler implements Cancellable {
	

    private static final HandlerList handlers = new HandlerList();
    private Player p;
    private boolean cancelled;
    private String perm;
    private String message;
    private String header;
    private String color;
    private String pName;
    private DPI toggle;

    public DivinityChannelEvent(Player sender, String perm, String header, String message, DPI toggle) {
        this.perm = perm;
        this.message = message;
        this.toggle = toggle;
        this.header = header;
        this.color = "&f";
        p = sender;
    }
    
    public DivinityChannelEvent(Player sender, String perm, String header, String message) {
        this.perm = perm;
        this.message = message;
        this.header = header;
        this.color = "&f";
        toggle = DPI.ELY;
        p = sender;
    }
    
    public DivinityChannelEvent(String sender, String perm, String header, String message, String color) {
        this.perm = perm;
        this.message = message;
        this.header = header;
        this.color = color;
        toggle = DPI.ELY;
        pName = sender;
    }
    
    public DivinityChannelEvent(Player sender, String perm, String header, String[] message, String color, DPI toggle) {
    	
    	String msg = message[0];
		 
		for (int i = 1; i < message.length; i++){
			msg = msg + " " + message[i];
		}
		 
        this.perm = perm;
        this.message = msg;
        this.toggle = toggle;
        this.header = header;
        this.color = color;
        p = sender;
    }
    
    public DivinityChannelEvent(Player sender, String perm, String header, String[] message, String color) {
    	
    	String msg = message[0];
		 
		for (int i = 1; i < message.length; i++){
			msg = msg + " " + message[i];
		}
		
        this.perm = perm;
        this.message = msg;
        this.header = header;
        this.color = color;
        toggle = DPI.ELY;
        p = sender;
    }
    
    public DPI getToggle(){
    	return toggle;
    }
    
    public String getHeader(){
    	return header;
    }

    public String getPerm(){
    	return perm;
    }
    
    public String getMessage(){
    	return message;
    }
    
    public String getColor(){
    	return color;
    }
    
    public void setColor(String a){
    	color = a;
    }
    
    public void setToggle(DPI s){
    	toggle = s;
    }
    
    public void setHeader(String s){
    	header = s;
    }
    
    public void setMessage(String s){
    	message = s;
    }
    
    public void setPerm(String s){
    	perm = s;
    }
    
    public void setSender(Player p){
    	this.p = p;
    }
 
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
    
    public boolean isCancelled() {
        return cancelled;
    }

    public Player getSender(){
    	return p;
    }
    
    public String getSenderName(){
    	return pName;
    }
 
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }
}