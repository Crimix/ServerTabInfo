package com.black_dog20.servertabinfo.network;


import com.black_dog20.servertabinfo.network.message.MessageRequest;
import com.black_dog20.servertabinfo.network.message.MessageResponse;
import com.black_dog20.servertabinfo.reference.Reference;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;


public class PacketHandler {

	public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID.toLowerCase());

	public static void init() {
		network.registerMessage(MessageRequest.class, MessageRequest.class, 1, Side.SERVER);
		network.registerMessage(MessageResponse.class, MessageResponse.class, 2, Side.CLIENT);
	}

}
