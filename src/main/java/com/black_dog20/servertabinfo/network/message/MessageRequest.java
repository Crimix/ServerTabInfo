package com.black_dog20.servertabinfo.network.message;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.black_dog20.servertabinfo.network.PacketHandler;
import com.black_dog20.servertabinfo.reference.Constants;
import com.black_dog20.servertabinfo.utility.Helper;
import com.black_dog20.servertabinfo.utility.TpsDimension;

import io.netty.buffer.ByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;


public class MessageRequest {

	public static void onMessage(MessageRequest message, Supplier<NetworkEvent.Context> context) {

		List<TpsDimension> dims = new ArrayList<TpsDimension>();
		MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
		int ping = context.get().getSender().ping;
	
		dims.add(new TpsDimension("gui.servertabinfo.overall" , Helper.mean(server.tickTimeArray),Constants.VERSION));
		
		for(WorldServer world : server.forgeGetWorldMap().values()) {
			String name = world.dimension.getType().getRegistryName().toString();
			if(name.equals(null)) {
				name = "";
			}
			dims.add(new TpsDimension(name, Helper.mean(server.getTickTime(world.dimension.getType())), world.dimension.getType().getId()));
		
			PacketHandler.network.reply(new MessageResponseServerInfo(Constants.VERSION, dims, ping), context.get());
		}
	}

	public MessageRequest() {}

	public void toBytes(ByteBuf buf) {
	}

	public static MessageRequest fromBytes(ByteBuf buf) {
		return new MessageRequest();
	}
}
