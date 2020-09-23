package com.black_dog20.servertabinfo.common.network;


import com.black_dog20.servertabinfo.ServerTabInfo;
import com.black_dog20.servertabinfo.client.ClientDataManager;
import com.black_dog20.servertabinfo.common.network.packets.PacketDimensions;
import com.black_dog20.servertabinfo.common.network.packets.PacketPlayers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;


public class PacketHandler {

	private static final String PROTOCOL_VERSION = Integer.toString(1);
	private static short index = 0;

	public static final SimpleChannel NETWORK = NetworkRegistry.ChannelBuilder
			.named(new ResourceLocation(ServerTabInfo.MOD_ID, "network"))
			.clientAcceptedVersions(PacketHandler::checkRemoteVersion)
			.serverAcceptedVersions(PacketHandler::checkClientVersion)
			.networkProtocolVersion(() -> PROTOCOL_VERSION)
			.simpleChannel();

	public static void register() {
		registerMessage(PacketDimensions.class, PacketDimensions::encode, PacketDimensions::decode, PacketDimensions.Handler::handle);
        registerMessage(PacketPlayers.class, PacketPlayers::encode, PacketPlayers::decode, PacketPlayers.Handler::handle);
	}

	public static void sendTo(Object msg, ServerPlayerEntity player) {
		if (!(player instanceof FakePlayer))
			NETWORK.sendTo(msg, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
	}

	public static void sendToAll(Object msg, World world) {
		for (PlayerEntity player : world.getPlayers()) {
			if (!(player instanceof FakePlayer))
				NETWORK.sendTo(msg, ((ServerPlayerEntity) player).connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
		}
	}

	public static void sendToAll(Object msg) {
		NETWORK.send(PacketDistributor.ALL.noArg(), msg);
	}

	public static void sendToServer(Object msg) {
		NETWORK.sendToServer(msg);
	}

	private static <MSG> void registerMessage(Class<MSG> messageType, BiConsumer<MSG, PacketBuffer> encoder, Function<PacketBuffer, MSG> decoder, BiConsumer<MSG, Supplier<NetworkEvent.Context>> messageConsumer) {
		NETWORK.registerMessage(index, messageType, encoder, decoder, messageConsumer);
		index++;
		if (index > 0xFF)
			throw new RuntimeException("Too many messages!");
	}

	private static boolean checkRemoteVersion(String s) {
		if(s.equals(NetworkRegistry.ABSENT))
			ClientDataManager.modOnServer = false;
		else
			ClientDataManager.modOnServer = true;
		return true;
	}

	private static boolean checkClientVersion(String s) {
		return true;
	}

}
