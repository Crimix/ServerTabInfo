package com.black_dog20.servertabinfo.common.network.packets;

import com.black_dog20.servertabinfo.client.ClientDataManager;
import com.black_dog20.servertabinfo.common.utils.Dimension;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class PacketDimensions{

    private List<Dimension> dimensions;

    public PacketDimensions(List<Dimension> dimensions) {
        this.dimensions = dimensions;
    }

    public static void encode(PacketDimensions msg, FriendlyByteBuf buffer) {
        buffer.writeInt(msg.dimensions.size());
        for (Dimension dimension : msg.dimensions) {
            buffer.writeResourceLocation(dimension.name);
            buffer.writeDouble(dimension.meanTickTime);
            buffer.writeInt(dimension.tps);
        }
    }

    public static PacketDimensions decode(FriendlyByteBuf buffer) {
        List<Dimension> dimensions = new LinkedList<>();
        int length = buffer.readInt();
        for (int i = 0; i < length; i++) {
            dimensions.add(new Dimension(buffer.readResourceLocation(), buffer.readDouble(), buffer.readInt()));
        }
        return new PacketDimensions(dimensions);
    }

    public static class Handler {
        public static void handle(PacketDimensions msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                ClientDataManager.DIMENSIONS = msg.dimensions;
            }));

            ctx.get().setPacketHandled(true);
        }
    }
}
