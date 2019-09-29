package com.black_dog20.servertabinfo.network.message;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.black_dog20.servertabinfo.client.GuiTabPage;
import com.black_dog20.servertabinfo.reference.Reference;
import com.black_dog20.servertabinfo.utility.TpsDimension;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;


public class MessageResponseServerInfo {

	private int version;
	private List<TpsDimension> dims = new ArrayList<TpsDimension>();
	private int ping;
	private String versionString;
	
	public static void onMessage(MessageResponseServerInfo message, Supplier<NetworkEvent.Context> context) {
		
		Minecraft.getInstance().runAsync(new Runnable(){
		  	public void run(){
		  		GuiTabPage.responseVersion = message.version;
		  		GuiTabPage.dims = message.dims;
		  		GuiTabPage.ping = message.ping;
		  		GuiTabPage.serverVersion = message.versionString;
		  		
			}
		});
		context.get().setPacketHandled(true);
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

	public void toBytes(PacketBuffer buf) {
		buf.writeString(Reference.getVersion());
		buf.writeInt(version);
		buf.writeInt(dims.size());
		for(TpsDimension s: dims) {
			buf.writeString(s.name);
			buf.writeDouble(s.meanTickTime);
			buf.writeInt(s.Id);
		}
		buf.writeInt(ping);
		
	}

	public static MessageResponseServerInfo fromBytes(PacketBuffer buf) {
		List<TpsDimension> dims = new ArrayList<TpsDimension>();
		String versionString = buf.readString(32767);
		int version = buf.readInt();
		int length = buf.readInt();
		while (length != 0) {
			dims.add(new TpsDimension(buf.readString(32767), buf.readDouble(),buf.readInt()));
			length--;
		}
		int ping = buf.readInt();
		return new MessageResponseServerInfo(versionString, version, dims, ping);
	}
}
