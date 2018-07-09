package com.black_dog20.servertabinfo.network.message;

import java.util.HashMap;
import java.util.Map;

import com.black_dog20.servertabinfo.client.CustomPlayerList;
import com.black_dog20.servertabinfo.utility.TpsDimension;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;

public class MessageResponsePlayerDimInfo implements IMessage, IMessageHandler<MessageResponsePlayerDimInfo, IMessage> {

	private HashMap<String, TpsDimension> dims = new HashMap<>();
	
	@Override
	public IMessage onMessage(MessageResponsePlayerDimInfo message, MessageContext context) {
		
		Minecraft.getMinecraft().addScheduledTask(new Runnable(){
		  	public void run(){
		  		CustomPlayerList.playerDims = message.dims;
		  		
			}
		});
		
		return null;
	}

	public MessageResponsePlayerDimInfo() {
	}
	
	public MessageResponsePlayerDimInfo(HashMap<String, TpsDimension> playerDims) {
		this.dims = playerDims;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(dims.size());
		for(Map.Entry<String, TpsDimension> entry : dims.entrySet()) {
			ByteBufUtils.writeUTF8String(buf,entry.getKey());
			ByteBufUtils.writeUTF8String(buf, entry.getValue().name);
			buf.writeDouble(entry.getValue().meanTickTime);
			buf.writeInt(entry.getValue().Id);
		}		
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		int length = buf.readInt();
		while (length != 0) {
			dims.put(ByteBufUtils.readUTF8String(buf),new TpsDimension(ByteBufUtils.readUTF8String(buf), buf.readDouble(),buf.readInt()));
			length--;
		}
		
	}
}