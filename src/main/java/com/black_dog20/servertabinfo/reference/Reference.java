package com.black_dog20.servertabinfo.reference;

import net.minecraftforge.fml.ModList;

public class Reference {

	public static final String MOD_ID = "servertabinfo";
	public static final String MOD_NAME = "Server Tab Info";
	public static final String VERSION = ModList.get().getModFileById(MOD_ID).getConfig().<String>getOptional("version").orElse("@Version@");
	public static final String MC_VERSIONS = "[1.12,1.12.2]";
	public static final String DEPENDENCIES = "";
	public static final String CLIENT_PROXY_CLASS = "com.black_dog20.servertabinfo.proxies.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "com.black_dog20.servertabinfo.proxies.ServerProxy";
}
