package com.github.lyokofirelyte.Divinity.Storage;

import com.github.lyokofirelyte.Divinity.Divinity;
import com.github.lyokofirelyte.Divinity.Manager.DivinityManager;

public class DivinityAlliance extends DivinityStorage {
	
	public DivinityAlliance(String n, Divinity i) {
		super(n, i);
	}

	public boolean exists(){
		return api.divManager.getMap(DivinityManager.allianceDir).containsKey(name());
	}
}