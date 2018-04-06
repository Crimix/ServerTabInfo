package com.black_dog20.servertabinfo.network.message;


import java.util.ArrayList;
import java.util.List;

import com.black_dog20.servertabinfo.utility.Helper;
import com.black_dog20.servertabinfo.utility.TpsDimension;

import io.netty.buffer.ByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;


public class MessageRequest implements IMessage, IMessageHandler<MessageRequest, IMessage> {

	@Override
	public IMessage onMessage(MessageRequest message, MessageContext context) {

		List<TpsDimension> dims = new ArrayList<TpsDimension>();
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		dims.add(new TpsDimension("gui.servertabinfo.overall" , Helper.mean(server.tickTimeArray)* 1.0E-006D));
		
		for(WorldServer world : server.worlds) {
			String name = world.provider.getDimensionType().getName();
			if(name.equals(null)) {
				name = "";
			}
			dims.add(new TpsDimension(name, Helper.mean(server.worldTickTimes.get(world.provider.getDimension()))* 1.0E-006D, world.provider.getDimension()));
		}
		
		return new MessageResponse(dims);
	}

	public MessageRequest() {}

	@Override
	public void toBytes(ByteBuf buf) {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
	}
}