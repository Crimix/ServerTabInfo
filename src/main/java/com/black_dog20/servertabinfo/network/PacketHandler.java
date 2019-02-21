package com.black_dog20.servertabinfo.network;


import com.black_dog20.servertabinfo.ServerTabInfo;
import com.black_dog20.servertabinfo.network.message.MessageRequest;
import com.black_dog20.servertabinfo.network.message.MessageRequestPlayerDimInfo;
import com.black_dog20.servertabinfo.network.message.MessageResponsePlayerDimInfo;
import com.black_dog20.servertabinfo.network.message.MessageResponseServerInfo;
import com.black_dog20.servertabinfo.reference.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;


public class PacketHandler {

	private static ResourceLocation net = new ResourceLocation(Reference.MOD_ID,"net");
	public static SimpleChannel network;

	public static void init() {
		network = NetworkRegistry.ChannelBuilder.named(net).
        clientAcceptedVersions(s -> checkRemoteVersion(s)).
        serverAcceptedVersions(s -> true).
        networkProtocolVersion(() -> "1").
		simpleChannel();
		
		network.messageBuilder(MessageRequest.class, 1).
		decoder(MessageRequest::fromBytes).
		encoder(MessageRequest::toBytes).
		consumer(MessageRequest::onMessage).
		add();
		
		network.messageBuilder(MessageResponseServerInfo.class, 2).
		decoder(MessageResponseServerInfo::fromBytes).
		encoder(MessageResponseServerInfo::toBytes).
		consumer(MessageResponseServerInfo::onMessage).
		add();
		
		network.messageBuilder(MessageRequestPlayerDimInfo.class, 3).
		decoder(MessageRequestPlayerDimInfo::fromBytes).
		encoder(MessageRequestPlayerDimInfo::toBytes).
		consumer(MessageRequestPlayerDimInfo::onMessage).
		add();
		
		network.messageBuilder(MessageResponsePlayerDimInfo.class, 4).
		decoder(MessageResponsePlayerDimInfo::fromBytes).
		encoder(MessageResponsePlayerDimInfo::toBytes).
		consumer(MessageResponsePlayerDimInfo::onMessage).
		add();
		
	}
	
	private static boolean checkRemoteVersion(String s) {
		if(s.equals(NetworkRegistry.ABSENT))
			ServerTabInfo.modOnServer = false;
		else
			ServerTabInfo.modOnServer = true;
		return true;
	}

}
