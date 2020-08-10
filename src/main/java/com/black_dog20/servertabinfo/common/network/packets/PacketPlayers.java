package com.black_dog20.servertabinfo.common.network.packets;

import com.black_dog20.servertabinfo.client.ClientDataManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class PacketPlayers {

    private Map<UUID, ResourceLocation> playerDims;

    public PacketPlayers(Map<UUID, ResourceLocation> playerDims) {
        this.playerDims = playerDims;
    }

    public static void encode(PacketPlayers msg, PacketBuffer buffer) {
        buffer.writeInt(msg.playerDims.size());
        for (Map.Entry<UUID, ResourceLocation> playerKvP : msg.playerDims.entrySet()) {
            buffer.writeUniqueId(playerKvP.getKey());
            buffer.writeResourceLocation(playerKvP.getValue());
        }
    }

    public static PacketPlayers decode(PacketBuffer buffer) {
        Map<UUID, ResourceLocation> map = new HashMap<>();
        int length = buffer.readInt();
        for (int i = 0; i < length; i++) {
            map.put(buffer.readUniqueId(), buffer.readResourceLocation());
        }
        return new PacketPlayers(map);
    }

    public static class Handler {
        public static void handle(PacketPlayers msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                ClientDataManager.PLAYER_DIMENSIONS = msg.playerDims;
            }));

            ctx.get().setPacketHandled(true);
        }
    }
}
