package com.black_dog20.servertabinfo.network.message;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.black_dog20.servertabinfo.client.CustomPlayerList;
import com.black_dog20.servertabinfo.utility.TpsDimension;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageResponsePlayerDimInfo  {

	private HashMap<String, TpsDimension> dims = new HashMap<>();
	
	public static void onMessage(MessageResponsePlayerDimInfo message, Supplier<NetworkEvent.Context> context) {
		
		Minecraft.getInstance().runAsync(new Runnable(){
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

	public void toBytes(PacketBuffer buf) {
		buf.writeInt(dims.size());
		for(Map.Entry<String, TpsDimension> entry : dims.entrySet()) {
			buf.writeString(entry.getKey()); 
			buf.writeString(entry.getValue().name); 
			buf.writeDouble(entry.getValue().meanTickTime);
			buf.writeInt(entry.getValue().Id);
		}		
	}

	public static MessageResponsePlayerDimInfo fromBytes(PacketBuffer buf) {
		HashMap<String, TpsDimension> dims = new HashMap<>();
		int length = buf.readInt();
		while (length != 0) {
			dims.put(buf.readString(32767),new TpsDimension(buf.readString(32767), buf.readDouble(),buf.readInt()));
			length--;
		}
		return new MessageResponsePlayerDimInfo(dims);
		
	}
}