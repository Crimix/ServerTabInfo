package com.black_dog20.servertabinfo.network.message;


import java.util.ArrayList;
import java.util.List;

import com.black_dog20.servertabinfo.reference.Constants;
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

	private int version;
	
	@Override
	public IMessage onMessage(MessageRequest message, MessageContext context) {

		List<TpsDimension> dims = new ArrayList<TpsDimension>();
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		int ping = context.getServerHandler().playerEntity.ping;
		if(message.version==0) {
			dims.add(new TpsDimension("gui.servertabinfo.overall" , Helper.mean(server.tickTimeArray)* 1.0E-006D,0));
		
		for(WorldServer world : server.worldServers) {
			String name = world.provider.getDimensionType().getName();
			if(name.equals(null)) {
				name = "";
			}
			dims.add(new TpsDimension(name, Helper.mean(server.worldTickTimes.get(world.provider.getDimension()))* 1.0E-006D, world.provider.getDimension()));
		}
		
			return new MessageResponse(dims);
		}
		else {
			dims.add(new TpsDimension("gui.servertabinfo.overall" , Helper.mean(server.tickTimeArray),Constants.VERSION));
		
		for(WorldServer world : server.worldServers) {
			String name = world.provider.getDimensionType().getName();
			if(name.equals(null)) {
				name = "";
			}
			dims.add(new TpsDimension(name, Helper.mean(server.worldTickTimes.get(world.provider.getDimension())), world.provider.getDimension()));
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