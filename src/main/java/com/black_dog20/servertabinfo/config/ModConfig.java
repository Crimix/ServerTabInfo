package com.black_dog20.servertabinfo.config;

import java.io.File;

import com.black_dog20.servertabinfo.reference.Reference;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModConfig {

	public static Configuration configuration;
	public static boolean ping = true;
	public static boolean version = true;
	public static boolean playerlist  = true;

	public static void init(File configFile) {

		// Create configuration object from the given configurations file
		if (configuration == null) {
			configuration = new Configuration(configFile);
			loadConfiguration();
		}
	}

	@SubscribeEvent
	public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {

		if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
			loadConfiguration();
		}
	}

	public static void loadConfiguration() {

		ping = configuration.getBoolean("Show ping", Configuration.CATEGORY_GENERAL, true,"");
		version = configuration.getBoolean("Show version box", Configuration.CATEGORY_GENERAL, true, "");
		playerlist = configuration.getBoolean("Replace player list", Configuration.CATEGORY_GENERAL, true, "");

		if (configuration.hasChanged()) {
			configuration.save();
		}
	}
}
