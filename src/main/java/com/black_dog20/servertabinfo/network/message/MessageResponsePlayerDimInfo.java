package com.black_dog20.servertabinfo.network.message;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.black_dog20.servertabinfo.client.CustomPlayerList;
import com.black_dog20.servertabinfo.utility.TpsDimension;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageResponsePlayerDimInfo  {

	private HashMap<String, TpsDimension> dims = new HashMap<>();
	
	public static void onMessage(MessageResponsePlayerDimInfo message, Supplier<NetworkEvent.Context> context) {
		
		Minecraft.getInstance().addScheduledTask(new Runnable(){
		  	public void run(){
		  		CustomPlayerList.playerDims = message.dims;
		  		
			}
		});
	}

	public MessageResponsePlayerDimInfo() {
	}
	
	public MessageResponsePlayerDimInfo(HashMap<String, TpsDimension> playerDims) {
		this.dims = playerDims;
	}

	public void toBytes(ByteBuf buf) {
		buf.writeInt(dims.size());
		for(Map.Entry<String, TpsDimension> entry : dims.entrySet()) {
			ByteBufUtils.writeUTF8String(buf,entry.getKey());
			ByteBufUtils.writeUTF8String(buf, entry.getValue().name);
			buf.writeDouble(entry.getValue().meanTickTime);
			buf.writeInt(entry.getValue().Id);
		}		
	}

	public static MessageResponsePlayerDimInfo fromBytes(ByteBuf buf) {
		HashMap<String, TpsDimension> dims = new HashMap<>();
		int length = buf.readInt();
		while (length != 0) {
			dims.put(ByteBufUtils.readUTF8String(buf),new TpsDimension(ByteBufUtils.readUTF8String(buf), buf.readDouble(),buf.readInt()));
			length--;
		}
		return new MessageResponsePlayerDimInfo(dims);
		
	}
}