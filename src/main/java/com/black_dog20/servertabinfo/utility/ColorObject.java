package com.black_dog20.servertabinfo.utility;

import net.minecraft.util.EnumChatFormatting;

public class ColorObject {
	public enum Color {Red, Green, Yellow}
	
	private Color color;
	
	public ColorObject(Color color) {
		this.color = color;
	}
	
	public EnumChatFormatting getColor() {
		switch (color) {
		case Red:
			return EnumChatFormatting.RED;
		case Green:
			return EnumChatFormatting.GREEN;
		case Yellow:
			return EnumChatFormatting.YELLOW;
		}
		return EnumChatFormatting.BLACK;
	}
}
