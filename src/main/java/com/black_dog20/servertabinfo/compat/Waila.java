package com.black_dog20.servertabinfo.compat;

import mcp.mobius.waila.api.event.WailaRenderEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Waila {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onWailaRenderEvent(WailaRenderEvent.Pre event) {
        if(Minecraft.getInstance().gameSettings.keyBindPlayerList.isKeyDown()) {
            event.setCanceled(true);
        }
    }
}
