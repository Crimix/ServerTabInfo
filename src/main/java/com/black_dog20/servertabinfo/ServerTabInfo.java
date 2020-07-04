package com.black_dog20.servertabinfo;

import com.black_dog20.servertabinfo.compat.ModCompat;
import com.black_dog20.servertabinfo.config.Config;
import com.black_dog20.servertabinfo.network.PacketHandler;
import com.black_dog20.servertabinfo.proxies.ClientProxy;
import com.black_dog20.servertabinfo.proxies.IProxy;
import com.black_dog20.servertabinfo.proxies.ServerProxy;
import com.black_dog20.servertabinfo.reference.Reference;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Reference.MOD_ID)
public class ServerTabInfo {

	public static ServerTabInfo instance;
	public static final Logger LOGGER = LogManager.getLogger();
	public static IProxy Proxy;
	public static boolean modOnServer = false;
	
	public ServerTabInfo() {
		instance = this;
        IEventBus event = FMLJavaModLoadingContext.get().getModEventBus();
        event.addListener(this::setup);
        event.addListener(this::doClientStuff);
        Proxy = DistExecutor.runForDist(()-> ClientProxy::new, ()-> ServerProxy::new);

        event.addListener(this::config);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.SPEC);
        ModCompat.register(event);

        MinecraftForge.EVENT_BUS.register(this);
    }
	
	private void setup(final FMLCommonSetupEvent event){
        // some preinit code
		PacketHandler.init();
        LOGGER.info("Pre Initialization Complete!");
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
    	Proxy.registerKeyBindings();
    	Proxy.registerRendersPreInit();
    	
    }
	
    
    public void config(ModConfig.ModConfigEvent event)
    {
        if (event.getConfig().getSpec() == Config.SPEC)
            Config.load();
    }
	
    
	/*@NetworkCheckHandler
	public boolean checkModLists(Map<String, String> modList, Side side) {
		if (side == Side.SERVER) {
			modOnServer = modList.containsKey(Reference.MOD_ID);
		}
	
		return true;
	}*/
}
