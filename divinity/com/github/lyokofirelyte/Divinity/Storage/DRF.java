package com.github.lyokofirelyte.Divinity.Storage;

import com.github.lyokofirelyte.Divinity.Manager.DivinityManager;

public enum DRF {
	
	FIRE_SPREAD("FIRE_SPREAD"),
	TP_OUT("TP_OUT"),
	TP_IN("TP_IN"),
	BLOCK_BREAK("BLOCK_BREAK"),
	BLOCK_PLACE("BLOCK_PLACE"),
	TNT_EXPLODE("TNT_EXPLODE"),
	MOB_SPAWN("MOB_SPAWN"),
	USE_COMMANDS("USE_COMMANDS"),
	CHAT("CHAT"),
	INTERACT("INTERACT"),
	LEAF_DECAY("LEAF_DECAY"),
	MELT("MELT"),
	WATER_FLOW("WATER_FLOW"),
	LAVA_FLOW("LAVA_FLOW"),
	GRAVITY("GRAVITY"),
	TAKE_DAMAGE("TAKE_DAMAGE");

	DRF(String info){
		this.info = info;
	}
	
	public String info;
	
	@DivStorageModule (types = {DivinityManager.regionsDir})
	public String s(){
		return info;
	}
}