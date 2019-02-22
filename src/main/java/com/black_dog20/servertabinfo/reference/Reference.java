package com.black_dog20.servertabinfo.reference;

import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;

public class Reference {

	public static final String MOD_ID = "servertabinfo";
	public static final String MOD_NAME = "Server Tab Info";
	
	public static String getVersion() {
		ModContainer container = ModList.get().getModContainerById(MOD_ID).orElse(null);
		
		if(container != null)
			return container.getModInfo().getVersion().toString();
		
		return "@Version@";
	}
}
