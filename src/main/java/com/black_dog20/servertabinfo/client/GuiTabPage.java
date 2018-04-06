package com.black_dog20.servertabinfo.client;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.black_dog20.servertabinfo.ServerTabInfo;
import com.black_dog20.servertabinfo.network.PacketHandler;
import com.black_dog20.servertabinfo.network.message.MessageRequest;
import com.black_dog20.servertabinfo.utility.TpsDimension;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiTabPage extends GuiScreen
{

	private Minecraft mc;
	private int width = super.width;
	private int ticks = 100;

	public static List<TpsDimension> dims = new ArrayList<TpsDimension>();

	public GuiTabPage()
	{
		mc = Minecraft.getMinecraft();
	}


	@SubscribeEvent
	public void onRenderGameOverlay(RenderGameOverlayEvent event)
	{
		if(ServerTabInfo.modOnServer) {
			width = event.getResolution().getScaledWidth();
			if (event.getType() != RenderGameOverlayEvent.ElementType.PLAYER_LIST)
			{
				return;
			}
			if (!(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)))
			{
				return;
			}
			if(ticks%100 == 0) {
				ticks = 0;
				PacketHandler.network.sendToServer(new MessageRequest());
			}

			if (renderServerInfo())
			{
				ticks++;
				event.setCanceled(true);
			}
		}
		else {
			width = event.getResolution().getScaledWidth();
			if (event.getType() != RenderGameOverlayEvent.ElementType.PLAYER_LIST)
			{
				return;
			}
			
			if (!(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)))
			{
				return;
			}
			
			TextComponentTranslation text = new TextComponentTranslation("gui.servertabinfo.notinstalled");
			int textLength = mc.fontRenderer.getStringWidth(text.getFormattedText());
			mc.fontRenderer.drawStringWithShadow(text.getFormattedText(), (float) (width / 2 - textLength / 2), (float) 10, -1);
			
			event.setCanceled(true);
		}
	}

	public boolean renderServerInfo()
	{

		int maxWidth = 0;
		List<String> list = new ArrayList<>();

		if(dims==null && dims.isEmpty())
			return true;
		
		for(TpsDimension tpsInfo : dims) {
			TextFormatting color = TextFormatting.GREEN;
			int tps = (int) Math.min(1000.0D / tpsInfo.meanTickTime, 20);

			if (tps < 20)
			{
				color = TextFormatting.YELLOW;
			}
			if (tps <= 10)
			{
				color = TextFormatting.RED;
			}

			TextComponentString tpsString = new TextComponentString(Integer.toString(tps));
			TextComponentTranslation mean = new TextComponentTranslation("gui.servertabinfo.mean");
			TextComponentTranslation name = new TextComponentTranslation(tpsInfo.name);
			tpsString.getStyle().setColor(color);
			list.add(String.format("%s: %s %.2f%s (%s %s)", name.getFormattedText(), mean.getFormattedText(), tpsInfo.meanTickTime, "ms", tpsString.getFormattedText(), "tps" ));

		}

		int startTop = 10;

		for (String tpsInfoString : list)
		{
			if(tpsInfoString != null) {
				int k = mc.fontRenderer.getStringWidth(tpsInfoString);
				maxWidth = Math.max(maxWidth, k);
			}
		}

		maxWidth = (int) (maxWidth*1.5);


		if (list != null && !list.isEmpty())
		{

			drawRect(width / 2 - maxWidth / 2 - 1, startTop - 1, width / 2 + maxWidth / 2 + 1, startTop + list.size() * mc.fontRenderer.FONT_HEIGHT, Integer.MIN_VALUE);

			for (String string : list)
			{

				drawRect(width / 2 - maxWidth / 2, startTop, width / 2 + maxWidth / 2, startTop+8, 553648127);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				GlStateManager.enableAlpha();
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

				int i2 = mc.fontRenderer.getStringWidth(string);
				mc.fontRenderer.drawStringWithShadow(string, (float) (width / 2 - i2 / 2), (float) startTop, -1);
				startTop += mc.fontRenderer.FONT_HEIGHT;
			}
		}

		return true;
	}

}