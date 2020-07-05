package com.black_dog20.servertabinfo.network.message;

import com.black_dog20.servertabinfo.network.PacketHandler;
import com.black_dog20.servertabinfo.utility.Helper;
import com.black_dog20.servertabinfo.utility.TpsDimension;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public class MessageRequestPlayerDimInfo {
	

	@SuppressWarnings("deprecation")
	public static void onMessage(MessageRequestPlayerDimInfo message, Supplier<NetworkEvent.Context> context) {
		MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
		HashMap<String, TpsDimension> playerDims = new HashMap<String, TpsDimension>();

		List<ServerPlayerEntity>playerList = server.getPlayerList().getPlayers();
		
		for(ServerPlayerEntity player : playerList) {
			String name = player.getEntityWorld().func_234922_V_().func_240901_a_().toString();
			Double meanTickTime = Helper.mean(server.getTickTime(player.getEntityWorld().func_234923_W_()));
			TpsDimension dim = new TpsDimension(name, meanTickTime);
			playerDims.put(player.getDisplayName().getString(), dim);
		}
		
		PacketHandler.network.reply(new MessageResponsePlayerDimInfo(playerDims), context.get());
		context.get().setPacketHandled(true);
	}

	public MessageRequestPlayerDimInfo() {}
	


	public void toBytes(PacketBuffer buf) {
	}


	public static MessageRequestPlayerDimInfo fromBytes(PacketBuffer buf) {
		return new MessageRequestPlayerDimInfo();
	}
}
