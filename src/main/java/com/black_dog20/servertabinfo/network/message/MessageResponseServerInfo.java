package com.black_dog20.servertabinfo.network.message;


import java.util.ArrayList;
import java.util.List;

import com.black_dog20.servertabinfo.client.GuiTabPage;
import com.black_dog20.servertabinfo.reference.Reference;
import com.black_dog20.servertabinfo.utility.TpsDimension;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;


public class MessageResponseServerInfo implements IMessage, IMessageHandler<MessageResponseServerInfo, IMessage> {

	private int version;
	private List<TpsDimension> dims = new ArrayList<TpsDimension>();
	private int ping;
	private String versionString;
	
	@Override
	public IMessage onMessage(MessageResponseServerInfo message, MessageContext context) {
		
		Minecraft.getMinecraft().addScheduledTask(new Runnable(){
		  	public void run(){
		  		GuiTabPage.dims = message.dims;
		  		GuiTabPage.responseVersion = message.version;
		  		GuiTabPage.ping = message.ping;
		  		GuiTabPage.serverVersion = message.versionString;
		  		
			}
		});
		
		return null;
	}

	public MessageResponseServerInfo() {
	}
	
	public MessageResponseServerInfo(int version, List<TpsDimension> dims, int ping) {
		this.version = version;
		this.dims = dims;
		this.ping = ping;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, Reference.VERSION);
		buf.writeInt(version);
		buf.writeInt(dims.size());
		for(TpsDimension s: dims) {
			ByteBufUtils.writeUTF8String(buf, s.name);
			buf.writeDouble(s.meanTickTime);
			buf.writeInt(s.Id);
		}
		buf.writeInt(ping);
		
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		versionString = ByteBufUtils.readUTF8String(buf);
		version = buf.readInt();
		int length = buf.readInt();
		while (length != 0) {
			dims.add(new TpsDimension(ByteBufUtils.readUTF8String(buf), buf.readDouble(),buf.readInt()));
			length--;
		}
		if(version >= 2) {
			ping = buf.readInt();
		}
		
	}
}
