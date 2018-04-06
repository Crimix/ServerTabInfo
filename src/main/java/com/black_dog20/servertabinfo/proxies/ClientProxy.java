package com.black_dog20.servertabinfo.proxies;

import com.black_dog20.servertabinfo.client.GuiTabPage;

import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

	private GuiTabPage guiTabPage = new GuiTabPage();
	
	@Override
	public void registerRendersPreInit() {
		MinecraftForge.EVENT_BUS.register(guiTabPage);
	}
}
