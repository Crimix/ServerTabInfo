package com.black_dog20.servertabinfo;

import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.black_dog20.servertabinfo.network.PacketHandler;
import com.black_dog20.servertabinfo.proxies.IProxy;
import com.black_dog20.servertabinfo.reference.Reference;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, acceptableRemoteVersions = "*", acceptedMinecraftVersions = Reference.MC_VERSIONS, dependencies = Reference.DEPENDENCIES)
public class ServerTabInfo {

	@Mod.Instance(Reference.MOD_ID)
	public static ServerTabInfo instance = new ServerTabInfo();
	public static Logger logger;
	public static boolean modOnServer = false;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static IProxy Proxy;
	
	@NetworkCheckHandler
	public boolean checkModLists(Map<String, String> modList, Side side) {
		if (side == Side.SERVER) {
			modOnServer = modList.containsKey(Reference.MOD_ID);
		}
	
		return true;
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		PacketHandler.init();
		Proxy.registerRendersPreInit();
		logger.info("Pre Initialization Complete!");
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {

		logger.info("Initialization Complete!");
}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		logger.info("Post Initialization Complete!");
	}
}
