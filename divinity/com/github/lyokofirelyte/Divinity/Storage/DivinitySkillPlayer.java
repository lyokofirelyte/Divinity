package com.github.lyokofirelyte.Divinity.Storage;

public interface DivinitySkillPlayer extends DivInfo {
	
	public int getLevel(ElySkill skill);
	
	public void setLevel(ElySkill skill, int level);
	
	public boolean hasLevel(ElySkill skill, int level);
}