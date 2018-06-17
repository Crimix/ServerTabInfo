package com.black_dog20.servertabinfo.config;


import com.black_dog20.servertabinfo.reference.Reference;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


@Config(modid = Reference.MOD_ID)
@Config.LangKey("config.servertabinfo")
public class ModConfig {
	
	@Config.LangKey("config.servertabinfo.ping")
	public static boolean ping = true;
	
	@Config.LangKey("config.servertabinfo.version")
	public static boolean version = true;

	@Config.LangKey("config.servertabinfo.playerlist")
	public static boolean playerlist = true;
	
	@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
	private static class EventHandler {

		/**
		 * Inject the new values and save to the config file when the config has been changed from the GUI.
		 *
		 * @param event The event
		 */
		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(Reference.MOD_ID)) {
				ConfigManager.sync(Reference.MOD_ID, Config.Type.INSTANCE);
			}
		}
	}

	

}
