package com.black_dog20.servertabinfo.network.message;


import java.util.ArrayList;
import java.util.List;

import com.black_dog20.servertabinfo.reference.Constants;
import com.black_dog20.servertabinfo.utility.Helper;
import com.black_dog20.servertabinfo.utility.TpsDimension;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;



public class MessageRequest implements IMessage, IMessageHandler<MessageRequest, IMessage> {

	private int version;
	
	@Override
	public IMessage onMessage(MessageRequest message, MessageContext context) {

		List<TpsDimension> dims = new ArrayList<TpsDimension>();
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		int ping = context.getServerHandler().playerEntity.ping;
		if(message.version==0) {
			dims.add(new TpsDimension("gui.servertabinfo.overall" , Helper.mean(server.tickTimeArray)* 1.0E-006D));
		
		for(WorldServer world : server.worldServers) {
			String name = world.provider.getDimensionName();
			if(name.equals(null)) {
				name = "";
			}
			dims.add(new TpsDimension(name, Helper.mean(server.worldTickTimes.get(world.provider.dimensionId))* 1.0E-006D,world.provider.dimensionId));
		}
		
			return new MessageResponse(dims);
		}
		else {
			dims.add(new TpsDimension("gui.servertabinfo.overall" , Helper.mean(server.tickTimeArray)));
		
		for(WorldServer world : server.worldServers) {
			String name = world.provider.getDimensionName();
			if(name.equals(null)) {
				name = "";
			}
			dims.add(new TpsDimension(name, Helper.mean(server.worldTickTimes.get(world.provider.dimensionId)), world.provider.dimensionId));
		}
		
			return new MessageResponseServerInfo(Constants.VERSION, dims, ping);
		}
	}

	public MessageRequest() {}
	
	public MessageRequest(int version) {
		this.version = version;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(version);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		if(buf.isReadable())
			version = buf.readInt();
	}
}
