package com.black_dog20.servertabinfo.config;


import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;


public class Config {
	
	
	public static final ClientConfig CONFIG;
    public static final ForgeConfigSpec SPEC;
    static {
        final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        SPEC = specPair.getRight();
        CONFIG = specPair.getLeft();
    }

	public static boolean ping = true;
	public static boolean version = true;
	public static boolean playerlist = true;

    public static void load()
    {
        ping = CONFIG.ping.get();
        version = CONFIG.version.get();
        playerlist = CONFIG.playerlist.get();
    }

    public static class ClientConfig
    {
        public ForgeConfigSpec.BooleanValue ping;
        public ForgeConfigSpec.BooleanValue version;
        public ForgeConfigSpec.BooleanValue playerlist;

        ClientConfig(ForgeConfigSpec.Builder builder) {
            builder.push("Client");
            ping = builder
                    .comment("Show ping box on TPS page")
                    .translation("config.servertabinfo.ping")
                    .define("ping", true);
            version =builder
                    .comment("Show version box on TPS page")
                    .translation("config.servertabinfo.version")
                    .define("version", true);
            playerlist = builder
                    .comment("Replace the vanilla player list / tab page")
                    .translation("config.servertabinfo.playerlist")
                    .define("playerlist", true);
            builder.pop();
        }
    }

}
