package com.black_dog20.servertabinfo.proxies;

import com.black_dog20.servertabinfo.client.GuiTabPage;
import com.black_dog20.servertabinfo.client.settings.Keybindings;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;


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

	@Override
	public boolean isSinglePlayer() {
		return Minecraft.getMinecraft().isSingleplayer();
	}

}
