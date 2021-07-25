package com.black_dog20.servertabinfo.client.keybinds;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

import static com.black_dog20.servertabinfo.common.utils.Translations.CATEGORY;
import static com.black_dog20.servertabinfo.common.utils.Translations.SHOW_KEY;

@OnlyIn(Dist.CLIENT)
public class Keybinds {
    public static final KeyMapping SHOW = new KeyMapping(SHOW_KEY.getDescription(),  KeyConflictContext.IN_GAME, KeyModifier.CONTROL, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_TAB, CATEGORY.getDescription());
}
