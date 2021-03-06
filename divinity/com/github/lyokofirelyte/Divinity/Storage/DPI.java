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
	SHARE_XP("SHARE_XP"),
	PATROL_TP_COOLDOWN("PATROL_TP_COOLDOWN"),
	PATROL_INPUT("PATROL_INPUT"),
	ALLIANCE_COLOR("ALLIANCE_COLOR"),
	RANK_NAME("RANK_NAME"),
	RANK_COLOR("RANK_COLOR"),
	RANK_DESC("RANK_DESC"),
	STAFF_COLOR("STAFF_COLOR"),
	STAFF_DESC("STAFF_DESC"),
	PLAYER_DESC("PLAYER_DESC"),
	IGNORE_XP("IGNORE_XP"),
	SOME_COOLDOWN("SOME_COOLDOWN"),
	NOTEPAD("NOTEPAD"),
	NOTEPAD_SETTING("NOTEPAD_SETTING"),
	ALLIANCE_COLOR_1("ALLIANCE_COLOR_1"),
	ALLIANCE_COLOR_2("ALLIANCE_COLOR_2"),
	ALLIANCE_NAME("ALLIANCE_NAME"),
	ELY("ELY"),
	WEALTH_LOOKUP("WEALTH_LOOKUP"),
	REPAIR_EXP("REPAIR_EXP"),
	REPAIR_TOGGLE("REPAIR_TOGGLE"),
	REPAIR_TOOLS("REPAIR_TOOLS"),
	REPAIR_INV("REPAIR_INV"),
	IN_GAME("IN_GAME"),
	TS3_CREDENTIALS("TS3_CREDENTIALS"),
	LOGGER("LOGGER"),
	REGISTERED("REGISTERED"),
	HOLO_ID("HOLO_ID"),
	LOGGER_RESULTS("LOGGER_RESULTS"),
	AFK_PLAYERS("AFK_PLAYERS"),
	DEATH_CHEST_LOC("DEATH_CHEST_LOC"),
	DEATH_CHEST_INV("DEATH_CHEST_INV"),
	DEATH_ARMOR("DEATH_ARMORS"),
	CLOSET_ITEMS("CLOSET_ITEMS"),
	SCOREBOARD("SCOREBOARD"),
	XP_DISP_NAME_TOGGLE("XP_DISP_NAME_TOGGLE"),
	FALLING_BLOCKS("FALLING_BLOCKS"),
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
	GV5("GV5"),
	CAST_PREFIX("CAST_PREFIX"),
	CALENDAR_LINK("CALENDAR_LINK"),
	ENDERDRAGON_CD("ENDERDRAGON_CD"),
	ENDERDRAGON_DEAD("ENDERDRAGON_DEAD"),
	BAN_QUEUE("BAN_QUEUE"),
	IS_BANNING("IS_BANNING"),
	PAUSED_CHAT("PAUSED_CHAT"),
	IS_DIS("IS_DIS"),
	DIS_ENTITY("DIS_ENTITY"),
	MOTD("MOTD"),
	CRAFT_COOLDOWN("CRAFT_COOLDOWN"),
	IS_STAFF_TP("IS_STAFF_TP"),
	FR_FK_COOLDOWN("FR_FK_COOLDOWN"),
	FR_FK_TOGGLE("FR_FK_TOGGLE"),
	FR_CH_COOLDOWN("FR_CH_COOLDOWN"),
	FR_CH_TOGGLE("FR_CH_TOGGLE"),
	FR_CR_COOLDOWN("FR_CR_COOLDOWN"),
	FR_CR_TOGGLE("FR_CR_TOGGLE"),
	FR_TR_COOLDOWN("FR_TR_COOLDOWN"),
	FR_TR_TOGGLE("FR_TR_TOGGLE"),
	SURVIVAL_INVENTORY("SURVIVAL_INVENTORY"),
	CREATIVE_INVENTORY("CREATIVE_INVENTORY"),
	ADVENTURE_INVENTORY("ADVENTURE_INVENTORY"),
	YES_VOTE("YES_VOTE"),
	NO_VOTE("NO_VOTE"),
	VOTE_MESSAGE("VOTE_MESSAGE"),
	MARKKIT_LOG("MARKKIT_LOG"),
	RAIN_TOGGLE("RAIN_TOGGLE");

	DPI(String info){
		this.info = info;
	}
	
	public String info;

	@DivStorageModule (types = {DivinityManager.dir, DivinityManager.sysDir})
	public String s(){
		return info;
	}
}