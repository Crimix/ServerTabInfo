package com.black_dog20.servertabinfo;

import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.black_dog20.servertabinfo.network.PacketHandler;
import com.black_dog20.servertabinfo.proxies.ClientProxy;
import com.black_dog20.servertabinfo.proxies.IProxy;
import com.black_dog20.servertabinfo.proxies.ServerProxy;
import com.black_dog20.servertabinfo.reference.Reference;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.logging.log4j.LogManager;

@Mod(Reference.MOD_ID)
public class ServerTabInfo {

	public static ServerTabInfo instance;
	private static final Logger LOGGER = LogManager.getLogger();
	public static IProxy Proxy;
	public static boolean modOnServer = false;
	
	public ServerTabInfo() {
		instance = this;
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        Proxy = DistExecutor.runForDist(()->()->new ClientProxy(), ()->()->new ServerProxy());
        

        MinecraftForge.EVENT_BUS.register(this);
    }
	
	private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }
	
	
	/*@NetworkCheckHandler
	public boolean checkModLists(Map<String, String> modList, Side side) {
		if (side == Side.SERVER) {
			modOnServer = modList.containsKey(Reference.MOD_ID);
		}
	
		return true;
	}*/
}
