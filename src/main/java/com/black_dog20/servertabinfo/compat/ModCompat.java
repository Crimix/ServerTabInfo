package com.black_dog20.servertabinfo.compat;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;

public class ModCompat {

    public static void register(IEventBus bus) {
        if (ModList.get().isLoaded("waila")) {
            //MinecraftForge.EVENT_BUS.register(new Waila());
        }
    }
}
