package com.black_dog20.servertabinfo.utility;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CompatibilityHelper {
	
	public static String translate(String text) {
		return new ChatComponentTranslation(text).getFormattedText();
	}
	
	public static String translate(String text, ColorObject color) {
		ChatComponentTranslation temp = new ChatComponentTranslation(text);
		temp.getChatStyle().setColor(color.getColor());
		return temp.getFormattedText();
	}
	
	public static String text(String text, ColorObject color) {
		ChatComponentText temp = new ChatComponentText(text);
		temp.getChatStyle().setColor(color.getColor());
		return temp.getFormattedText();
	}
	
	public static String text(String text) {
		ChatComponentText temp = new ChatComponentText(text);
		return temp.getFormattedText();
	}
	
	public static void glPush() {
		GL11.glPushMatrix();
	}
	
	public static void glPop() {
		GL11.glPopMatrix();
	}
	
	public static void glScale(double value) {
		GL11.glScaled(value, value, value);
	}
	
	public static void glListHelper() {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(770, 771);
		GL11.glAlphaFunc(1, 0);
	}
	
	public static int getStringWidth(Minecraft mc, String text) {
		return mc.fontRendererObj.getStringWidth(text);
	}
	
	public static void drawStringWithShadow(Minecraft mc, String text, float x, float y, int color) {
		mc.fontRendererObj.drawStringWithShadow(text, (int)x, (int)y, color);
	}
	
	public static void drawStringWithShadowItalic(Minecraft mc, String text, float x, float y, int color) {
		mc.fontRendererObj.drawStringWithShadow(EnumChatFormatting.ITALIC + text, (int)x, (int)y, color);
	}
	
	public static int getFontRenderHeight(Minecraft mc) {
		return mc.fontRendererObj.FONT_HEIGHT;
	}
}
