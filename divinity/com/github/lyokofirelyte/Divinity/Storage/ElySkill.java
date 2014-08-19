package com.github.lyokofirelyte.Divinity.Storage;

import com.github.lyokofirelyte.Divinity.Manager.DivinityManager;

public enum ElySkill {

	MINING("MINING"),
	WOODCUTTING("WOODCUTTING"),
	FARMING("FARMING");
	
	ElySkill(String skill){
		this.skill = skill;
	}
	
	@DivStorageModule (types = {DivinityManager.dir})
	public String s(){
		return skill;
	}

	String skill;
}