package com.github.lyokofirelyte.Divinity.Events;

import org.bukkit.event.Event;

public abstract class DivinityEventHandler extends Event {

    public abstract void setCancelled(boolean cancel);
    public abstract boolean isCancelled();  
}