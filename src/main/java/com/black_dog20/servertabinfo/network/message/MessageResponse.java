package com.black_dog20.servertabinfo.network.message;


import java.util.ArrayList;
import java.util.List;

import com.black_dog20.servertabinfo.client.GuiTabPage;
import com.black_dog20.servertabinfo.utility.TpsDimension;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;


public class MessageResponse implements IMessage, IMessageHandler<MessageResponse, IMessage> {

	List<TpsDimension> dims = new ArrayList<TpsDimension>();
	
	@Override
	public IMessage onMessage(MessageResponse message, MessageContext context) {
		
		Minecraft.getMinecraft().addScheduledTask(new Runnable(){
		  	public void run(){
		  		GuiTabPage.dims = message.dims;
			}
		});
		
		return null;
	}

	public MessageResponse() {
	}
	
	public MessageResponse(List<TpsDimension> dims) {
		this.dims = dims;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(dims.size());
		for(TpsDimension s: dims) {
			ByteBufUtils.writeUTF8String(buf, s.name);
			buf.writeDouble(s.meanTickTime);
			buf.writeInt(s.Id);
		}
		
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		int length = buf.readInt();
		while (length != 0) {
			dims.add(new TpsDimension(ByteBufUtils.readUTF8String(buf), buf.readDouble(),buf.readInt()));
			length--;
		}
		
	}
}
