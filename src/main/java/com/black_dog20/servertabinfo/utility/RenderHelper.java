package com.black_dog20.servertabinfo.utility;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

public class RenderHelper {

	
	
	public static int RenderList(List<String> list, Minecraft mc, int maxWidth, int startTop, int width) {

		if (list != null && !list.isEmpty())
		{

			GuiScreen.drawRect(width / 2 - maxWidth / 2 - 1, startTop - 1, width / 2 + maxWidth / 2 + 1, startTop + list.size() * mc.fontRenderer.FONT_HEIGHT, Integer.MIN_VALUE);

			for (String string : list)
			{

				GuiScreen.drawRect(width / 2 - maxWidth / 2, startTop, width / 2 + maxWidth / 2, startTop+8, 553648127);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				GlStateManager.enableAlpha();
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

				int i2 = mc.fontRenderer.getStringWidth(string);
				mc.fontRenderer.drawStringWithShadow(string, (float) (width / 2 - i2 / 2), (float) startTop, -1);
				startTop += mc.fontRenderer.FONT_HEIGHT;
			}
		}
		return startTop;
	}
	
	public static int RenderListAtStartPoint(List<String> list, Minecraft mc, int maxWidth, int startTop, int startLeft) {
		if (list != null && !list.isEmpty())
		{
			GuiScreen.drawRect(startLeft , startTop - 1, startLeft+maxWidth-1, startTop + list.size() * mc.fontRenderer.FONT_HEIGHT, Integer.MIN_VALUE);
			
			for (String string : list)
			{
				GuiScreen.drawRect(startLeft+1, startTop, startLeft+maxWidth-2, startTop+8, 553648127);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				GlStateManager.enableAlpha();
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

				mc.fontRenderer.drawStringWithShadow(string, (float) startLeft+2, (float) startTop, -1);
				startTop += mc.fontRenderer.FONT_HEIGHT;
			}
		}
		return startTop;
	}
}
