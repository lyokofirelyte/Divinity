package com.github.lyokofirelyte.Divinity.JSON;

public enum JSONChatFormat {
	
    BOLD("bold"),
    UNDERLINED("underlined"),
    STRIKETHROUGH("strikethrough"),
    ITALIC("italic"),
    OBFUSCATED("obfuscated");
    
    private final String format;

    JSONChatFormat(String format) {
        this.format = format;
    }

    public String getFormatString() {
        return format;
    }
}