package com.black_dog20.servertabinfo;

import com.black_dog20.servertabinfo.common.compat.ModCompat;
import com.black_dog20.servertabinfo.common.network.PacketHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ServerTabInfo.MOD_ID)
public class ServerTabInfo {

    public static final String MOD_ID = "servertabinfo";
	public static final Logger LOGGER = LogManager.getLogger();
	
	public ServerTabInfo() {
        IEventBus event = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
        Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve(MOD_ID + "-client.toml"));
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG);
        Config.loadConfig(Config.SERVER_CONFIG, FMLPaths.CONFIGDIR.get().resolve(MOD_ID + "-server.toml"));
        event.addListener(this::setup);

        ModCompat.register(event);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event){
		PacketHandler.register();
    }

    public static String getVersion() {
        ModContainer container = ModList.get().getModContainerById(MOD_ID).orElse(null);

        if(container != null)
            return container.getModInfo().getVersion().toString();

        return "@Version@";
    }
}
