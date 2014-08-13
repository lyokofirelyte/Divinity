package com.github.lyokofirelyte.Divinity.Storage;

public enum DAI {
	
	NAME("NAME"),
	TIER("TIER"),
	BALANCE("BALANCE"),
	MEMBERS("MEMBERS"),
	COLOR_1("COLOR_1"),
	COLOR_2("COLOR_2"),
	LEADER("LEADER"),
	CENTER("CENTER");

	DAI(String info){
		this.info = info;
	}
	
	public String info;
	
	public String s(){
		return info;
	}
}