package com.black_dog20.servertabinfo.client;

import java.util.ArrayList;
import java.util.List;

import com.black_dog20.servertabinfo.ServerTabInfo;
import com.black_dog20.servertabinfo.client.settings.Keybindings;
import com.black_dog20.servertabinfo.network.PacketHandler;
import com.black_dog20.servertabinfo.network.message.MessageRequest;
import com.black_dog20.servertabinfo.reference.Constants;
import com.black_dog20.servertabinfo.reference.Reference;
import com.black_dog20.servertabinfo.utility.TpsDimension;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
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
	public static int responseVersion = 0;
	public static int ping = 0;
	public static String serverVersion;
	public GuiTabPage()
	{
		mc = Minecraft.getMinecraft();
	}


	@SubscribeEvent
	public void onRenderGameOverlayServer(RenderGameOverlayEvent.Pre event)
	{
		if(!ServerTabInfo.Proxy.isSinglePlayer()){
			width = event.resolution.getScaledWidth();
			if (event.type != RenderGameOverlayEvent.ElementType.PLAYER_LIST)
			{
				return;
			}

			if (!(Keybindings.SHOW.isKeyDown() || Keybindings.SHOW2.isKeyDown()))
			{
				return;
			}
			if(ServerTabInfo.modOnServer) {
				if(ticks%100 == 0) {
					ticks = 0;
					PacketHandler.network.sendToServer(new MessageRequest(Constants.VERSION));
				}

				if (renderServerInfo())
				{
					ticks++;
					event.setCanceled(true);
				}
			}
			else {

				ChatComponentTranslation text = new ChatComponentTranslation("gui.servertabinfo.notinstalled");
				int startTop = 10;
				int maxWidth = mc.fontRendererObj.getStringWidth(text.getFormattedText());
				GlStateManager.pushMatrix();
				maxWidth = (int) (maxWidth*1.3);

				drawRect(width / 2 - maxWidth / 2 - 1, startTop - 1, width / 2 + maxWidth / 2 + 1, startTop + 1 * mc.fontRendererObj.FONT_HEIGHT, Integer.MIN_VALUE);

				drawRect(width / 2 - maxWidth / 2, startTop, width / 2 + maxWidth / 2, startTop+8, 553648127);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				GlStateManager.enableAlpha();
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
				
				int i2 = mc.fontRendererObj.getStringWidth(text.getFormattedText());
				mc.fontRendererObj.drawStringWithShadow(text.getFormattedText(), (float) (width / 2 - i2 / 2), (float) startTop, -1);
				event.setCanceled(true);
				GlStateManager.popMatrix();
				
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onRenderGameOverlayClient(RenderGameOverlayEvent.Post event)
	{
		if(ServerTabInfo.Proxy.isSinglePlayer()){
			width = event.resolution.getScaledWidth();

			if (event.type != RenderGameOverlayEvent.ElementType.ALL)
			{
				return;
			}

			if(!mc.gameSettings.keyBindPlayerList.isKeyDown()) {
				return;
			}

			if (!(Keybindings.SHOW.isKeyDown() || Keybindings.SHOW2.isKeyDown()))
			{
				return;
			}
			if(ticks%100 == 0) {
				ticks = 0;
				PacketHandler.network.sendToServer(new MessageRequest(Constants.VERSION));
			}

			if (renderServerInfo())
			{
				ticks++;
			}
		}
	}

	public boolean renderServerInfo()
	{
		int startTop = 10;

		renderClientServerVersion();

		startTop = renderPing(startTop);

		renderTps(startTop);

		return true;
	}


	private int renderTps(int startTop) {
		int maxWidth = 0;
		List<String> list = new ArrayList<>();

		if(dims==null || dims.isEmpty())
			return startTop;

		for(TpsDimension tpsInfo : dims) {
			EnumChatFormatting color = EnumChatFormatting.GREEN;
			int tps;
			if(responseVersion >= 2) {
				tps = (int) Math.min(1000.0D / (tpsInfo.meanTickTime*1.0E-006D), 20);
			}
			else {
				tps = (int) Math.min(1000.0D / tpsInfo.meanTickTime, 20);
			}

			if (tps < 20)
			{
				color = EnumChatFormatting.YELLOW;
			}
			if (tps <= 10)
			{
				color = EnumChatFormatting.RED;
			}


			ChatComponentText tpsValue = new ChatComponentText(Integer.toString(tps));
			ChatComponentTranslation mean = new ChatComponentTranslation("gui.servertabinfo.mean");
			ChatComponentTranslation dim = new ChatComponentTranslation("gui.servertabinfo.dim");
			ChatComponentTranslation ms = new ChatComponentTranslation("gui.servertabinfo.ms");
			ChatComponentTranslation tpsText = new ChatComponentTranslation("gui.servertabinfo.tps");
			ChatComponentTranslation name = new ChatComponentTranslation(dim.getFormattedText() + " " +Integer.toString(tpsInfo.Id));
			if(!tpsInfo.name.equals("")) {
				name = new ChatComponentTranslation(tpsInfo.name);

				if(name.getFormattedText().equals(tpsInfo.name+"§r")) {
					ChatComponentTranslation nameC = new ChatComponentTranslation("servertabinfo.dim." + tpsInfo.name);
					if(!nameC.getFormattedText().equals("servertabinfo.dim." + tpsInfo.name+"§r")) {
						name = nameC;
					}
				}
			}
			tpsValue.getChatStyle().setColor(color);
			if(responseVersion >= 2) {
				list.add(String.format("%s: %s %.2f%s (%s %s)", name.getFormattedText(), mean.getFormattedText(), (tpsInfo.meanTickTime*1.0E-006D), ms.getFormattedText(), tpsValue.getFormattedText(), tpsText.getFormattedText() ));
			} else {
				list.add(String.format("%s: %s %.2f%s (%s %s)", name.getFormattedText(), mean.getFormattedText(), tpsInfo.meanTickTime, ms.getFormattedText(), tpsValue.getFormattedText(), tpsText.getFormattedText() ));
			}
		}


		for (String tpsInfoString : list)
		{
			if(tpsInfoString != null) {
				int k = mc.fontRendererObj.getStringWidth(tpsInfoString);
				maxWidth = Math.max(maxWidth, k);
			}
		}

		maxWidth = (int) (maxWidth*1.3);


		if (list != null && !list.isEmpty())
		{
			GlStateManager.pushMatrix();
			drawRect(width / 2 - maxWidth / 2 - 1, startTop - 1, width / 2 + maxWidth / 2 + 1, startTop + list.size() * mc.fontRendererObj.FONT_HEIGHT, Integer.MIN_VALUE);

			for (String string : list)
			{

				drawRect(width / 2 - maxWidth / 2, startTop, width / 2 + maxWidth / 2, startTop+8, 553648127);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				GlStateManager.enableAlpha();
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

				int i2 = mc.fontRendererObj.getStringWidth(string);
				mc.fontRendererObj.drawStringWithShadow(string, (float) (width / 2 - i2 / 2), (float) startTop, -1);
				startTop += mc.fontRendererObj.FONT_HEIGHT;
			}
			GlStateManager.popMatrix();
		}
		return startTop;
	}

	private int renderPing(int startTop) {
		if(responseVersion >= 2 && !ServerTabInfo.Proxy.isSinglePlayer()) {
			ChatComponentTranslation pingText = new ChatComponentTranslation("gui.servertabinfo.ping");
			ChatComponentText pingValue = new ChatComponentText(Integer.toString(ping));
			ChatComponentTranslation ms = new ChatComponentTranslation("gui.servertabinfo.ms");

			String pingString = String.format("%s: %s%s", pingText.getFormattedText(), pingValue.getFormattedText(), ms.getFormattedText());

			int maxWidth = mc.fontRendererObj.getStringWidth(pingString);

			maxWidth = (int) (maxWidth*1.3);
			
			GlStateManager.pushMatrix();

			drawRect(width / 2 - maxWidth / 2 - 1, startTop - 1, width / 2 + maxWidth / 2 + 1, startTop + 1 * mc.fontRendererObj.FONT_HEIGHT, Integer.MIN_VALUE);

			drawRect(width / 2 - maxWidth / 2, startTop, width / 2 + maxWidth / 2, startTop+8, 553648127);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableAlpha();
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			
			int i2 = mc.fontRendererObj.getStringWidth(pingString);
			mc.fontRendererObj.drawStringWithShadow(pingString, (float) (width / 2 - i2 / 2), (float) startTop, -1);
			startTop += mc.fontRendererObj.FONT_HEIGHT;

			startTop += 10;
			GlStateManager.popMatrix();
		}
		return startTop;
	}


	private void renderClientServerVersion() {
		int startTopp = 1;
		String cv = "C" + ": " + Reference.VERSION;
		String sv = "S" + ": " + (serverVersion != null ? serverVersion : "1.0.0");
		GlStateManager.pushMatrix();
		if(this.mc.gameSettings.guiScale!=1)
			GlStateManager.scale(0.5, 0.5, 0.5);
		int maxWidth = mc.fontRendererObj.getStringWidth(sv);

		maxWidth+=6;

		drawRect(0 , startTopp - 1, maxWidth-1, startTopp + 1 * mc.fontRendererObj.FONT_HEIGHT, Integer.MIN_VALUE);
		if(!ServerTabInfo.Proxy.isSinglePlayer())
			drawRect(0 , startTopp+10 - 1, maxWidth-1, startTopp+10+ 1 * mc.fontRendererObj.FONT_HEIGHT, Integer.MIN_VALUE);

		drawRect(1, startTopp, maxWidth-2, startTopp+8, 553648127);
		if(!ServerTabInfo.Proxy.isSinglePlayer())
			drawRect(1, startTopp+9, maxWidth-2, startTopp+18, 553648127);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		mc.fontRendererObj.drawStringWithShadow(cv, (float) 2, (float) startTopp, -1);
		if(!ServerTabInfo.Proxy.isSinglePlayer())
			mc.fontRendererObj.drawStringWithShadow(sv, (float) 2, (float) startTopp+10, -1);
		GlStateManager.popMatrix();
	}

}