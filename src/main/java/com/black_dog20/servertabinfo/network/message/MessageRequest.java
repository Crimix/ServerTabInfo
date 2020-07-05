package com.black_dog20.servertabinfo.network.message;


import com.black_dog20.servertabinfo.network.PacketHandler;
import com.black_dog20.servertabinfo.reference.Constants;
import com.black_dog20.servertabinfo.utility.Helper;
import com.black_dog20.servertabinfo.utility.TpsDimension;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;


public class MessageRequest {

	@SuppressWarnings("deprecation")
	public static void onMessage(MessageRequest message, Supplier<NetworkEvent.Context> context) {

		List<TpsDimension> dims = new ArrayList<TpsDimension>();
		MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
		int ping = context.get().getSender().ping;
	
		dims.add(new TpsDimension("gui.servertabinfo.overall" , Helper.mean(server.tickTimeArray),Constants.VERSION));
		
		for(ServerWorld world : server.forgeGetWorldMap().values()) {
			String name = world.func_234922_V_().func_240901_a_().toString();
			Double meanTickTime = Helper.mean(server.getTickTime(world.func_234923_W_()));
			dims.add(new TpsDimension(name, meanTickTime));
		}
		PacketHandler.network.reply(new MessageResponseServerInfo(Constants.VERSION, dims, ping), context.get());
		context.get().setPacketHandled(true);
	}

	public MessageRequest() {}

	public void toBytes(PacketBuffer buf) {
	}

	public static MessageRequest fromBytes(PacketBuffer buf) {
		return new MessageRequest();
	}
}
