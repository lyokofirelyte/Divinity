package com.github.lyokofirelyte.Divinity.Storage;

public enum ElySkill {

	MINING("MINING"),
	WOODCUTTING("WOODCUTTING"),
	FARMING("FARMING");
	
	ElySkill(String skill){
		this.skill = skill;
	}
	
	public String s(){
		return skill;
	}
	
	String skill;
}