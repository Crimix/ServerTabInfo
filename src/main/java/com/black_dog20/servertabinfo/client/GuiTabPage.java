package com.black_dog20.servertabinfo.client;

import java.util.ArrayList;
import java.util.List;

import com.black_dog20.servertabinfo.ServerTabInfo;
import com.black_dog20.servertabinfo.client.settings.Keybindings;
import com.black_dog20.servertabinfo.config.ModConfig;
import com.black_dog20.servertabinfo.network.PacketHandler;
import com.black_dog20.servertabinfo.network.message.MessageRequest;
import com.black_dog20.servertabinfo.network.message.MessageRequestPlayerDimInfo;
import com.black_dog20.servertabinfo.reference.Constants;
import com.black_dog20.servertabinfo.utility.TpsDimension;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.RenderGameOverlayEvent;


@SideOnly(Side.CLIENT)
public class GuiTabPage extends GuiScreen
{

	private Minecraft mc;
	public static int width = 0;
	public static int hight = 0;
	private int ticks = 100;

	public static List<TpsDimension> dims = new ArrayList<TpsDimension>();
	public static int responseVersion = 0;
	public static int ping = 0;
	public static String serverVersion;

	private TpsPage tpsPage;
	private NotInstalledPage notInstalledPage;
	private CustomPlayerList playerList;

	public GuiTabPage()
	{
		mc = Minecraft.getMinecraft();
		tpsPage = new TpsPage(mc);
		notInstalledPage = new NotInstalledPage(mc);
		playerList = new CustomPlayerList(mc);
	}


	@SubscribeEvent
	public void onRenderGameOverlayServer(RenderGameOverlayEvent.Pre event)
	{
		if(!ServerTabInfo.Proxy.isSinglePlayer()){
			width = event.resolution.getScaledWidth();
			hight = event.resolution.getScaledHeight();
			if (event.type != RenderGameOverlayEvent.ElementType.PLAYER_LIST)
			{
				return;
			}
			
			if(serverVersion == null) {
				PacketHandler.network.sendToServer(new MessageRequest(Constants.VERSION));
			}

			if ((Keybindings.SHOW.getIsKeyPressed() || Keybindings.SHOW2.getIsKeyPressed()))
			{	
				if(ServerTabInfo.modOnServer || ServerTabInfo.Proxy.isSinglePlayer()) {
					if(ticks%100 == 0) {
						ticks = 0;
						PacketHandler.network.sendToServer(new MessageRequest(Constants.VERSION));
					}

					if (tpsPage.render())
					{
						ticks++;
						if(!ServerTabInfo.Proxy.isSinglePlayer())
							event.setCanceled(true);
					}
				}
				else {
					notInstalledPage.render();
					event.setCanceled(true);
				}
			} else {
				if(!ServerTabInfo.Proxy.isSinglePlayer() && ModConfig.playerlist) {
					if(ticks%100 == 0) {
						ticks = 0;
						if(responseVersion >= 3)
							PacketHandler.network.sendToServer(new MessageRequestPlayerDimInfo());
					}
					
					if (playerList.render(width))
					{
						ticks++;
						if(!ServerTabInfo.Proxy.isSinglePlayer())
							event.setCanceled(true);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onRenderGameOverlayClient(RenderGameOverlayEvent.Post event)
	{
		if(ServerTabInfo.Proxy.isSinglePlayer()){
			width = event.resolution.getScaledWidth();
			hight = event.resolution.getScaledHeight();

			if (event.type != RenderGameOverlayEvent.ElementType.ALL)
			{
				return;
			}

			if(!mc.gameSettings.keyBindPlayerList.getIsKeyPressed()) {
				return;
			}

			if (!(Keybindings.SHOW.getIsKeyPressed() || Keybindings.SHOW2.getIsKeyPressed()))
			{
				return;
			}
			
			if(ticks%100 == 0) {
				ticks = 0;
				PacketHandler.network.sendToServer(new MessageRequest(Constants.VERSION));
			}
			if (tpsPage.render())
			{
				ticks++;
				
			}
		}
	}
}