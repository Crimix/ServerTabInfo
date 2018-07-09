package com.black_dog20.servertabinfo.network.message;

import java.util.HashMap;
import java.util.List;

import com.black_dog20.servertabinfo.utility.Helper;
import com.black_dog20.servertabinfo.utility.TpsDimension;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class MessageRequestPlayerDimInfo implements IMessage, IMessageHandler<MessageRequestPlayerDimInfo, IMessage> {
	
	@Override
	public IMessage onMessage(MessageRequestPlayerDimInfo message, MessageContext context) {
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		HashMap<String, TpsDimension> playerDims = new HashMap<String, TpsDimension>();

		List<EntityPlayerMP>playerList = server.getConfigurationManager().playerEntityList;
		
		for(EntityPlayerMP player : playerList) {
			String name = player.getEntityWorld().provider.getDimensionName();
			int id = player.getEntityWorld().provider.dimensionId;
			Double meanTickTime = Helper.mean(server.worldTickTimes.get(id));
			TpsDimension dim = new TpsDimension(name, meanTickTime,id);
			playerDims.put(player.getDisplayName(), dim);
		}
		
		return new MessageResponsePlayerDimInfo(playerDims);
	}

	public MessageRequestPlayerDimInfo() {}
	

	@Override
	public void toBytes(ByteBuf buf) {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
	}
}
