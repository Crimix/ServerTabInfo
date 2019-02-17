package com.black_dog20.servertabinfo.network.message;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.black_dog20.servertabinfo.client.GuiTabPage;
import com.black_dog20.servertabinfo.reference.Reference;
import com.black_dog20.servertabinfo.utility.TpsDimension;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.network.NetworkEvent;


public class MessageResponseServerInfo {

	private int version;
	private List<TpsDimension> dims = new ArrayList<TpsDimension>();
	private int ping;
	private String versionString;
	
	public static void onMessage(MessageResponseServerInfo message, Supplier<NetworkEvent.Context> context) {
		
		Minecraft.getInstance().addScheduledTask(new Runnable(){
		  	public void run(){
		  		GuiTabPage.responseVersion = message.version;
		  		GuiTabPage.dims = message.dims;
		  		GuiTabPage.ping = message.ping;
		  		GuiTabPage.serverVersion = message.versionString;
		  		
			}
		});
	}

	public MessageResponseServerInfo() {
	}
	
	public MessageResponseServerInfo(int version, List<TpsDimension> dims, int ping) {
		this.versionString = "";
		this.version = version;
		this.dims = dims;
		this.ping = ping;
	}
	
	public MessageResponseServerInfo(String serverVersion, int version, List<TpsDimension> dims, int ping) {
		this.versionString = serverVersion;
		this.version = version;
		this.dims = dims;
		this.ping = ping;
	}

	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, "Reference.VERSION");
		buf.writeInt(version);
		buf.writeInt(dims.size());
		for(TpsDimension s: dims) {
			ByteBufUtils.writeUTF8String(buf, s.name);
			buf.writeDouble(s.meanTickTime);
			buf.writeInt(s.Id);
		}
		buf.writeInt(ping);
		
	}

	public static MessageResponseServerInfo fromBytes(ByteBuf buf) {
		List<TpsDimension> dims = new ArrayList<TpsDimension>();
		String versionString = ByteBufUtils.readUTF8String(buf);
		int version = buf.readInt();
		int length = buf.readInt();
		while (length != 0) {
			dims.add(new TpsDimension(ByteBufUtils.readUTF8String(buf), buf.readDouble(),buf.readInt()));
			length--;
		}
		int ping = buf.readInt();
		return new MessageResponseServerInfo(versionString, version, dims, ping);
	}
}
