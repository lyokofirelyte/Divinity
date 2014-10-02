package com.github.lyokofirelyte.Divinity.Storage;

import com.github.lyokofirelyte.Divinity.Divinity;
import com.github.lyokofirelyte.Divinity.Manager.DivinityManager;

public class DivinityGame extends DivinityStorage {

	public DivinityGame(String gameType, String n, Divinity i) {
		super(gameType, n, i);
	}
	
	public String getFullPath(){
		return DivinityManager.gamesDir + gameName() + "/";
	}
	
	public String getAppenededPath(){
		return gameName() + "/";
	}
}