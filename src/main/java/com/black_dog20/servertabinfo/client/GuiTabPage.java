package com.black_dog20.servertabinfo.client;

import java.util.ArrayList;
import java.util.List;

import com.black_dog20.servertabinfo.ServerTabInfo;
import com.black_dog20.servertabinfo.client.settings.Keybindings;
import com.black_dog20.servertabinfo.config.Config;
import com.black_dog20.servertabinfo.network.PacketHandler;
import com.black_dog20.servertabinfo.network.message.MessageRequest;
import com.black_dog20.servertabinfo.network.message.MessageRequestPlayerDimInfo;
import com.black_dog20.servertabinfo.reference.Constants;
import com.black_dog20.servertabinfo.utility.TpsDimension;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class GuiTabPage extends GuiScreen
{

	private Minecraft mc;
	public static int width = 0;
	private int ticks = 100;
	private int refreshTicks = 100;

	public static List<TpsDimension> dims = new ArrayList<TpsDimension>();
	public static int responseVersion = 0;
	public static int ping = 0;
	public static String serverVersion;
	public static int hight;

	private TpsPage tpsPage;
	private NotInstalledPage notInstalledPage;
	private CustomPlayerList playerList;

	public GuiTabPage()
	{
		mc = Minecraft.getInstance();
		tpsPage = new TpsPage(mc);
		notInstalledPage = new NotInstalledPage(mc);
		playerList = new CustomPlayerList(mc);
	}


	@SubscribeEvent
	public void onRenderGameOverlay(RenderGameOverlayEvent event)
	{
		width = Minecraft.getInstance().mainWindow.getScaledWidth();
		hight = Minecraft.getInstance().mainWindow.getScaledHeight();
		if (event.getType() != RenderGameOverlayEvent.ElementType.PLAYER_LIST && !ServerTabInfo.Proxy.isSinglePlayer())
		{
			return;
		}

		if (event.getType() != RenderGameOverlayEvent.ElementType.ALL && ServerTabInfo.Proxy.isSinglePlayer())
		{
			return;
		}

		if(ServerTabInfo.Proxy.isSinglePlayer() && !mc.gameSettings.keyBindPlayerList.isKeyDown()) {
			return;
		}
		
		if(serverVersion == null) {
			PacketHandler.network.sendToServer(new MessageRequest());
		}

		if ((Keybindings.SHOW.isKeyDown() || Keybindings.SHOW2.isKeyDown()))
		{	

			if(ServerTabInfo.modOnServer || ServerTabInfo.Proxy.isSinglePlayer()) {
				if(ticks%refreshTicks == 0) {
					ticks = 0;
					PacketHandler.network.sendToServer(new MessageRequest());
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
		}
		else {
			if(!ServerTabInfo.Proxy.isSinglePlayer() && Config.playerlist) {
				if(ticks%refreshTicks == 0) {
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