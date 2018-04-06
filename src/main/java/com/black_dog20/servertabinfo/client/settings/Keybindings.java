package com.black_dog20.servertabinfo.client.settings;

import org.lwjgl.input.Keyboard;

import com.black_dog20.servertabinfo.reference.Names;

import net.minecraft.client.settings.KeyBinding;

public class Keybindings {
	
	public static KeyBinding SHOW = new KeyBinding(Names.Keys.SHOW, Keyboard.KEY_LCONTROL, Names.Keys.CATEGORY);
	public static KeyBinding SHOW2 = new KeyBinding(Names.Keys.SHOW2, Keyboard.KEY_RCONTROL, Names.Keys.CATEGORY);

}