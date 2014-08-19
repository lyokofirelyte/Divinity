package com.github.lyokofirelyte.Divinity;

/**
 * Represents a plugin hooking into the API.
 */
public interface DivinityModule {

	/**
	 * Called when the actual module enables
	 */
	public abstract void onEnable();
	
	/**
	 * Called when the actual module disabless
	 */
	public abstract void onDisable();
	
	/**
	 * Returns the main class of the API.
	 * @return Divinity
	 */
	public abstract Divinity getApi();
	
	/**
	 * Fired when a plugin registers with the API.
	 * Also fires for all plugins when the API is enabled.
	 */
	public abstract void onRegister();
	
	/**
	 * Fired when a plugin un-registers with the API or the API is disabled.
	 */
	public abstract void onUnRegister();
}