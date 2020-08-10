package com.black_dog20.servertabinfo.client;

import com.black_dog20.servertabinfo.common.utils.Dimension;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ClientDataManager {
    public static boolean modOnServer = false;

    public static List<Dimension> DIMENSIONS = new LinkedList<>();
    public static Map<UUID, ResourceLocation> PLAYER_DIMENSIONS = new HashMap<>();

    public static Map<ResourceLocation, TextComponent> DIMENSION_NAME_CACHE = new HashMap<>();
}
