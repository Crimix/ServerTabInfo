package com.black_dog20.servertabinfo.client;

import com.black_dog20.servertabinfo.common.utils.Dimension;
import net.minecraft.network.chat.BaseComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.*;

public class ClientDataManager {
    public static boolean modOnServer = false;

    public static List<Dimension> DIMENSIONS = new LinkedList<>();
    public static Map<UUID, ResourceLocation> PLAYER_DIMENSIONS = new HashMap<>();

    public static Map<ResourceLocation, BaseComponent> DIMENSION_NAME_CACHE = new HashMap<>();
}
