package com.black_dog20.servertabinfo.common.compat;

import com.black_dog20.servertabinfo.client.keybinds.Keybinds;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Rect2i;
import snownee.jade.api.Accessor;
import snownee.jade.api.ITooltip;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;
import snownee.jade.api.callback.JadeBeforeRenderCallback;

@WailaPlugin
public class Jade implements IWailaPlugin {

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.addBeforeRenderCallback(this::beforeRender);
    }

    private boolean beforeRender(ITooltip var1, Rect2i var2, PoseStack var3, Accessor<?> var4, JadeBeforeRenderCallback.ColorSetting var5) {
        return Minecraft.getInstance().options.keyPlayerList.isDown() && !Minecraft.getInstance().hasSingleplayerServer() || Keybinds.SHOW.isDown();
    }
}
