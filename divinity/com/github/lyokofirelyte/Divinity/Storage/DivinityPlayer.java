package com.github.lyokofirelyte.Divinity.Storage;

import java.util.UUID;

public interface DivinityPlayer extends DivInfo {
	public UUID uuid();
	public boolean isOnline();
	public boolean hasLevel(ElySkill skill, int i);
	public int getLevel(ElySkill skill);
	public int getXP(ElySkill skill);
	public int getNeededXP(ElySkill skill);
	public void s(String message);
	public void err(String message);
}