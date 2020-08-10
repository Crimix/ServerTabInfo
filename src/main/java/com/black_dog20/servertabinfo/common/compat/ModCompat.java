package com.black_dog20.servertabinfo.common.compat;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;

public class ModCompat {

    public static void register(IEventBus bus) {
        if (ModList.get().isLoaded("waila")) {
            MinecraftForge.EVENT_BUS.register(new Waila());
        }
    }
}
