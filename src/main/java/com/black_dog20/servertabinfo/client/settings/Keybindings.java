package com.black_dog20.servertabinfo.client.settings;


import org.lwjgl.glfw.GLFW;

import com.black_dog20.servertabinfo.reference.Names;

import net.minecraft.client.settings.KeyBinding;

public class Keybindings {
	
	public static KeyBinding SHOW = new KeyBinding(Names.Keys.SHOW, GLFW.GLFW_KEY_LEFT_CONTROL, Names.Keys.CATEGORY);
	public static KeyBinding SHOW2 = new KeyBinding(Names.Keys.SHOW2, GLFW.GLFW_KEY_RIGHT_CONTROL, Names.Keys.CATEGORY);

}
