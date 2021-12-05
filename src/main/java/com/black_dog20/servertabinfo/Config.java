package com.black_dog20.servertabinfo;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Mod.EventBusSubscriber
public class Config {

    public static final String CLIENT_SETTINGS = "client";
    public static final String CATEGORY_GENERAL = "general";
    public final static Pattern DIMENSION_NAME_OR_WILDCARD_PATTERN = Pattern.compile("^[A-z]+:([A-z]+|\\*)$");

    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec CLIENT_CONFIG;
    public static ForgeConfigSpec SERVER_CONFIG;

    public static ForgeConfigSpec.IntValue REFRESH_TICKS;
    public static ForgeConfigSpec.BooleanValue REPLACE_PLAYER_LIST;
    public static ForgeConfigSpec.BooleanValue OP_ONLY_MODE;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> DIMENSION_BLOCK_LIST;

    static {
        CLIENT_BUILDER.comment("Client settings").push(CLIENT_SETTINGS);
        REPLACE_PLAYER_LIST = CLIENT_BUILDER.comment("Replace the vanilla player list")
                .define("replacePlayerList", true);
        CLIENT_BUILDER.pop();

        CLIENT_CONFIG = CLIENT_BUILDER.build();

        SERVER_BUILDER.comment("General settings").push(CATEGORY_GENERAL);
        REFRESH_TICKS = SERVER_BUILDER.comment("How often should the server send tps updates to clients")
                .defineInRange("refreshTicks", 100, 100,  600);
        OP_ONLY_MODE = SERVER_BUILDER.comment("Only ops can see tps information, this is to stop tps hunters")
                .define("opOnlyMode", false);
        List<String> defaultBlockedDimensions = new ArrayList<>();
        defaultBlockedDimensions.add(new ResourceLocation(ServerTabInfo.MOD_ID, "example").toString());
        DIMENSION_BLOCK_LIST = SERVER_BUILDER.comment("List of dimensions not shown on the TPS list", "In the following forms \"mod:dimension\" or \"mod:*\" to block all dimension for that mod", "Examples include", " - \"minecraft:overworld\"", " - \"minecraft:the_nether\"", " - \"minecraft:the_end\"")
                .defineList("dimensionBlockList", defaultBlockedDimensions, s -> DIMENSION_NAME_OR_WILDCARD_PATTERN.matcher((String)s).matches());
        SERVER_BUILDER.pop();

        SERVER_CONFIG = SERVER_BUILDER.build();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {

        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();
        spec.setConfig(configData);
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {

    }

    @SubscribeEvent
    public static void onReload(final ModConfig.Reloading configEvent) {
    }

}
