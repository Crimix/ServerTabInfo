package com.black_dog20.servertabinfo.config;


import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeConfigSpec;


//@Config(modid = Reference.MOD_ID)
//@Config.LangKey("config.servertabinfo")
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
                    .comment("TileEntities to allow regardless of the blacklist")
                    .translation("config.servertabinfo.ping")
                    .define("ping", true);
            version =builder
                    .comment("TileEntities to allow regardless of the blacklist")
                    .translation("config.servertabinfo.version")
                    .define("ping", true);
            playerlist = builder
                    .comment("TileEntities to allow regardless of the blacklist")
                    .translation("config.servertabinfo.playerlist")
                    .define("ping", true);
            builder.pop();
        }
    }

}
