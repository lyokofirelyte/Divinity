package com.github.lyokofirelyte.Divinity.Storage;

import java.util.UUID;

public interface DivinityPlayer extends DivInfo {
	public UUID uuid();
	public boolean isOnline();
	public void s(String message);
	public void err(String message);
}