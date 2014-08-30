package com.github.lyokofirelyte.Divinity.Storage;

import com.github.lyokofirelyte.Divinity.Manager.DivinityManager;

public enum DAI {
	
	NAME("NAME"),
	TIER("TIER"),
	BALANCE("BALANCE"),
	MEMBERS("MEMBERS"),
	COLOR_1("COLOR_1"),
	COLOR_2("COLOR_2"),
	LEADER("LEADER"),
	DESC("DESC"),
	CENTER("CENTER");

	DAI(String info){
		this.info = info;
	}
	
	public String info;
	
	@DivStorageModule (types = {DivinityManager.allianceDir})
	public String s(){
		return info;
	}
}