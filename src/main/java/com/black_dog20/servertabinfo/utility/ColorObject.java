package com.black_dog20.servertabinfo.utility;

import net.minecraft.util.text.TextFormatting;

public class ColorObject {
	public enum Color {Red, Green, Yellow}
	
	private Color color;
	
	public ColorObject(Color color) {
		this.color = color;
	}
	
	public TextFormatting getColor() {
		switch (color) {
		case Red:
			return TextFormatting.RED;
		case Green:
			return TextFormatting.GREEN;
		case Yellow:
			return TextFormatting.YELLOW;
		}
		return TextFormatting.BLACK;
	}
}
