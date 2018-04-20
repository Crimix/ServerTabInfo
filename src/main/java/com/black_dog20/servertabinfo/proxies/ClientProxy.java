package com.black_dog20.servertabinfo.proxies;

import com.black_dog20.servertabinfo.client.GuiTabPage;
import com.black_dog20.servertabinfo.client.settings.Keybindings;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {

	private GuiTabPage guiTabPage = new GuiTabPage();
	
	@Override
	public void registerRendersPreInit() {
		MinecraftForge.EVENT_BUS.register(guiTabPage);
	}

	@Override
	public void registerKeyBindings() {
		ClientRegistry.registerKeyBinding(Keybindings.SHOW);
		ClientRegistry.registerKeyBinding(Keybindings.SHOW2);
		
	}

}
