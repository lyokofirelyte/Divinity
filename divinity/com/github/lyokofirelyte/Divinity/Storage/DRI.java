package com.github.lyokofirelyte.Divinity.Storage;

import com.github.lyokofirelyte.Divinity.Manager.DivinityManager;

public enum DRI {
	
	PERMS("PERMS"),
	PRIORITY("PRIORITY"),
	LENGTH("LENGTH"),
	HEIGHT("HEIGHT"),
	WIDTH("WIDTH"),
	AREA("AREA"),
	MAX_BLOCK("MAX_BLOCK"),
	MIN_BLOCK("MIN_BLOCK"),
	DISABLED("DISABLED"),
	WORLD("WORLD");

	DRI(String info){
		this.info = info;
	}
	
	public String info;
	
	@DivStorageModule (types = {DivinityManager.regionsDir})
	public String s(){
		return info;
	}
}