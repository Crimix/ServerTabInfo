package com.black_dog20.servertabinfo.utility;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
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
		GlStateManager.pushMatrix();
	}
	
	public static void glPop() {
		GlStateManager.popMatrix();
	}
	
	public static void glScale(double value) {
		GlStateManager.scale(value, value, value);
	}
	
	public static void glListHelper() {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
	}
	
	public static int getStringWidth(Minecraft mc, String text) {
		return mc.fontRendererObj.getStringWidth(text);
	}
	
	public static void drawStringWithShadow(Minecraft mc, String text, float x, float y, int color) {
		mc.fontRendererObj.drawStringWithShadow(text, x, y, color);
	}
	
	public static void drawStringWithShadowItalic(Minecraft mc, String text, float x, float y, int color) {
		mc.fontRendererObj.drawStringWithShadow(EnumChatFormatting.ITALIC + text, x, y, color);
	}
	
	public static int getFontRenderHeight(Minecraft mc) {
		return mc.fontRendererObj.FONT_HEIGHT;
	}
}
