package com.github.lyokofirelyte.Divinity.Storage;

import com.github.lyokofirelyte.Divinity.Manager.DivinityManager;

public enum ElySkill {

	MINING("MINING"), // diggy diggy hole but not dirt
	WOODCUTTING("WOODCUTTING"), // choppy choppy tree
	FARMING("FARMING"), // farm
	DIGGING("DIGGING"), // diggy diggy hole
	CRAFTING("CRAFTING"), // when you craft stuff 
	BUILDING("BUILDING"), // placing stuff
	ENDURANCE("ENDURANCE"), // falling off things without dying
	RESISTANCE("RESISTANCE"), // builds up when you get hit
	VAMPYRISM("VAMPYRISM"), // health you lost during battle = xp for kill
	ATTACK("ATTACK"), // weapons like normal
	FENCING("FENCING"), // stick fighting
	ARCHERY("ARCHERY"),
	PATROL("PATROL");
	
	ElySkill(String skill){
		this.skill = skill;
	}
	
	@DivStorageModule (types = {DivinityManager.dir})
	public String s(){
		return skill;
	}

	String skill;
}