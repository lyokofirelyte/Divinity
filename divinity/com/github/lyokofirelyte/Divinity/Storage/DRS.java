package com.github.lyokofirelyte.Divinity.Storage;

import com.github.lyokofirelyte.Divinity.Manager.DivinityManager;

public enum DRS {

	CENTER("CENTER"),
	DEST("DEST"),
	MAT_ID("MAT_ID"),
	BYTE_ID("BYTE_ID");
	
	DRS(String s){
		this.s = s;
	}
	
	public String s;
	
	@DivStorageModule (types = {DivinityManager.ringsDir})
	public String s(){
		return s;
	}	
}