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

	public GuiTabPage()
	{
		mc = Minecraft.getMinecraft();
	}


	@SubscribeEvent
	public void onRenderGameOverlay(RenderGameOverlayEvent event)
	{
		if(ServerTabInfo.modOnServer) {
			width = event.resolution.getScaledWidth();
			if (event.type!= RenderGameOverlayEvent.ElementType.PLAYER_LIST)
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
			width = event.resolution.getScaledWidth();
			if (event.type != RenderGameOverlayEvent.ElementType.PLAYER_LIST)
			{
				return;
			}
			
			if (!(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)))
			{
				return;
			}
			
			ChatComponentTranslation text = new ChatComponentTranslation("gui.servertabinfo.notinstalled");
			int textLength = mc.fontRendererObj.getStringWidth(text.getFormattedText());
			mc.fontRendererObj.drawStringWithShadow(text.getFormattedText(), (float) (width / 2 - textLength / 2), (float) 10, -1);
			
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
			EnumChatFormatting color = EnumChatFormatting.GREEN;
			int tps = (int) Math.min(1000.0D / tpsInfo.meanTickTime, 20);

			if (tps < 20)
			{
				color = EnumChatFormatting.YELLOW;
			}
			if (tps <= 10)
			{
				color = EnumChatFormatting.RED;
			}

			ChatComponentText tpsString = new ChatComponentText(Integer.toString(tps));
			ChatComponentTranslation mean = new ChatComponentTranslation("gui.servertabinfo.mean");
			ChatComponentTranslation dim = new ChatComponentTranslation("gui.servertabinfo.dim");
			ChatComponentTranslation name = new ChatComponentTranslation(dim.getFormattedText() + " " +Integer.toString(tpsInfo.Id));
			if(!tpsInfo.name.equals(""))
				name = new ChatComponentTranslation(tpsInfo.name);
			tpsString.getChatStyle().setColor(color);
			list.add(String.format("%s: %s %.2f%s (%s %s)", name.getFormattedText(), mean.getFormattedText(), tpsInfo.meanTickTime, "ms", tpsString.getFormattedText(), "tps" ));

		}

		int startTop = 10;

		for (String tpsInfoString : list)
		{
			if(tpsInfoString != null) {
				int k = mc.fontRendererObj.getStringWidth(tpsInfoString);
				maxWidth = Math.max(maxWidth, k);
			}
		}

		maxWidth = (int) (maxWidth*1.5);


		if (list != null && !list.isEmpty())
		{

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
		}

		return true;
	}

}