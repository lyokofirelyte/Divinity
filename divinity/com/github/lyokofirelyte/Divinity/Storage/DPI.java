package com.github.lyokofirelyte.Divinity.Storage;

import com.github.lyokofirelyte.Divinity.Manager.DivinityManager;

public enum DPI {
	
	BALANCE("BALANCE"),
	HOME("HOME"),
	LAST_LOGIN("LAST_LOGIN"),
	LAST_LOGOUT("LAST_LOGOUT"),
	LOGOUT_LOCATION("LOGOUT_LOCATION"),
	PERMS("PERMS"),
	DISPLAY_NAME("DISPLAY_NAME"),
	JOIN_MESSAGE("JOIN_MESSAGE"),
	QUIT_MESSAGE("QUIT_MESSAGE"),
	GLOBAL_COLOR("GLOBAL_COLOR"),
	PM_COLOR("PM_COLOR"),
	ALLIANCE_COLOR("ALLIANCE_COLOR"),
	RANK_NAME("RANK_NAME"),
	RANK_COLOR("RANK_COLOR"),
	RANK_DESC("RANK_DESC"),
	STAFF_COLOR("STAFF_COLOR"),
	STAFF_DESC("STAFF_DESC"),
	PLAYER_DESC("PLAYER_DESC"),
	ALLIANCE_COLOR_1("ALLIANCE_COLOR_1"),
	ALLIANCE_COLOR_2("ALLIANCE_COLOR_2"),
	ALLIANCE_NAME("ALLIANCE_NAME"),
	CAST_PREFIX("CAST_PREFIX"),
	ELY("ELY"),
	LOGGER("LOGGER"),
	CALENDAR_LINK("CALENDAR_LINK"),
	LOGGER_RESULTS("LOGGER_RESULTS"),
	AFK_PLAYERS("AFK_PLAYERS"),
	DEATH_CHEST_LOC("DEATH_CHEST_LOC"),
	DEATH_CHEST_INV("DEATH_CHEST_INV"),
	DEATH_ARMOR("DEATH_ARMORS"),
	SCOREBOARD("SCOREBOARD"),
	AFK_TIME("AFK_TIME"),
	AFK_TIME_INIT("AFK_TIME_INIT"),
	PATROL_LEVEL("PATROL_LEVEL"),
	SPECTATING("SPECTATING"),
	SPECTATE_TARGET("SPECTATE_TARGET"),
	EXP("EXP"),
	VISUAL("VISUAL"),
	ALLIANCE_TOGGLE("ALLIANCE_TOGGLE"),
	SCOREBOARD_TOGGLE("SCOREBOARD_TOGGLE"),
	POKES_TOGGLE("POKES_TOGGLE"),
	PVP_TOGGLE("PVP_TOGGLE"),
	FIREWORKS_TOGGLE("FIREWORKS_TOGGLE"),
	DEATHLOCS_TOGGLE("DEATHLOCS_TOGGLE"),
	PARTICLES_TOGGLE("PARTICLES_TOGGLE"),
	CHAT_FILTER_TOGGLE("CHAT_FILTER"),
	MUTED("MUTED"),
	MUTE_TIME("MUTE_TIME"),
	OWNED_CHESTS("OWNED_CHESTS"),
	BACKUP_INVENTORY("BACKUP_INVENTORY"),
	CHEST_MODE("CHEST_MODE"),
	CHEST_NAMES("CHEST_NAMES"),
	ROLLBACK_IN_PROGRESS("ROLLBACK_IN_PROGRESS"),
	ANNOUNCER("ANNOUNCER"),
	MAIL("MAIL"),
	PREVIOUS_LOCATIONS("PREVIOUS_LOCATIONS"),
	TP_INVITE("TP_INVITE"),
	TP_BLOCK("TP_BLOCK"),
	VANISHED("VANISHED"),
	ALERT_QUEUE("ALERT_QUEUE"),
	ALLIANCE_INVITE("ALLIANCE_INVITE"),
	ALLIANCE_LEADER("ALLIANCE_LEADER"),
	ANNOUNCER_INDEX("ANNOUNCER_INDEX"),
	PREVIOUS_PM("PREVIOUS_PM"),
	FIREWORK_COOLDOWN("FIREWORK_COOLDOWN"),
	DISABLED("DISABLED"),
	DISABLE_TIME("DISABLE_TIME"),
	MOB_MONEY("MOB_MONEY"),
	FILTER("FILTER"),
	IN_COMBAT("IN_COMBAT"),
	DUEL_PARTNER("DUEL_PARTNER"),
	IS_DUEL_SAFE("IS_DUEL_SAFE"),
	DUEL_WINS("DUEL_WINS"),
	EXP_DEPOSIT("EXP_DEPOSIT"),
	DUEL_INVITE("DUEL_INVITE"),
	RING_LOCS("RING_LOCS"),
	GV1("GV1"),
	GV2("GV2"),
	GV3("GV3"),
	GV4("GV4"),
	GV5("GV5");

	DPI(String info){
		this.info = info;
	}
	
	public String info;

	@DivStorageModule (types = {DivinityManager.dir, DivinityManager.sysDir})
	public String s(){
		return info;
	}
}