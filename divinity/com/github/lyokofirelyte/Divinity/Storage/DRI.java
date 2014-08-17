package com.github.lyokofirelyte.Divinity.Storage;

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
	
	public String s(){
		return info;
	}
}