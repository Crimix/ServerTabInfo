package com.black_dog20.servertabinfo.proxies;

public interface IProxy {
	
	void registerRendersPreInit();

	void registerKeyBindings();
	
	boolean isSinglePlayer();

}
