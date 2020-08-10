package com.black_dog20.servertabinfo.client.keybinds;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

import static com.black_dog20.servertabinfo.common.utils.Translations.*;

@OnlyIn(Dist.CLIENT)
public class Keybinds {
    public static final KeyBinding SHOW = new KeyBinding(SHOW_KEY.getDescription(),  KeyConflictContext.IN_GAME, KeyModifier.CONTROL, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_TAB, CATEGORY.getDescription());
}
